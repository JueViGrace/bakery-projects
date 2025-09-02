import { type APIContext } from 'astro';
import { ActionError } from 'astro:actions';

export async function GET({}: APIContext): Promise<Response> {
  try {
    const pingReq = await fetch('/api/auth/ping', {
      headers: {
        'Content-Type': 'application/json',
      },
      method: 'GET',
    });
    if (!pingReq.ok) {
      const pingErr: ActionError = await pingReq.json();
      throw pingErr;
    }

    const userMeReq = await fetch('/api/users/me', {
      headers: {
        'Content-Type': 'application/json',
      },
      method: 'GET',
    });
    if (!userMeReq.ok) {
      const pingErr: ActionError = await userMeReq.json();
      throw pingErr;
    }

    return new Response(JSON.stringify(true), {
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
      body = new ActionError({
        message: `${e}`,
        code: 'INTERNAL_SERVER_ERROR',
      });
    }

    console.error(body);

    return Response.redirect('/auth/signin');
  }
}
