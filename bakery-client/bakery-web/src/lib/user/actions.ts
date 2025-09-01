import { actions, defineAction } from 'astro:actions';
import { USER_ROLES } from '@user/types';
import { z } from 'astro:schema';
import type { Session } from '../auth/types';

export const userActions = {
  saveUserData: defineAction({
    input: z.object({
      firstName: z.string(),
      lastName: z.string(),
      displayName: z.string(),
      username: z.string(),
      email: z.string(),
      phoneNumber: z.string(),
      birthDate: z.string(),
      profileImgUrl: z.string(),
      role: z.enum(USER_ROLES),
    }),
    handler: async (input, ctx) => {
      const { data, error } = await actions.auth.getSession();
      if (!data && error) {
        throw error;
      }

      ctx.session?.set<Session>('session', {
        id: data.id,
        accessToken: data.accessToken,
        refreshToken: data.refreshToken,
        user: input,
      });
    },
  }),
};
