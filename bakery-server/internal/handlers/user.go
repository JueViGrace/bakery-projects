package handlers

import (
	"github.com/JueViGrace/bakery-server/internal/data"
	"github.com/JueViGrace/bakery-server/internal/types"
	"github.com/JueViGrace/bakery-server/internal/util"
	"github.com/gofiber/fiber/v2"
)

type UserHandler interface {
	GetUsers(c *fiber.Ctx) error
	GetUserById(c *fiber.Ctx, data *types.AuthData) error
	UpdateUser(c *fiber.Ctx, body *types.UpdateUserRequest, data *types.AuthData) error
	DeleteUser(c *fiber.Ctx, data *types.AuthData) error
}

type userHandler struct {
	db data.UserStore
}

func NewUserHandler(db data.UserStore) UserHandler {
	return &userHandler{
		db: db,
	}
}

func (h *userHandler) GetUsers(c *fiber.Ctx) (err error) {
	users, err := h.db.GetUsers()
	if err != nil {
		res := types.RespondNotFound(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res := types.RespondOk(users, "Success")
	return c.Status(res.Status).JSON(res)
}

func (h *userHandler) GetUserById(c *fiber.Ctx, a *types.AuthData) error {
	user, err := h.db.GetUserById(&a.UserId)
	if err != nil {
		res := types.RespondNotFound(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res := types.RespondOk(user, "Success")
	return c.Status(res.Status).JSON(res)
}

// todo: refactor this
func (h *userHandler) UpdateUser(c *fiber.Ctx, body *types.UpdateUserRequest, a *types.AuthData) error {
	user, err := h.db.UpdateUser(body)
	if err != nil {
		res := types.RespondNotFound(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res := types.RespondAccepted(user, "Success")
	return c.Status(res.Status).JSON(res)
}

func (h *userHandler) DeleteUser(c *fiber.Ctx, a *types.AuthData) error {
	id, err := util.GetIdFromParams(c.Params("id"))
	if err != nil {
		res := types.RespondBadRequest(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	err = h.db.DeleteUser(id)
	if err != nil {
		res := types.RespondBadRequest(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res := types.RespondNoContent("Deleted", "Success")
	return c.Status(res.Status).JSON(res)
}
