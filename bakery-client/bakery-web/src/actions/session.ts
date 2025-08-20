import type { Session } from '@/env';
import { defineAction } from 'astro:actions';
import { z } from 'astro:schema';

export const session = {
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
      return { session: session };
    },
  }),
  deleteSession: defineAction({
    handler: async (_, ctx) => {
      ctx.session?.delete('session');
    },
  }),
};
