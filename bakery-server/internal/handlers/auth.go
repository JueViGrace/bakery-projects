package handlers

import (
	"github.com/JueViGrace/bakery-server/internal/data"
	"github.com/JueViGrace/bakery-server/internal/types"
	"github.com/gofiber/fiber/v2"
)

type AuthHandler interface {
	RequestPasswordReset(c *fiber.Ctx, body *types.RequestPasswordReset) error
	ConfirmPasswordReset(c *fiber.Ctx, body *types.ConfirmPasswordReset) error
	RecoverPassword(c *fiber.Ctx, body *types.RecoverPasswordRequest) error
	LogOut(c *fiber.Ctx, data *types.AuthData) error
	Ping(c *fiber.Ctx, data *types.AuthData) error
	SignIn(c *fiber.Ctx, body *types.SignInRequest) error
	SignUp(c *fiber.Ctx, body *types.SignUpRequest) error
	Refresh(c *fiber.Ctx, data *types.AuthData) error
}

type authHandler struct {
	db data.AuthStore
}

func NewAuthHandler(db data.AuthStore) AuthHandler {
	return &authHandler{
		db: db,
	}
}

func (h *authHandler) RequestPasswordReset(c *fiber.Ctx, body *types.RequestPasswordReset) error {
	return nil
}

func (h *authHandler) ConfirmPasswordReset(c *fiber.Ctx, body *types.ConfirmPasswordReset) error {
	return nil
}

func (h *authHandler) RecoverPassword(c *fiber.Ctx, body *types.RecoverPasswordRequest) error {
	res := new(types.APIResponse)

	msg, err := h.db.RecoverPassword(body)
	if err != nil {
		res = types.RespondNotFound(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res = types.RespondAccepted(msg, "Success")
	return c.Status(res.Status).JSON(res)
}

func (h *authHandler) LogOut(c *fiber.Ctx, data *types.AuthData) error {
	res := new(types.APIResponse)

	msg, err := h.db.LogOut(data.SessionId)

	if err != nil {
		res = types.RespondNotFound(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res = types.RespondAccepted(msg, "Success")
	return c.Status(res.Status).JSON(res)
}

func (h *authHandler) Ping(c *fiber.Ctx, data *types.AuthData) error {
	res := new(types.APIResponse)

	valid, err := h.db.Ping(data.SessionId)
	if err != nil {
		res = types.RespondNotFound(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res = types.RespondOk(valid, "Success")
	return c.Status(res.Status).JSON(res)

}

func (h *authHandler) SignIn(c *fiber.Ctx, body *types.SignInRequest) error {
	res := new(types.APIResponse)

	token, err := h.db.SignIn(body)
	if err != nil {
		res = types.RespondNotFound(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res = types.RespondOk(token, "Success")
	return c.Status(res.Status).JSON(res)
}

func (h *authHandler) SignUp(c *fiber.Ctx, body *types.SignUpRequest) error {
	res := new(types.APIResponse)

	token, err := h.db.SignUp(body)
	if err != nil {
		res = types.RespondNotFound(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res = types.RespondCreated(token, "Success")
	return c.Status(res.Status).JSON(res)
}

func (h *authHandler) Refresh(c *fiber.Ctx, data *types.AuthData) error {
	res := new(types.APIResponse)

	msg, err := h.db.Refresh(data)
	if err != nil {
		res = types.RespondNotFound(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res = types.RespondAccepted(msg, "Success")
	return c.Status(res.Status).JSON(res)
}
