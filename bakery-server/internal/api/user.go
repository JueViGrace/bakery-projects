package api

import (
	"github.com/JueViGrace/bakery-server/internal/handlers"
	"github.com/JueViGrace/bakery-server/internal/types"
	"github.com/gofiber/fiber/v2"
)

func (a *api) UserRoutes(api fiber.Router) {
	usersGroup := api.Group("/users", a.sessionMiddleware)

	userHandler := handlers.NewUserHandler(a.db.UserStore())

	usersGroup.Get("/", a.adminAuthMiddleware, userHandler.GetUsers)
	usersGroup.Get("/me", a.authenticatedMiddleware(userHandler.GetUserById))
	usersGroup.Patch("/:id", a.authenticatedMiddleware(func(c *fiber.Ctx, data *types.AuthData) error {
		return validatedHandler(types.UpdateUserRequest{}, c, func(t *types.UpdateUserRequest) error {
			return userHandler.UpdateUser(c, t, data)
		})
	}))
	usersGroup.Delete("/:id", a.authenticatedMiddleware(userHandler.DeleteUser))
}
