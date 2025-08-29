import { securedRoutes } from '@/common/routes';
import { actions } from 'astro:actions';
import { defineMiddleware } from 'astro:middleware';

export const auth = defineMiddleware((ctx, next) => {
  if (ctx.url.pathname.startsWith('/api/')) {
    // NOTE: do i need to protect this?
    return next();
  }

  if (securedRoutes.includes(ctx.url.pathname)) return verifySession(ctx, next);

  return next();
});

const verifySession = defineMiddleware(async ({ callAction, url }, next) => {
  const { data, error } = await callAction(actions.auth.getSession, null);
  // TODO: might need to do a check for a valid server session.
  if (!data && error) {
    return Response.redirect(new URL('/auth/signin', url));
  }

  const { data: userData, error: userError } = await callAction(
    actions.user.getUserFromSession,
    null
  );

  if (!userData && userError) {
    return Response.error();
  }

  if (url.pathname.startsWith('/admin') && userData.user?.role !== 'admin') {
    return Response.error();
  }
  return next();
});
