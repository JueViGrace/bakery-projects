import type { APIContext } from 'astro';
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

    const onLogOut = async (headers: HeadersInit = {}) => {
      const logOutReq = await fetch('/api/auth/logout', {
        headers: { 'Content-Type': 'application/json', ...headers },
        method: 'GET',
      });

      if (!logOutReq.ok) {
        const logOutErr: ActionError = await logOutReq.json();
        throw logOutErr;
      }
    };

    const { data: refreshData, error: refreshError } = await callAction(
      actions.auth.refresh,
      session.refreshToken
    );

    if (refreshError) {
      onLogOut();
      throw refreshError;
    }

    const { error: saveSessionErr } = await callAction(
      actions.auth.saveSession,
      {
        id: refreshData.data.id,
        accessToken: refreshData.data.access_token,
        refreshToken: refreshData.data.refresh_token,
      }
    );

    if (saveSessionErr) {
      onLogOut({ 'x-call-server-logout': '' });
      throw saveSessionErr;
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
