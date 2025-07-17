package types

import (
	"fmt"
	"time"

	"github.com/JueViGrace/bakery-server/internal/database"
	"github.com/JueViGrace/bakery-server/internal/util"
	"github.com/gofiber/fiber/v2"
	"github.com/google/uuid"
)

type AuthDataHandler = func(*fiber.Ctx, *AuthData) error

type AuthData struct {
	UserId    uuid.UUID
	SessionId uuid.UUID
	Username  string
	Role      string
}

type AuthResponse struct {
	ID           uuid.UUID `json:"id"`
	AccessToken  string    `json:"access_token"`
	RefreshToken string    `json:"refresh_token"`
}

type SignInRequest struct {
	Email    string `json:"email" validate:"required,min=1,max=255"`
	Password string `json:"password" validate:"required,min=1,max=255"`
}

type SignUpRequest struct {
	FirstName   string `json:"first_name" validate:"required,min=1,max=255"`
	LastName    string `json:"last_name" validate:"required,min=1,max=255"`
	PhoneNumber string `json:"phone_number" validate:"required,min=1,max=20"`
	BirthDate   string `json:"birth_date" validate:"required,min=1,max=50"`
	Address     string `json:"address" validate:"required,min=1,max=255"`
	Email       string `json:"email" validate:"required,email,max=255"`
	Username    string `json:"username" validate:"max=255"`
	Password    string `json:"password" validate:"required,min=1,max=255"`
}

type RefreshRequest struct {
	RefreshToken string `json:"refresh_token" validate:"required,min=1,max=255"`
}

type RecoverPasswordRequest struct {
	Password string `json:"password" validate:"required,min=1,max255"`
}

func SignUpRequestToDbUser(r *SignUpRequest) (*database.CreateUserParams, error) {
	id, err := uuid.NewV7()
	if err != nil {
		return nil, err
	}

	pass, err := util.HashPassword(r.Password)
	if err != nil {
		return nil, err
	}

	birthDate, err := time.Parse(time.DateOnly, r.BirthDate)
	if err != nil {
		return nil, err
	}

	username := r.Username
	if username == "" {
		username = r.Email
	}

	return &database.CreateUserParams{
		ID:          id.String(),
		FirstName:   r.FirstName,
		LastName:    r.LastName,
		Alias:       fmt.Sprintf("%s %s", r.FirstName, r.LastName),
		Username:    username,
		Email:       r.Email,
		Password:    pass,
		PhoneNumber: r.PhoneNumber,
		BirthDate:   util.FormatDateForResponse(birthDate),
		Address1:    r.Address,
		CreatedAt:   time.Now().UTC().String(),
		UpdatedAt:   time.Now().UTC().String(),
	}, nil
}
