// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.29.0

package database

import (
	"database/sql"
)

type BakeryOrder struct {
	ID            string
	TotalAmount   float64
	PaymentMethod string
	Status        string
	UserID        string
	CreatedAt     string
	UpdatedAt     string
}

type BakeryOrderProduct struct {
	OrderID         string
	ProductID       string
	ProductName     string
	ProductPrice    float64
	ProductDiscount float64
	ProductRating   float64
	TotalPrice      float64
	Quantity        int64
}

type BakeryProduct struct {
	ID          string
	Name        string
	Description string
	Category    string
	Price       float64
	Stock       int64
	Issued      int64
	HasStock    int64
	Discount    float64
	Rating      float64
	Images      string
	CreatedAt   string
	UpdatedAt   string
	DeletedAt   sql.NullString
}

type BakerySession struct {
	ID           string
	RefreshToken string
	AccessToken  string
	Username     string
	UserID       string
}

type BakeryUser struct {
	ID          string
	FirstName   string
	LastName    string
	Alias       string
	Username    string
	Email       string
	Password    string
	PhoneNumber string
	BirthDate   string
	Role        string
	CreatedAt   string
	UpdatedAt   string
	DeletedAt   sql.NullString
}
