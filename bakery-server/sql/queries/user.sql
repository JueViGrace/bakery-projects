-- name: GetUsers :many
select *
from bakery_user
;

-- name: GetUserById :one
select *
from bakery_user
where id = ?
;

-- name: GetUser :one
select *
from bakery_user
where email = ? or username = ?
;

-- name: CreateUser :one
INSERT INTO bakery_user (
    id,
    first_name,
    last_name,
    alias,
    username,
    email,
    password,
    phone_number,
    birth_date,
    created_at,
    updated_at
)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
RETURNING *;

-- name: UpdateUser :one
UPDATE bakery_user SET
    first_name = ?,
    last_name = ?,
    alias = ?,
    phone_number = ?,
    birth_date = ?,
    updated_at = ?
WHERE id = ?
RETURNING *;

-- name: UpdateEmail :one
UPDATE bakery_user SET
    email = ?,
    updated_at = ?
WHERE id = ?
RETURNING *;

-- name: UpdateUsername :one
UPDATE bakery_user SET
    username = ?,
    updated_at = ?
WHERE id = ?
RETURNING *;

-- name: DeleteUser :exec
UPDATE bakery_user SET 
    deleted_at = ?
WHERE id = ?;

