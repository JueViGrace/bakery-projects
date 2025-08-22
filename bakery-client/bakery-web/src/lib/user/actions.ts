import { defineAction } from 'astro:actions';

export const userActions = {
  getUser: defineAction({
    handler: async (_, ctx) => {},
  }),
};
