import type { SignInRequest } from '@auth/types';
import type { APIContext } from 'astro';
import { ActionError, actions } from 'astro:actions';

export async function POST({
  request,
  callAction,
}: APIContext): Promise<Response> {
  try {
    const body: SignInRequest = await request.json();
    const { data, error } = await callAction(actions.auth.signIn, {
      username: body.username,
      password: body.password,
    });

    if (!data && error) {
      throw error;
    }

    const { data: sessionData, error: sessionError } = await callAction(
      actions.session.saveSession,
      {
        id: data.data!!.id,
        accessToken: data.data!!.access_token,
        refreshToken: data.data!!.refresh_token,
      }
    );

    if (!sessionData && sessionError) {
      const { error: logOutError } = await callAction(actions.auth.logOut, {
        token: data.data.access_token,
      });

      if (logOutError) {
        throw logOutError;
      }
      throw sessionError;
    }

    return new Response(JSON.stringify(data), {
      status: data.status,
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
