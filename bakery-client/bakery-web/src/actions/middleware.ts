import {
  ACTION_ERROR_CODES,
  defineAction,
  type ActionErrorCode,
} from 'astro:actions';
import { z } from 'astro:schema';

export const middleware = {
  saveErrorRes: defineAction({
    input: z.object({
      code: z.enum(ACTION_ERROR_CODES),
      status: z.number(),
      message: z.string(),
    }),
    handler: async (input, ctx) => {},
  }),
};
