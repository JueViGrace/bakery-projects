import { refresher } from '@/lib/app/Refresher';
import { type APIContext } from 'astro';
import { ActionError, actions } from 'astro:actions';

export async function GET({ callAction }: APIContext): Promise<Response> {
  try {
    const { data: session, error: sessionErr } = await callAction(
      actions.auth.getSession,
      null
    );
    if (!session && sessionErr) {
      throw sessionErr;
    }

    if (refresher.shouldRefresh()) {
      const refreshReq = await fetch('/api/auth/refresh', {
        headers: {
          'Content-Type': 'application/json',
        },
        method: 'GET',
      });

      if (!refreshReq.ok) {
        const refreshErr: ActionError = await refreshReq.json();
        throw refreshErr;
      }
    } else {
      const { error: pingError } = await callAction(
        actions.auth.authPing,
        session.accessToken
      );

      if (pingError) {
        const logOutReq = await fetch('/api/auth/logout', {
          headers: { 'Content-Type': 'application/json' },
          method: 'GET',
        });

        if (!logOutReq.ok) {
          const logOutErr: ActionError = await logOutReq.json();
          throw logOutErr;
        }
        throw pingError;
      }
    }
    return new Response(JSON.stringify(true), {
      headers: { 'Content-Type': 'application/json' },
    });
  } catch (e) {
    let body: ActionError;
    if (e instanceof ActionError) {
      body = {
        ...e,
        message: e.message,
      };
    } else {
      body = new ActionError({
        message: `${e}`,
        code: 'INTERNAL_SERVER_ERROR',
      });
    }

    return new Response(JSON.stringify(body), {
      status: body.status,
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }
}
