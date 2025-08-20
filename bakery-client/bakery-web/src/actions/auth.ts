import type { Session } from '@/env';
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
    handler: async (input, ctx) => {},
  }),
  signUp: defineAction({
    input: z.object({
      firstName: z.string(),
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
    handler: async (input, ctx) => {},
  }),
  logOut: defineAction({
    handler: async (_, ctx) => {
      const session: Session | undefined =
        await ctx.session?.get<Session>('session');
      if (!session) {
        throw new ActionError({
          message: 'Session is undefined',
          code: 'UNAUTHORIZED',
        });
      }
      const req = await fetch(`${SERVER_URL}/api/auth/logout`, {
        headers: {
          'Content-Type': 'application/json',
          'x-redirect-to': '/',
          Authorization: `Bearer ${session.accessToken}`,
        },
        method: 'POST',
      });

      if (!req.ok) {
        throw new ActionError({
          message: 'Unable to log out, did you sign in?',
          code: 'UNAUTHORIZED',
        });
      }

      return;
    },
  }),
};
