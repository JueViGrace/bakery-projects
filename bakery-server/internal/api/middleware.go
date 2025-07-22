package api

import (
	"errors"
	"fmt"
	"strings"

	"github.com/JueViGrace/bakery-server/internal/data"
	"github.com/JueViGrace/bakery-server/internal/types"
	"github.com/JueViGrace/bakery-server/internal/util"
	"github.com/go-playground/validator/v10"
	"github.com/gofiber/fiber/v2"
)

var (
	v *validator.Validate = validator.New()
)

func (a *api) adminAuthMiddleware(c *fiber.Ctx) error {
	data, err := getUserDataForReq(c, a.db)
	if err != nil {
		res := types.RespondUnauthorized(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	if data.Role != types.Admin {
		res := types.RespondForbbiden(nil, "forbbiden resource")
		return c.Status(res.Status).JSON(res)
	}

	return c.Next()
}

func (a *api) sessionMiddleware(c *fiber.Ctx) error {
	_, err := getUserDataForReq(c, a.db)
	if err != nil {
		res := types.RespondUnauthorized(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	return c.Next()
}

func validatedMiddleware[T any](t T, handler func(c *fiber.Ctx, t *T) error) fiber.Handler {
	return func(c *fiber.Ctx) error {
		res := new(types.APIResponse)
		body, err := validateBody(t, c)
		if err != nil {
			res = types.RespondBadRequest(nil, err.Error())
			return c.Status(res.Status).JSON(res)
		}

		return handler(c, body)
	}
}

func validatedHandler[T any](t T, c *fiber.Ctx, handler func(t *T) error) error {
	res := new(types.APIResponse)
	body, err := validateBody(t, c)
	if err != nil {
		res = types.RespondBadRequest(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}
	return handler(body)
}

func validateBody[T any](t T, c *fiber.Ctx) (*T, error) {
	var request *T = new(T)

	if err := c.BodyParser(&request); err != nil {
		return nil, err
	}

	errs := v.Struct(request)
	if errs != nil {
		errMsgs := make([]string, 0)
		for _, err := range errs.(validator.ValidationErrors) {
			errMsgs = append(errMsgs, fmt.Sprintf(
				"[%s]: '%v' | Needs to implement '%s'",
				err.Field(),
				err.Value(),
				err.Tag(),
			))
		}

		return nil, errors.New(strings.Join(errMsgs, " and "))
	}

	return request, nil
}

func (a *api) authenticatedMiddleware(handler func(c *fiber.Ctx, data *types.AuthData) error) fiber.Handler {
	return func(c *fiber.Ctx) error {
		data, err := getUserDataForReq(c, a.db)
		if err != nil {
			res := types.RespondUnauthorized(nil, err.Error())
			return c.Status(res.Status).JSON(res)
		}
		return handler(c, data)
	}
}

func getUserDataForReq(c *fiber.Ctx, db data.Storage) (*types.AuthData, error) {
	jwt, err := util.ExtractJWTFromHeader(c, func(s string) {
		db.SessionStore().DeleteSessionByToken(s)
	})
	if err != nil {
		return nil, err
	}

	session, err := db.SessionStore().GetSessionById(jwt.Claims.SessionID)
	if err != nil {
		return nil, err
	}

	dbUser, err := db.UserStore().GetUserById(&session.UserId)
	if err != nil {
		return nil, err
	}

	return &types.AuthData{
		UserId:    dbUser.ID,
		SessionId: session.ID,
		Username:  dbUser.Username,
		Role:      dbUser.Role,
	}, nil
}
