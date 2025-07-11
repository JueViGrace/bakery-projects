package com.bakery.core.types.state

enum class AppCodes(
    val value: Int,
) {
    Message(100),
    Ok(200),
    Created(201),
    Accepted(202),
    NoContent(204),
    BadRequest(400),
    Unauthorized(401),
    Forbidden(403),
    NotFound(404),
    MethodNotAllowed(405),
    NotAcceptable(406),
    RequestTimeout(408),
    Conflict(409),
    UnsupportedMediaType(415),
    InternalServerError(500),
    ServiceUnavailable(503),
    UnexpectedError(600),
    DatabaseError(601),
    NullError(602),
    VersionError(603),
    UnknownError(604),
}
