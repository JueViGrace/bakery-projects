package api

import (
	"github.com/JueViGrace/bakery-server/internal/data"
	"github.com/gofiber/fiber/v2"
)

type Api interface {
	Init() error
}

type api struct {
	*fiber.App
	db data.Storage
}

func New(app *fiber.App, db data.Storage) Api {
	return &api{
		App: app,
		db:  db,
	}
}

func (a *api) Init() (err error) {
	a.RegisterRoutes()

	return
}
