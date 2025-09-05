import { type APIContext } from 'astro';
import { ActionError, actions } from 'astro:actions';

export async function POST({
  callAction,
  request,
}: APIContext): Promise<Response> {
  try {
    if (request.headers.has('x-call-server-logout')) {
      const { data: sessionData, error: sessionError } = await callAction(
        actions.session.getSession,
        null
      );
      if (!sessionData && sessionError) {
        throw sessionError;
      }

      const { error } = await callAction(actions.auth.logOut, {
        token: sessionData.accessToken,
      });
      if (error && error.code !== 'UNAUTHORIZED') {
        throw error;
      }
    }

    const { data: deleteSession, error: deleteSessionError } = await callAction(
      actions.session.deleteSession,
      null
    );
    if (deleteSession && deleteSessionError) {
      throw deleteSessionError;
    }

    return new Response('Success', {
      status: 200,
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
