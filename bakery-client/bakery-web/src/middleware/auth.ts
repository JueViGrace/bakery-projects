import { publicRoutes } from '@/common/routes';
import type { Session } from '@/lib/auth/types';
import type { APIContext } from 'astro';
import { actions } from 'astro:actions';
import { defineMiddleware } from 'astro:middleware';

export const authMiddleware = defineMiddleware((ctx, next) => {
  if (
    publicRoutes.includes(ctx.url.pathname) ||
    ctx.url.pathname.split('/').includes('partials')
  ) {
    return next();
  }

  if (ctx.url.pathname.startsWith('/api')) {
    // NOTE: do i need to protect this?
    return next();
  }

  return verifySession(ctx, next);
});

const verifySession = defineMiddleware(async (ctx, next) => {
  const redirect = (path: string) => {
    return Response.redirect(new URL(path, ctx.url));
  };

  const session = await checkValidSession(ctx);

  if (!session) {
    await ctx.callAction(actions.auth.deleteSession, null);
    return redirect('/auth/signin');
  }

  const isAdminRoute =
    ctx.url.pathname.split('/').filter((str) => str !== '')[0] === 'admin';
  if (isAdminRoute && session.user?.role !== 'admin') {
    return redirect('/');
  }

  return next();
});

async function checkValidSession({
  callAction,
}: APIContext): Promise<Session | null> {
  const { data, error } = await callAction(actions.auth.getSession, null);

  if (!data && error) {
    return null;
  }

  const { error: pingError } = await callAction(
    actions.auth.authPing,
    data.accessToken
  );

  if (pingError) {
    return null;
  }

  return data;
}
