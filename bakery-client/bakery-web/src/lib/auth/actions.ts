import type { APIResponse } from '@/env';
import type {
  AuthResponse,
  Session,
  SignInRequest,
  SignUpRequest,
} from '@auth/types';
import { isValidPhoneNumber } from 'react-phone-number-input';
import { ActionError, defineAction } from 'astro:actions';
import { SERVER_URL } from 'astro:env/server';
import { z } from 'astro:schema';

export const auth = {
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
      try {
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
            message: res.message,
            code: ActionError.statusToCode(res.status),
          });
        }

        return { res: res as APIResponse<AuthResponse> };
      } catch (e) {
        throw new ActionError({
          message: `Unnexpected error: ${e}`,
          code: 'INTERNAL_SERVER_ERROR',
        });
      }
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

      try {
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
      } catch (e) {
        throw new ActionError({
          message: `Unnexpected error: ${e}`,
          code: 'INTERNAL_SERVER_ERROR',
        });
      }
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
          'x-redirect-to': '/',
          Authorization: `Bearer ${input.token}`,
        },
        method: 'POST',
      });

      if (!req.ok) {
        throw ActionError.fromJson(await req.json());
      }

      return;
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
