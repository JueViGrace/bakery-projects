-- name: GetSessionById :one
select *
from sessions
where id = ?
;

-- name: GetSessionByUser :many
select *
from sessions
where user_id = ?
;

-- name: GetSessionByUsername :many
select *
from sessions
where username = ?
;

-- name: CreateSession :exec
insert into sessions(
    id,
    refresh_token,
    access_token,
    username,
    user_id
)
values (?, ?, ?, ?, ?);

-- name: UpdateSession :exec
update sessions set
    refresh_token = ?,
    access_token = ?,
    username = ?
where id = ?;

-- name: DeleteSessionById :exec
delete from sessions
where id = ?
;

-- name: DeleteSessionByUser :exec
delete from sessions
where user_id = ?
;

-- name: DeleteSessionByToken :exec
delete from sessions
where refresh_token = ? or access_token = ?
;

