import { ActionError, defineAction } from 'astro:actions';
import { USER_ROLES, type User, type UserResponse } from '@user/types';
import { z } from 'astro:schema';
import { SERVER_URL } from 'astro:env/server';
import type { APIResponse } from '@/env';

export const userActions = {
  getUserData: defineAction({
    handler: async (_, ctx) => {
      return await ctx.session?.get<User>('user');
    },
  }),
  requestUserData: defineAction({
    input: z.object({
      token: z.string(),
    }),
    handler: async (input) => {
      const req = await fetch(`${SERVER_URL}/api/users/me`, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${input.token}`,
        },
        method: 'GET',
      });

      const res: APIResponse<UserResponse | null> = await req.json();
      if (!req.ok) {
        throw new ActionError({
          message: res.message,
          code: ActionError.statusToCode(res.status),
        });
      }

      return res as APIResponse<UserResponse>;
    },
  }),
  saveUserData: defineAction({
    input: z.object({
      id: z.string(),
      firstName: z.string(),
      lastName: z.string(),
      displayName: z.string(),
      username: z.string(),
      email: z.string(),
      phoneNumber: z.string(),
      birthDate: z.string(),
      profileImg: z.string(),
      role: z.enum(USER_ROLES),
    }),
    handler: async (input, ctx) => {
      ctx.session?.set<User>('user', input as User);
    },
  }),
};
