-- name: GetOrders :many
select *
from orders
left join order_products on orders.id = order_products.id
left join products on order_products.product_id = products.id
where orders.deleted_at is null
;

-- name: GetOrdersByUser :many
select *
from orders
left join order_products on orders.id = order_products.id
left join products on order_products.product_id = products.id
where orders.user_id = ? and orders.deleted_at is null
;

-- name: GetOrderById :one
select *
from orders
left join order_products on orders.id = order_products.id
left join products on order_products.product_id = products.id
where orders.id = ? and orders.deleted_at is null
;

-- name: CreateOrder :exec
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
VALUES (?, ?, ?, ?, ?, ?, ?, ?);

-- name: CreateOrderProducts :exec
INSERT INTO order_products(
    order_id,
    product_id,
    product_name,
    product_price,
    product_discount,
    final_price,
    quantity
)
VALUES (?, ?, ?, ?, ?, ?, ?);

-- name: UpdateOrderStatus :exec
UPDATE orders SET 
    status = ?,
    updated_at = ?
WHERE id = ?;

-- name: CancelOrder :exec
UPDATE orders SET 
    status = "CANCELLED",
    updated_at = ?
WHERE id = ?;

