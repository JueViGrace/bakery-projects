package types

import (
	"strings"
	"time"

	"github.com/JueViGrace/bakery-server/internal/database"
	"github.com/google/uuid"
)

// TODO: fix response object
type ProductResponse struct {
	ID          uuid.UUID `json:"id"`
	Name        string    `json:"name"`
	Description string    `json:"description"`
	Brand       string    `json:"brand"`
	CategoryID  string    `json:"category_id"`
	ByRequest   bool      `json:"by_request"`
	Discount    float64   `json:"discount"`
	Price       float64   `json:"price"`
	Stock       int       `json:"stock"`
	Issued      int       `json:"issued"`
	Images      []string  `json:"images"`
	CreatedAt   string    `json:"created_at"`
	UpdatedAt   string    `json:"updated_at"`
	DeletedAt   string    `json:"-"`
}

type CreateProductRequest struct {
	Name        string   `json:"name" validate:"required"`
	Description string   `json:"description" validate:"required"`
	Brand       string   `json:"brand" validate:"required"`
	CategoryID  string   `json:"category_id" validate:"required"`
	ByRequest   bool     `json:"by_request" validate:"required"`
	Discount    float64  `json:"discount" validate:"required"`
	Price       float64  `json:"price" validate:"required"`
	Stock       int      `json:"stock" validate:"required"`
	Issued      int      `json:"issued" validate:"required"`
	Images      []string `json:"images" validate:"required"`
}

type UpdateProductRequest struct {
	Description string    `json:"description" validate:"required"`
	Brand       string    `json:"brand" validate:"required"`
	CategoryID  string    `json:"category_id" validate:"required"`
	ByRequest   bool      `json:"by_request" validate:"required"`
	Discount    float64   `json:"discount" validate:"required"`
	Price       float64   `json:"price" validate:"required"`
	Stock       int       `json:"stock" validate:"required"`
	Issued      int       `json:"issued" validate:"required"`
	Images      []string  `json:"images" validate:"required"`
	ID          uuid.UUID `json:"id" validate:"required"`
}

type UpdateProductNameRequest struct {
	Name string    `json:"name" validate:"required"`
	ID   uuid.UUID `json:"id" validate:"required"`
}

// TODO: fix response mapping
func DbProductToProduct(db *database.Product) (*ProductResponse, error) {
	id, err := uuid.Parse(db.ID)
	if err != nil {
		return nil, err
	}

	var byRequest bool
	if db.ByRequest == 1 {
		byRequest = true
	} else {
		byRequest = false
	}

	return &ProductResponse{
		ID:          id,
		Name:        db.Name,
		Description: db.Description,
		Price:       db.Price,
		Stock:       int(db.Stock),
		Issued:      int(db.Issued),
		Discount:    db.Discount,
		Images:      strings.Split(db.Images, ","),
		CreatedAt:   db.CreatedAt,
		UpdatedAt:   db.UpdatedAt,
		DeletedAt:   db.DeletedAt.String,
		Brand:       db.Brand,
		CategoryID:  db.CategoryID,
		ByRequest:   byRequest,
	}, nil
}

func NewCreateProductParams(r *CreateProductRequest) (*database.CreateProductParams, error) {
	id, err := uuid.NewV7()
	if err != nil {
		return nil, err
	}

	var byRequest int64
	if r.ByRequest {
		byRequest = 1
	} else {
		byRequest = 0
	}

	return &database.CreateProductParams{
		ID:          id.String(),
		Name:        r.Name,
		Description: r.Description,
		Brand:       r.Brand,
		ByRequest:   byRequest,
		CategoryID:  r.CategoryID,
		Discount:    r.Discount,
		Price:       r.Price,
		Stock:       int64(r.Stock),
		Issued:      int64(r.Issued),
		Images:      strings.Join(r.Images, ","),
		CreatedAt:   time.Now().UTC().String(),
		UpdatedAt:   time.Now().UTC().String(),
	}, nil
}

func NewUpdateProductParams(r *UpdateProductRequest) *database.UpdateProductParams {
	var byRequest int64
	if r.ByRequest {
		byRequest = 1
	} else {
		byRequest = 0
	}

	return &database.UpdateProductParams{
		Description: r.Description,
		Brand:       r.Brand,
		ByRequest:   byRequest,
		CategoryID:  r.CategoryID,
		Discount:    r.Discount,
		Price:       r.Price,
		Stock:       int64(r.Stock),
		Issued:      int64(r.Issued),
		Images:      strings.Join(r.Images, ","),
		UpdatedAt:   time.Now().UTC().String(),
		ID:          r.ID.String(),
	}
}
