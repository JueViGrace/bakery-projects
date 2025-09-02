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
import { ActionError, defineAction } from 'astro:actions';
import { SERVER_URL } from 'astro:env/server';
import { z } from 'astro:schema';
import { refresher } from '@lib/app/Refresher';
import { isNonNullExpression } from 'typescript';

export const authActions = {
  authPing: defineAction({
    input: z.string(),
    handler: async (input) => {
      const req = await fetch(`${SERVER_URL}/api/auth/ping`, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${input}`,
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

      return true;
    },
  }),
  logOut: defineAction({
    input: z.string(),
    handler: async (input) => {
      const req = await fetch(`${SERVER_URL}/api/auth/logout`, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${input}`,
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

      return res as APIResponse<string>;
    },
  }),
  confirmPasswordReset: defineAction({
    input: z.string(),
    handler: async (input) => {
      const body: ConfirmPasswordReset = {
        code: input,
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

      return res as APIResponse<string>;
    },
  }),
  recoverPassword: defineAction({
    input: z
      .string()
      .min(4, { message: 'Password must be longer than 4 characters' })
      .max(255, {
        message: 'Password must not be longer than 255 characters',
      }),

    handler: async (input) => {
      const body: RecoverPasswordRequest = {
        new_password: input,
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

      return res as APIResponse<string>;
    },
  }),
  refresh: defineAction({
    input: z.string(),
    handler: async (input) => {
      const req = await fetch(`${SERVER_URL}/api/auth/refresh`, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${input}`,
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

      return res as APIResponse<AuthResponse>;
    },
  }),
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

      return res as APIResponse<AuthResponse>;
    },
  }),
  signUp: defineAction({
    input: z.object({
      firstNameField: z.string(),
      lastNameField: z.string(),
      emailField: z.string().email(),
      phoneNumberField: z
        .string()
        .refine(isValidPhoneNumber, { message: 'Phone number is not valid' }),
      birthDateField: z.number(),
      usernameField: z.string(),
      passwordField: z.string(),
    }),
    handler: async (input) => {
      const body: SignUpRequest = {
        first_name: input.firstNameField,
        last_name: input.lastNameField,
        email: input.emailField,
        phone_number: input.phoneNumberField,
        birth_date: input.birthDateField,
        username: input.usernameField ?? '',
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

      return res as APIResponse<AuthResponse>;
    },
  }),
  saveSession: defineAction({
    input: z.object({
      id: z.string(),
      accessToken: z.string(),
      refreshToken: z.string(),
    }),
    handler: async (input, ctx) => {
      let inputSession: Session = {
        id: input.id,
        accessToken: input.accessToken,
        refreshToken: input.refreshToken,
      };
      const savedSession: Session | undefined =
        await ctx.session?.get<Session>('session');

      if (savedSession && savedSession.user) {
        inputSession.user = savedSession.user;
      }

      ctx.session?.set<Session>('session', inputSession);
      refresher.updateLastRefresh(new Date());
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

      return session;
    },
  }),
  deleteSession: defineAction({
    handler: async (_, ctx) => {
      ctx.session?.delete('session');
      refresher.resetRefresh();
    },
  }),
};
