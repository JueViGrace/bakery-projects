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

    // TODO: create actions for user

    return new Response();
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
