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

    const deleteSession = async () => {
      const { error: deleteSessionError } = await callAction(
        actions.auth.deleteSession,
        null
      );
      if (deleteSessionError) {
        throw deleteSessionError;
      }
    };

    const { error } = await callAction(actions.auth.logOut, {
      token: sessionData.session.accessToken,
    });
    if (error) {
      if (error.code === 'UNAUTHORIZED' || error.code === 'FORBIDDEN') {
        await deleteSession();
      } else {
        throw error;
      }
    }

    await deleteSession();

    return new Response(null, {
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
      status: ActionError.codeToStatus(body.code),
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }
}
