import type { APIResponse, AuthResponse, SignUpRequest } from '@/env';
import { type APIContext } from 'astro';
import { actions } from 'astro:actions';
import { SERVER_URL } from 'astro:env/server';

export async function POST({
  request,
  callAction,
}: APIContext): Promise<Response> {
  const body: SignUpRequest = await request.json();
  let res: APIResponse<AuthResponse | null>;

  try {
    const req = await fetch(`${SERVER_URL}/api/auth/signup`, {
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(body),
      method: 'POST',
    });

    res = await req.json();

    if (req.ok) {
      const session = await callAction(actions.session.saveSession, {
        id: res.data!!.id,
        accessToken: res.data!!.access_token,
        refreshToken: res.data!!.refresh_token,
      });

      if (session.error) {
        res = {
          status: 404,
          description: 'Not Found',
          data: null,
          message: 'Unable to save user session',
          time: new Date().toString(),
        };

        await fetch(`${SERVER_URL}/api/auth/logout`, {
          headers: {
            'Content-Type': 'application/json',
            'x-redirect-to': '/',
            Authorization: `Bearer ${res.data!!.access_token}`,
          },
          method: 'POST',
        });
      }
    }

    return new Response(JSON.stringify(res), {
      status: res.status,
      statusText: res.description,
      headers: { 'Content-Type': 'application/json', 'x-redirect-to': '/' },
    });
  } catch (e) {
    console.error('Sign in request failed', e);
    res = {
      status: 500,
      description: 'Internal Server Error',
      data: null,
      message: `Unnexpected error: ${e}`,
      time: new Date().toString(),
    };
    return new Response(JSON.stringify(res), {
      status: res.status,
      statusText: res.description,
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }
}
