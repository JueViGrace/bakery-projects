import { isValidPhoneNumber } from 'react-phone-number-input';
import { z } from 'zod';
import type { User } from '@user/types';

// App
export type Session = {
  id: string;
  accessToken: string;
  refreshToken: string;
  user: User | null;
};

// Schemas
export const signInFormSchema = z.object({
  username: z
    .string()
    .min(4, { error: 'Username must be longer than 4 characters' })
    .max(255, { error: 'Username must not be longer than 255 characters' })
    .nonoptional(),
  password: z
    .string()
    .min(4, { error: 'Password must be longer than 4 characters' })
    .max(255, { error: 'Password must not be longer than 255 characters' })
    .nonoptional(),
});

export type SignInFormSchema = z.infer<typeof signInFormSchema>;

export const signUpFormSchema = z.object({
  firstNameField: z
    .string()
    .min(2, { error: 'First name must be longer than 2 characters' })
    .max(255, { error: 'First name must not be longer than 255 characters' }),
  lastNameField: z
    .string()
    .min(2, { error: 'Last name must be longer than 2 characters' })
    .max(255, { error: 'Last name must not be longer than 255 characters' }),
  emailField: z.email(),
  phoneNumberField: z
    .string()
    .refine(isValidPhoneNumber, { message: 'Phone number is not valid' }),
  birthDateField: z.date(),
  usernameField: z
    .string()
    .min(4, { error: 'Username must be longer than 4 characters' })
    .max(255, { error: 'Usernam must not be longer than 255 characters' })
    .optional(),
  passwordField: z
    .string()
    .min(4, { error: 'Password must be longer than 4 characters' })
    .max(255, { error: 'Password must not be longer than 255 characters' }),
  confirmPasswordField: z
    .string()
    .min(4, { error: 'Password must be longer than 4 characters' })
    .max(255, { error: 'Password must not be longer than 255 characters' }),
});

export type SignUpFormSchema = z.infer<typeof signUpFormSchema>;

// Requests
export type SignInRequest = {
  username: string;
  password: string;
};

export type SignInErrors = {
  username_error: string;
  password_error: string;
};

export type SignUpRequest = {
  first_name: string;
  last_name: string;
  phone_number: string;
  birth_date: number;
  email: string;
  username: string;
  password: string;
};

export type RequestPasswordReset = {
  email: string;
};

export type ConfirmPasswordReset = {
  code: string;
};

export type RecoverPasswordRequest = {
  new_password: string;
};

// Responses
export type AuthResponse = {
  id: string;
  access_token: string;
  refresh_token: string;
};
