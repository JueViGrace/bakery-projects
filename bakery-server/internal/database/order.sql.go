// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.29.0
// source: order.sql

package database

import (
	"context"
	"database/sql"
)

const cancelOrder = `-- name: CancelOrder :exec
UPDATE orders SET 
    status = "CANCELLED",
    updated_at = ?
WHERE id = ?
`

type CancelOrderParams struct {
	UpdatedAt string
	ID        string
}

func (q *Queries) CancelOrder(ctx context.Context, arg CancelOrderParams) error {
	_, err := q.db.ExecContext(ctx, cancelOrder, arg.UpdatedAt, arg.ID)
	return err
}

const createOrder = `-- name: CreateOrder :exec
;

INSERT INTO orders (
    id,
    net_price,
    total_price,
    payment_method,
    status,
    user_id,
    created_at,
    updated_at
)
VALUES (?, ?, ?, ?, ?, ?, ?, ?)
`

type CreateOrderParams struct {
	ID            string
	NetPrice      float64
	TotalPrice    float64
	PaymentMethod string
	Status        string
	UserID        string
	CreatedAt     string
	UpdatedAt     string
}

func (q *Queries) CreateOrder(ctx context.Context, arg CreateOrderParams) error {
	_, err := q.db.ExecContext(ctx, createOrder,
		arg.ID,
		arg.NetPrice,
		arg.TotalPrice,
		arg.PaymentMethod,
		arg.Status,
		arg.UserID,
		arg.CreatedAt,
		arg.UpdatedAt,
	)
	return err
}

const createOrderProducts = `-- name: CreateOrderProducts :exec
INSERT INTO order_products(
    order_id,
    product_id,
    product_name,
    product_price,
    product_discount,
    final_price,
    quantity
)
VALUES (?, ?, ?, ?, ?, ?, ?)
`

type CreateOrderProductsParams struct {
	OrderID         string
	ProductID       string
	ProductName     string
	ProductPrice    float64
	ProductDiscount float64
	FinalPrice      float64
	Quantity        int64
}

func (q *Queries) CreateOrderProducts(ctx context.Context, arg CreateOrderProductsParams) error {
	_, err := q.db.ExecContext(ctx, createOrderProducts,
		arg.OrderID,
		arg.ProductID,
		arg.ProductName,
		arg.ProductPrice,
		arg.ProductDiscount,
		arg.FinalPrice,
		arg.Quantity,
	)
	return err
}

const getOrderById = `-- name: GetOrderById :one
;

select orders.id, net_price, total_price, payment_method, status, user_id, orders.created_at, orders.updated_at, order_id, product_id, product_name, product_price, product_discount, quantity, final_price, products.id, name, description, brand, by_request, discount, price, stock, issued, images, category_id, products.created_at, products.updated_at, deleted_at
from orders
left join order_products on orders.id = order_products.id
left join products on order_products.product_id = products.id
where orders.id = ? and orders.deleted_at is null
`

type GetOrderByIdRow struct {
	ID              string
	NetPrice        float64
	TotalPrice      float64
	PaymentMethod   string
	Status          string
	UserID          string
	CreatedAt       string
	UpdatedAt       string
	OrderID         sql.NullString
	ProductID       sql.NullString
	ProductName     sql.NullString
	ProductPrice    sql.NullFloat64
	ProductDiscount sql.NullFloat64
	Quantity        sql.NullInt64
	FinalPrice      sql.NullFloat64
	ID_2            sql.NullString
	Name            sql.NullString
	Description     sql.NullString
	Brand           sql.NullString
	ByRequest       sql.NullInt64
	Discount        sql.NullFloat64
	Price           sql.NullFloat64
	Stock           sql.NullInt64
	Issued          sql.NullInt64
	Images          sql.NullString
	CategoryID      sql.NullString
	CreatedAt_2     sql.NullString
	UpdatedAt_2     sql.NullString
	DeletedAt       sql.NullString
}

func (q *Queries) GetOrderById(ctx context.Context, id string) (GetOrderByIdRow, error) {
	row := q.db.QueryRowContext(ctx, getOrderById, id)
	var i GetOrderByIdRow
	err := row.Scan(
		&i.ID,
		&i.NetPrice,
		&i.TotalPrice,
		&i.PaymentMethod,
		&i.Status,
		&i.UserID,
		&i.CreatedAt,
		&i.UpdatedAt,
		&i.OrderID,
		&i.ProductID,
		&i.ProductName,
		&i.ProductPrice,
		&i.ProductDiscount,
		&i.Quantity,
		&i.FinalPrice,
		&i.ID_2,
		&i.Name,
		&i.Description,
		&i.Brand,
		&i.ByRequest,
		&i.Discount,
		&i.Price,
		&i.Stock,
		&i.Issued,
		&i.Images,
		&i.CategoryID,
		&i.CreatedAt_2,
		&i.UpdatedAt_2,
		&i.DeletedAt,
	)
	return i, err
}

const getOrders = `-- name: GetOrders :many
select orders.id, net_price, total_price, payment_method, status, user_id, orders.created_at, orders.updated_at, order_id, product_id, product_name, product_price, product_discount, quantity, final_price, products.id, name, description, brand, by_request, discount, price, stock, issued, images, category_id, products.created_at, products.updated_at, deleted_at
from orders
left join order_products on orders.id = order_products.id
left join products on order_products.product_id = products.id
where orders.deleted_at is null
`

