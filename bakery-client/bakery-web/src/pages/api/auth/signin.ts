import type { APIResponse, AuthResponse, SignInRequest } from '@/env';
import type { APIRoute } from 'astro';
import { SERVER_URL } from 'astro:env/server';

export const POST: APIRoute = async ({ request }) => {
  const body: SignInRequest = await request.json();

  if (!body.username || !body.password) {
    const errors = {
      errors: {
        username: body.username ?? 'Username required',
        password: body.password ?? 'Password required',
      },
    };
    return new Response(JSON.stringify(errors), {
      status: 400,
      statusText: 'Bad Request',
      headers: { 'Content-Type': 'application/json' },
    });
  }

  try {
    const req = await fetch(`${SERVER_URL}/api/auth/signin`, {
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(body),
      method: 'POST',
    });

    const res: APIResponse<AuthResponse> = await req.json();

    return new Response(JSON.stringify(res), {
      status: res.status,
      statusText: res.description,
      headers: { 'Content-Type': 'application/json' },
    });
  } catch (e) {
    console.error('Sign in request failed', e);
    const res: APIResponse<any> = {
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
};
