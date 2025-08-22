import { type APIContext } from 'astro';
import { ActionError, actions } from 'astro:actions';

export async function POST({ callAction }: APIContext): Promise<Response> {
  try {
    const { data: sessionData, error: sessionError } = await callAction(
      actions.auth.getSession,
      null
    );
    if (!sessionData && sessionError) {
      throw sessionError;
    }

    const { error } = await callAction(actions.auth.logOut, {
      token: sessionData.session.accessToken,
    });
    if (error) {
      throw error;
    }

    const { error: deleteSessionError } = await callAction(
      actions.auth.deleteSession,
      null
    );
    if (deleteSessionError) {
      throw deleteSessionError;
    }

    return new Response(null, {
      status: 200,
      headers: { 'Content-Type': 'application/json' },
    });
  } catch (e) {
    if (e instanceof ActionError) {
      return new Response(JSON.stringify({ message: e.message }), {
        status: ActionError.codeToStatus(e.code),
        headers: {
          'Content-Type': 'application/json',
        },
      });
    }
    return new Response(JSON.stringify({ message: e }), {
      status: 500,
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }
}