type GetOrdersRow struct {
	ID              string
	NetPrice        float64
	TotalPrice      float64
	PaymentMethod   string
	Status          string
	UserID          string
	CreatedAt       string
	UpdatedAt       string
	OrderID         sql.NullString
	ProductID       sql.NullString
	ProductName     sql.NullString
	ProductPrice    sql.NullFloat64
	ProductDiscount sql.NullFloat64
	Quantity        sql.NullInt64
	FinalPrice      sql.NullFloat64
	ID_2            sql.NullString
	Name            sql.NullString
	Description     sql.NullString
	Brand           sql.NullString
	ByRequest       sql.NullInt64
	Discount        sql.NullFloat64
	Price           sql.NullFloat64
	Stock           sql.NullInt64
	Issued          sql.NullInt64
	Images          sql.NullString
	CategoryID      sql.NullString
	CreatedAt_2     sql.NullString
	UpdatedAt_2     sql.NullString
	DeletedAt       sql.NullString
}

func (q *Queries) GetOrders(ctx context.Context) ([]GetOrdersRow, error) {
	rows, err := q.db.QueryContext(ctx, getOrders)
	if err != nil {
		return nil, err
	}
	defer rows.Close()
	var items []GetOrdersRow
	for rows.Next() {
		var i GetOrdersRow
		if err := rows.Scan(
			&i.ID,
			&i.NetPrice,
			&i.TotalPrice,
			&i.PaymentMethod,
			&i.Status,
			&i.UserID,
			&i.CreatedAt,
			&i.UpdatedAt,
			&i.OrderID,
			&i.ProductID,
			&i.ProductName,
			&i.ProductPrice,
			&i.ProductDiscount,
			&i.Quantity,
			&i.FinalPrice,
			&i.ID_2,
			&i.Name,
			&i.Description,
			&i.Brand,
			&i.ByRequest,
			&i.Discount,
			&i.Price,
			&i.Stock,
			&i.Issued,
			&i.Images,
			&i.CategoryID,
			&i.CreatedAt_2,
			&i.UpdatedAt_2,
			&i.DeletedAt,
		); err != nil {
			return nil, err
		}
		items = append(items, i)
	}
	if err := rows.Close(); err != nil {
		return nil, err
	}
	if err := rows.Err(); err != nil {
		return nil, err
	}
	return items, nil
}

const getOrdersByUser = `-- name: GetOrdersByUser :many
;

select orders.id, net_price, total_price, payment_method, status, user_id, orders.created_at, orders.updated_at, order_id, product_id, product_name, product_price, product_discount, quantity, final_price, products.id, name, description, brand, by_request, discount, price, stock, issued, images, category_id, products.created_at, products.updated_at, deleted_at
from orders
left join order_products on orders.id = order_products.id
left join products on order_products.product_id = products.id
where orders.user_id = ? and orders.deleted_at is null
`

type GetOrdersByUserRow struct {
	ID              string
	NetPrice        float64
	TotalPrice      float64
	PaymentMethod   string
	Status          string
	UserID          string
	CreatedAt       string
	UpdatedAt       string
	OrderID         sql.NullString
	ProductID       sql.NullString
	ProductName     sql.NullString
	ProductPrice    sql.NullFloat64
	ProductDiscount sql.NullFloat64
	Quantity        sql.NullInt64
	FinalPrice      sql.NullFloat64
	ID_2            sql.NullString
	Name            sql.NullString
	Description     sql.NullString
	Brand           sql.NullString
	ByRequest       sql.NullInt64
	Discount        sql.NullFloat64
	Price           sql.NullFloat64
	Stock           sql.NullInt64
	Issued          sql.NullInt64
	Images          sql.NullString
	CategoryID      sql.NullString
	CreatedAt_2     sql.NullString
	UpdatedAt_2     sql.NullString
	DeletedAt       sql.NullString
}

func (q *Queries) GetOrdersByUser(ctx context.Context, userID string) ([]GetOrdersByUserRow, error) {
	rows, err := q.db.QueryContext(ctx, getOrdersByUser, userID)
	if err != nil {
		return nil, err
	}
	defer rows.Close()
	var items []GetOrdersByUserRow
	for rows.Next() {
		var i GetOrdersByUserRow
		if err := rows.Scan(
			&i.ID,
			&i.NetPrice,
			&i.TotalPrice,
			&i.PaymentMethod,
			&i.Status,
			&i.UserID,
			&i.CreatedAt,
			&i.UpdatedAt,
			&i.OrderID,
			&i.ProductID,
			&i.ProductName,
			&i.ProductPrice,
			&i.ProductDiscount,
			&i.Quantity,
			&i.FinalPrice,
			&i.ID_2,
			&i.Name,
			&i.Description,
			&i.Brand,
			&i.ByRequest,
			&i.Discount,
			&i.Price,
			&i.Stock,
			&i.Issued,
			&i.Images,
			&i.CategoryID,
			&i.CreatedAt_2,
			&i.UpdatedAt_2,
			&i.DeletedAt,
		); err != nil {
			return nil, err
		}
		items = append(items, i)
	}
	if err := rows.Close(); err != nil {
		return nil, err
	}
	if err := rows.Err(); err != nil {
		return nil, err
	}
	return items, nil
}

const updateOrderStatus = `-- name: UpdateOrderStatus :exec
UPDATE orders SET 
    status = ?,
    updated_at = ?
WHERE id = ?
`

type UpdateOrderStatusParams struct {
	Status    string
	UpdatedAt string
	ID        string
}

func (q *Queries) UpdateOrderStatus(ctx context.Context, arg UpdateOrderStatusParams) error {
	_, err := q.db.ExecContext(ctx, updateOrderStatus, arg.Status, arg.UpdatedAt, arg.ID)
	return err
}
