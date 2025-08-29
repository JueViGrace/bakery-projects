import type { APIResponse } from '@/env';
import type {
  AuthResponse,
  ConfirmPasswordReset,
  RecoverPasswordRequest,
  RequestPasswordReset,
  Session,
  SignInRequest,
  SignUpRequest,
} from '@auth/types';
import { isValidPhoneNumber } from 'react-phone-number-input';
import { ActionError, actions, defineAction } from 'astro:actions';
import { SERVER_URL } from 'astro:env/server';
import { z } from 'astro:schema';
import type { User } from '../user/types';

export const authActions = {
  signIn: defineAction({
    input: z.object({
      username: z
        .string()
        .min(4, { message: 'Username must be longer than 4 characters' })
        .max(255, {
          message: 'Username must not be longer than 255 characters',
        }),
      password: z
        .string()
        .min(4, { message: 'Password must be longer than 4 characters' })
        .max(255, {
          message: 'Password must not be longer than 255 characters',
        }),
    }),
    handler: async (input) => {
      const body: SignInRequest = {
        username: input.username,
        password: input.password,
      };
      const req = await fetch(`${SERVER_URL}/api/auth/signin`, {
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
        method: 'POST',
      });

      const res: APIResponse<AuthResponse | null> = await req.json();
      if (!req.ok) {
        throw new ActionError({
          message: req.status === 404 ? 'Invalid credentials' : res.message,
          code: ActionError.statusToCode(res.status),
        });
      }

      return { res: res as APIResponse<AuthResponse> };
    },
  }),
  signUp: defineAction({
    input: z.object({
      firstNameField: z
        .string()
        .min(2, { message: 'First name must be longer than 2 characters' })
        .max(255, {
          message: 'First name must not be longer than 255 characters',
        }),
      lastNameField: z
        .string()
        .min(2, { message: 'Last name must be longer than 2 characters' })
        .max(255, {
          message: 'Last name must not be longer than 255 characters',
        }),
      emailField: z.string().email(),
      phoneNumberField: z
        .string()
        .refine(isValidPhoneNumber, { message: 'Phone number is not valid' }),
      birthDateField: z.number(),
      usernameField: z
        .string()
        .min(4, { message: 'Username must be longer than 4 characters' })
        .max(255, {
          message: 'Usernam must not be longer than 255 characters',
        }),
      passwordField: z
        .string()
        .min(4, { message: 'Password must be longer than 4 characters' })
        .max(255, {
          message: 'Password must not be longer than 255 characters',
        }),
    }),
    handler: async (input) => {
      const body: SignUpRequest = {
        first_name: input.firstNameField,
        last_name: input.lastNameField,
        email: input.emailField,
        phone_number: input.phoneNumberField,
        birth_date: input.birthDateField,
        username: input.usernameField,
        password: input.passwordField,
      };

      const req = await fetch(`${SERVER_URL}/api/auth/signup`, {
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
        method: 'POST',
      });

      const res: APIResponse<AuthResponse | null> = await req.json();
      if (!req.ok) {
        throw new ActionError({
          message: res.message,
          code: ActionError.statusToCode(res.status),
        });
      }

      return { res: res as APIResponse<AuthResponse> };
    },
  }),
  logOut: defineAction({
    input: z.object({
      token: z.string(),
    }),
    handler: async (input) => {
      const req = await fetch(`${SERVER_URL}/api/auth/logout`, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${input.token}`,
        },
        method: 'POST',
      });

      if (!req.ok) {
        const res: APIResponse<null> = await req.json();
        throw new ActionError({
          message: res.message,
          code: ActionError.statusToCode(res.status),
        });
      }

      return;
    },
  }),
  refresh: defineAction({
    handler: async () => {
      const { data, error } = await actions.auth.getSession();

      if (!data && error) {
        throw error;
      }

      const req = await fetch(`${SERVER_URL}/api/auth/refresh`, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${data.session.refreshToken}`,
        },
        method: 'POST',
      });

      const res: APIResponse<AuthResponse | null> = await req.json();
      if (!req.ok) {
        throw new ActionError({
          message: res.message,
          code: ActionError.statusToCode(res.status),
        });
      }

      return { res: res as APIResponse<AuthResponse> };
    },
  }),
  requestPasswordReset: defineAction({
    input: z.object({
      email: z.string().email(),
    }),
    handler: async (input) => {
      const body: RequestPasswordReset = {
        email: input.email,
      };

      const req = await fetch(
        `${SERVER_URL}/api/auth/recover/password/request`,
        {
          headers: {
            'Content-Type': 'application/json',
          },
          method: 'POST',
          body: JSON.stringify(body),
        }
      );

      const res: APIResponse<string | null> = await req.json();
      if (!req.ok) {
        throw new ActionError({
          message: res.message,
          code: ActionError.statusToCode(res.status),
        });
      }

      return { res: res as APIResponse<string> };
    },
  }),
  confirmPasswordReset: defineAction({
    input: z.object({
      code: z.string(),
    }),
    handler: async (input) => {
      const body: ConfirmPasswordReset = {
        code: input.code,
      };

      const req = await fetch(
        `${SERVER_URL}/api/auth/recover/password/confirm`,
        {
          headers: {
            'Content-Type': 'application/json',
          },
          method: 'POST',
          body: JSON.stringify(body),
        }
      );

      const res: APIResponse<string | null> = await req.json();
      if (!req.ok) {
        throw new ActionError({
          message: res.message,
          code: ActionError.statusToCode(res.status),
        });
      }

      return { res: res as APIResponse<string> };
    },
  }),
  recoverPassword: defineAction({
    input: z.object({
      newPassword: z
        .string()
        .min(4, { message: 'Password must be longer than 4 characters' })
        .max(255, {
          message: 'Password must not be longer than 255 characters',
        }),
    }),
    handler: async (input) => {
      const body: RecoverPasswordRequest = {
        new_password: input.newPassword,
      };

      const req = await fetch(`${SERVER_URL}/api/auth/recover/password`, {
        headers: {
          'Content-Type': 'application/json',
        },
        method: 'POST',
        body: JSON.stringify(body),
      });

      const res: APIResponse<string | null> = await req.json();
      if (!req.ok) {
        throw new ActionError({
          message: res.message,
          code: ActionError.statusToCode(res.status),
        });
      }

      return { res: res as APIResponse<string> };
    },
  }),
  saveSession: defineAction({
    input: z.object({
      id: z.string(),
      accessToken: z.string(),
      refreshToken: z.string(),
    }),
    handler: async (input, ctx) => {
      ctx.session?.set<Session>('session', {
        id: input.id,
        accessToken: input.accessToken,
        refreshToken: input.refreshToken,
      });
    },
  }),
  getSession: defineAction({
    handler: async (_, ctx) => {
      const session: Session | undefined =
        await ctx.session?.get<Session>('session');

      if (!session) {
        throw new ActionError({
          message: 'Session not found',
          code: 'NOT_FOUND',
        });
      }

      return { session: session };
    },
  }),
  deleteSession: defineAction({
    handler: async (_, ctx) => {
      ctx.session?.delete('session');
    },
  }),
};
