package api

import (
	"github.com/JueViGrace/bakery-server/internal/handlers"
	"github.com/JueViGrace/bakery-server/internal/types"
	"github.com/gofiber/fiber/v2"
)

func (a *api) ProductRoutes(api fiber.Router) {
	productRoutes := api.Group("/products")

	productHandler := handlers.NewProductHandler(a.db.ProductStore())

	productRoutes.Get("/", productHandler.GetProducts)
	productRoutes.Get("/:id", productHandler.GetProductById)
	productRoutes.Post("/", a.sessionMiddleware, a.adminAuthMiddleware, validatedMiddleware(types.CreateProductRequest{}, productHandler.CreateProduct))
	productRoutes.Patch("/", a.sessionMiddleware, a.adminAuthMiddleware, validatedMiddleware(types.UpdateProductRequest{}, productHandler.UpdateProduct))
	productRoutes.Delete("/:id", a.sessionMiddleware, a.adminAuthMiddleware, productHandler.DeleteProduct)
}
