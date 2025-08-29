import { defineAction } from 'astro:actions';
import type { User } from '@user/types';

export const userActions = {
  getUserFromSession: defineAction({
    handler: async (_, ctx) => {
      const user: User | undefined = await ctx.session?.get<User>('user');

      return { user: user };
    },
  }),
};
