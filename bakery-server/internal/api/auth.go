package api

import (
	"github.com/JueViGrace/bakery-server/internal/handlers"
	"github.com/JueViGrace/bakery-server/internal/types"
	"github.com/gofiber/fiber/v2"
)

func (a *api) AuthRoutes(api fiber.Router) {
	authGroup := api.Group("/auth")

	authHandler := handlers.NewAuthHandler(a.db.AuthStore())

	authGroup.Post("/reset/password/request", validatedMiddleware(types.RequestPasswordReset{}, authHandler.RequestPasswordReset))
	authGroup.Post("/reset/password/confirm", validatedMiddleware(types.ConfirmPasswordReset{}, authHandler.ConfirmPasswordReset))
	authGroup.Post("/reset/password", validatedMiddleware(types.RecoverPasswordRequest{}, authHandler.RecoverPassword))
	authGroup.Post("/logout", a.authenticatedMiddleware(authHandler.LogOut))
	authGroup.Post("/refresh", a.authenticatedMiddleware(authHandler.Refresh))
	authGroup.Post("/login", validatedMiddleware(types.SignInRequest{}, authHandler.SignIn))
	authGroup.Post("/signup", validatedMiddleware(types.SignUpRequest{}, authHandler.SignUp))
}
