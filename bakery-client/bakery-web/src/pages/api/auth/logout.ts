import { type APIContext } from 'astro';
import { ActionError, actions } from 'astro:actions';

export async function GET({
  callAction,
  request,
}: APIContext): Promise<Response> {
  try {
    if (request.headers.has('x-call-server-logout')) {
      const { data: sessionData, error: sessionError } = await callAction(
        actions.auth.getSession,
        null
      );
      if (!sessionData && sessionError) {
        throw sessionError;
      }

      const { error } = await callAction(
        actions.auth.logOut,
        sessionData.accessToken
      );
      if (error) {
        if (error.code !== 'UNAUTHORIZED') {
          throw error;
        }
      }
    }

    const { error: deleteSessionError } = await callAction(
      actions.auth.deleteSession,
      null
    );
    if (deleteSessionError) {
      throw deleteSessionError;
    }

    return new Response(null, {
      status: 204,
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
