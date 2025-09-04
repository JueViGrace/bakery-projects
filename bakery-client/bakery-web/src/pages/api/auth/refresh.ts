import type { Session } from '@auth/types';
import { type APIContext } from 'astro';
import { ActionError, actions } from 'astro:actions';

export async function POST({
  request,
  callAction,
}: APIContext): Promise<Response> {
  try {
    const body: Session = await request.json();

    const { data: refreshData, error: refreshErr } = await callAction(
      actions.auth.refresh,
      {
        refreshToken: body.refreshToken,
      }
    );
    if (!refreshData && refreshErr) {
      throw refreshErr;
    }

    const { error: saveErr } = await callAction(actions.session.saveSession, {
      id: refreshData.data.id,
      accessToken: refreshData.data.access_token,
      refreshToken: refreshData.data.refresh_token,
    });
    if (saveErr) {
      throw saveErr;
    }

    return new Response('Success', {
      status: refreshData.status,
      headers: {
        'Content-Type': 'application/json',
      },
    });
  } catch (e) {
    let body: ActionError;
    if (e instanceof ActionError) {
      body = {
        ...e,
        message: e.message,
      };
    } else {
      const err = new ActionError({
        code: 'INTERNAL_SERVER_ERROR',
      });
      body = {
        ...err,
        message: `${e}`,
      };
    }

    return new Response(JSON.stringify(body), {
      status: body.status,
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }
}
