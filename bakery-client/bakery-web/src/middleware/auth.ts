import { securedRoutesList } from '@/common/routes';
import type { User } from '@user/types';
import { BASE_URL } from 'astro:env/server';
import { defineMiddleware, sequence } from 'astro:middleware';

const sessionMiddleware = defineMiddleware(async (ctx, next) => {
  if (securedRoutesList.includes(ctx.url.pathname)) {
    const userReq = await fetch(`${BASE_URL}/api/users/me`, {
      headers: {
        'Content-Type': 'application/json',
      },
      method: 'GET',
    });

    if (!userReq.ok) {
      return Response.redirect(new URL('/auth/signin', ctx.url));
    }

    const user: User = await userReq.json();

    const isAdminRoute =
      ctx.url.pathname.split('/').filter((str) => str !== '')[0] === 'admin';

    if (isAdminRoute && user?.role !== 'admin') {
      return Response.redirect(new URL('/', ctx.url));
    }
  }

  return next();
});

export const authMiddleware = sequence(sessionMiddleware);
