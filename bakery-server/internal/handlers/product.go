package handlers

import (
	"github.com/JueViGrace/bakery-server/internal/data"
	"github.com/JueViGrace/bakery-server/internal/types"
	"github.com/JueViGrace/bakery-server/internal/util"
	"github.com/gofiber/fiber/v2"
)

type ProductRoutes interface {
	GetProducts(c *fiber.Ctx) error
	GetProductById(c *fiber.Ctx) error
	CreateProduct(c *fiber.Ctx, body *types.CreateProductRequest) error
	UpdateProduct(c *fiber.Ctx, body *types.UpdateProductRequest) error
	DeleteProduct(c *fiber.Ctx) error
}

type ProductHandler struct {
	db data.ProductStore
}

func NewProductHandler(db data.ProductStore) ProductRoutes {
	return &ProductHandler{
		db: db,
	}
}

func (h *ProductHandler) GetProducts(c *fiber.Ctx) error {
	products, err := h.db.GetProducts()
	if err != nil {
		res := types.RespondNotFound(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res := types.RespondOk(products, "Success")
	return c.Status(res.Status).JSON(res)
}

func (h *ProductHandler) GetProductById(c *fiber.Ctx) error {
	id, err := util.GetIdFromParams(c.Params("id"))
	if err != nil {
		res := types.RespondBadRequest(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	product, err := h.db.GetProductById(id)
	if err != nil {
		res := types.RespondNotFound(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res := types.RespondOk(product, "Success")
	return c.Status(res.Status).JSON(res)
}

func (h *ProductHandler) CreateProduct(c *fiber.Ctx, body *types.CreateProductRequest) error {
	product, err := h.db.CreateProduct(body)
	if err != nil {
		res := types.RespondNotFound(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res := types.RespondCreated(product, "Success")
	return c.Status(res.Status).JSON(res)
}

func (h *ProductHandler) UpdateProduct(c *fiber.Ctx, body *types.UpdateProductRequest) error {
	product, err := h.db.UpdateProduct(body)
	if err != nil {
		res := types.RespondNoContent(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res := types.RespondAccepted(product, "Success")
	return c.Status(res.Status).JSON(res)
}

func (h *ProductHandler) DeleteProduct(c *fiber.Ctx) error {
	id, err := util.GetIdFromParams(c.Params("id"))
	if err != nil {
		res := types.RespondBadRequest(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	err = h.db.DeleteProduct(id)
	if err != nil {
		res := types.RespondNoContent(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res := types.RespondNoContent("Deleted!", "Success")
	return c.Status(res.Status).JSON(res)
}
