package data

import (
	"context"
	"errors"
	"fmt"

	"github.com/JueViGrace/bakery-server/internal/database"
	"github.com/JueViGrace/bakery-server/internal/types"
	"github.com/JueViGrace/bakery-server/internal/util"
	"github.com/google/uuid"
)

type AuthStore interface {
	LogOut(sessionId uuid.UUID) (msg string, err error)
	Ping(sessionId uuid.UUID) (valid bool, err error)
	SignIn(r *types.SignInRequest) (res *types.AuthResponse, err error)
	SignUp(r *types.SignUpRequest) (res *types.AuthResponse, err error)
	Refresh(a *types.AuthData) (res *types.AuthResponse, err error)
	RecoverPassword(r *types.RecoverPasswordRequest) (msg string, err error)
}

func (s *storage) AuthStore() AuthStore {
	return NewAuthStore(s.ctx, s.queries)
}

type authStore struct {
	ctx context.Context
	db  *database.Queries
}

func NewAuthStore(ctx context.Context, db *database.Queries) AuthStore {
	return &authStore{
		ctx: ctx,
		db:  db,
	}
}

func (s *authStore) LogOut(sessionId uuid.UUID) (string, error) {
	err := s.db.DeleteSessionById(s.ctx, sessionId.String())
	if err != nil {
		return "", fmt.Errorf("session with id %s not found", sessionId.String())
	}

	return "Logged out!", nil
}

func (s *authStore) Ping(sessionId uuid.UUID) (bool, error) {
	_, err := s.db.GetSessionById(s.ctx, sessionId.String())
	if err != nil {
		return false, err
	}

	return true, nil
}

// TODO: limit sessions to 5?
func (s *authStore) SignIn(r *types.SignInRequest) (*types.AuthResponse, error) {
	user, err := s.db.GetUser(s.ctx, database.GetUserParams{
		Email:    r.Username,
		Username: r.Username,
	})
	if err != nil {
		return nil, errors.New("user not found")
	}

	if user.DeletedAt.Valid {
		return nil, errors.New("this user was deleted")
	}

	if !util.ValidatePassword(r.Password, user.Password) {
		return nil, errors.New("invalid credentials")
	}

	userId, err := uuid.Parse(user.ID)
	if err != nil {
		return nil, err
	}

	sessionId, err := uuid.NewV7()
	if err != nil {
		return nil, err
	}

	newTokens, err := createTokens(userId, sessionId, user.Username)
	if err != nil {
		return nil, err
	}

	err = s.db.CreateSession(s.ctx, types.CreateSessionToDb(&types.Session{
		ID:           sessionId,
		UserId:       userId,
		Username:     user.Username,
		RefreshToken: newTokens.RefreshToken,
		AccessToken:  newTokens.AccessToken,
	}))
	if err != nil {
		return nil, err
	}

	return newTokens, nil
}

// TODO: check for conflicts
func (s *authStore) SignUp(r *types.SignUpRequest) (*types.AuthResponse, error) {
	newUser, err := types.SignUpRequestToDbUser(r)
	if err != nil {
		return nil, err
	}

	user, err := s.db.CreateUser(s.ctx, *newUser)
	if err != nil {
		return nil, err
	}

	userId, err := uuid.Parse(user.ID)
	if err != nil {
		return nil, err
	}

	sessionId, err := uuid.NewV7()
	if err != nil {
		return nil, err
	}

	newTokens, err := createTokens(userId, sessionId, user.Username)
	if err != nil {
		return nil, err
	}

	err = s.db.CreateSession(s.ctx, types.CreateSessionToDb(&types.Session{
		ID:           sessionId,
		UserId:       userId,
		Username:     user.Username,
		RefreshToken: newTokens.RefreshToken,
		AccessToken:  newTokens.AccessToken,
	}))
	if err != nil {
		return nil, err
	}

	return newTokens, nil
}

func (s *authStore) Refresh(a *types.AuthData) (*types.AuthResponse, error) {
	user, err := s.db.GetUserById(s.ctx, a.UserId.String())
	if err != nil {
		return nil, err
	}

	userId, err := uuid.Parse(user.ID)
	if err != nil {
		return nil, err
	}

	newTokens, err := createTokens(userId, a.SessionId, user.Username)
	if err != nil {
		return nil, err
	}

	err = s.db.UpdateSession(s.ctx, types.UpdateSessionToDb(
		&types.Session{
			UserId:       userId,
			Username:     user.Username,
			RefreshToken: newTokens.RefreshToken,
			AccessToken:  newTokens.AccessToken,
		},
	))
	if err != nil {
		return nil, err
	}

	return newTokens, nil
}

// TODO: implement this
func (s *authStore) RecoverPassword(r *types.RecoverPasswordRequest) (string, error) {
	return "", nil
}

func createTokens(userId, sessionId uuid.UUID, username string) (*types.AuthResponse, error) {
	accessToken, err := util.CreateAccessToken(userId, sessionId, username)
	if err != nil {
		return nil, err
	}

	refreshToken, err := util.CreateRefreshToken(userId, sessionId, username)
	if err != nil {
		return nil, err
	}

	return &types.AuthResponse{
		ID:           sessionId,
		AccessToken:  accessToken,
		RefreshToken: refreshToken,
	}, nil
}
