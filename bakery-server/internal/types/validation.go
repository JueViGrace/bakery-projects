package types

type ValidationErrorResponse struct {
	FailedField string
	Tag         string
	Value       any
}
