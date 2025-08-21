import type { SignInRequest } from '@auth/types';
import type { APIContext } from 'astro';
import { ActionError, actions } from 'astro:actions';

export async function POST({
  request,
  callAction,
}: APIContext): Promise<Response> {
  const body: SignInRequest = await request.json();
  const { data, error } = await callAction(actions.auth.signIn, {
    username: body.username,
    password: body.password,
  });

  if (!data && error) {
    return new Response(JSON.stringify(error), {
      status: ActionError.codeToStatus(error.code),
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }

  const { res } = data;
  const { data: sessionData, error: sessionError } = await callAction(
    actions.auth.saveSession,
    {
      id: res.data!!.id,
      accessToken: res.data!!.access_token,
      refreshToken: res.data!!.refresh_token,
    }
  );

  if (!sessionData && sessionError) {
    const { error: logOutError } = await callAction(actions.auth.logOut, {
      token: res.data!!.access_token,
    });

    if (logOutError) {
      return new Response(JSON.stringify(logOutError), {
        status: ActionError.codeToStatus(logOutError.code),
        headers: {
          'Content-Type': 'application/json',
        },
      });
    }
  }

  return new Response(JSON.stringify(res), {
    status: res.status,
    statusText: res.description,
    headers: { 'Content-Type': 'application/json' },
  });
}
