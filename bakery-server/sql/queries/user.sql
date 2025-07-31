-- name: GetUsers :many
select *
from users
;

-- name: GetUserById :one
select *
from users
where id = ?
;

-- name: GetUser :one
select *
from users
where email = ? or username = ?
;

-- name: CreateUser :one
INSERT INTO users (
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
UPDATE users SET
    first_name = ?,
    last_name = ?,
    alias = ?,
    phone_number = ?,
    birth_date = ?,
    updated_at = ?
WHERE id = ?
RETURNING *;

-- name: UpdateEmail :one
UPDATE users SET
    email = ?,
    updated_at = ?
WHERE id = ?
RETURNING *;

-- name: UpdateUsername :one
UPDATE users SET
    username = ?,
    updated_at = ?
WHERE id = ?
RETURNING *;

-- name: DeleteUser :exec
UPDATE users SET 
    deleted_at = ?
WHERE id = ?;

