-- name: GetProducts :many
select *
from products
where products.deleted_at is null
;

-- name: GetProductById :one
select *
from products
where products.id = ? and products.deleted_at is null
;

-- name: CreateProduct :one
INSERT INTO products (
    id,
    name,
    description,
    brand,
    by_request,
    discount,
    price,
    stock,
    issued,
    images,
    category_id,
    created_at,
    updated_at
)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
RETURNING *;

-- name: UpdateProductName :one
UPDATE products SET
    name = ?,
    updated_at = ?
WHERE id = ?
RETURNING *;

-- name: UpdateProduct :one
UPDATE products SET
    description = ?,
    brand = ?,
    by_request = ?,
    discount = ?,
    price = ?,
    stock = ?,
    issued = ?,
    images = ?,
    category_id = ?,
    updated_at = ?
WHERE id = ?
RETURNING *;

-- name: DeleteProduct :exec
UPDATE products SET 
    deleted_at = ?
WHERE id = ?;

