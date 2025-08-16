package types

import (
	"fmt"
	"strings"
	"time"

	"github.com/JueViGrace/bakery-server/internal/database"
	"github.com/JueViGrace/bakery-server/internal/util"
	"github.com/google/uuid"
)

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
	Username string `json:"username" validate:"required,min=1,max=255"`
	Password string `json:"password" validate:"required,min=1,max=255"`
}

type SignUpRequest struct {
	FirstName   string `json:"first_name" validate:"required,min=1,max=255"`
	LastName    string `json:"last_name" validate:"required,min=1,max=255"`
	PhoneNumber string `json:"phone_number" validate:"required,min=1,max=20"`
	BirthDate   string `json:"birth_date" validate:"required,min=1,max=50"`
	Email       string `json:"email" validate:"required,email,max=255"`
	Username    string `json:"username" validate:"max=255"`
	Password    string `json:"password" validate:"required,min=1,max=255"`
}

type RequestPasswordReset struct {
	Email string `json:"email" validate:"required,min=1,max=255"`
}

type ConfirmPasswordReset struct {
	Code string `json:"code" validate:"required,min=1,max=255"`
}

type RecoverPasswordRequest struct {
	Password    string `json:"password" validate:"required,min=1,max255"`
	NewPassword string `json:"new_password" validate:"required,min=1,max255"`
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

	firstName := strings.TrimSpace(r.FirstName)
	lastName := strings.TrimSpace(r.LastName)

	birthDate, err := time.Parse(time.DateOnly, r.BirthDate)
	if err != nil {
		return nil, err
	}

	username := strings.TrimSpace(r.Username)
	if username == "" {
		username = r.Email
	}

	return &database.CreateUserParams{
		ID:          id.String(),
		FirstName:   firstName,
		LastName:    lastName,
		Alias:       fmt.Sprintf("%s %s", firstName, lastName),
		Username:    username,
		Email:       r.Email,
		Password:    pass,
		PhoneNumber: r.PhoneNumber,
		BirthDate:   util.FormatDateForResponse(birthDate),
		CreatedAt:   time.Now().UTC().String(),
		UpdatedAt:   time.Now().UTC().String(),
	}, nil
}
