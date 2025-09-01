import type { SignUpRequest } from '@auth/types';
import { type APIContext } from 'astro';
import { ActionError, actions } from 'astro:actions';

export async function POST({
  request,
  callAction,
}: APIContext): Promise<Response> {
  try {
    const body: SignUpRequest = await request.json();
    const { data, error } = await callAction(actions.auth.signUp, {
      firstNameField: body.first_name,
      lastNameField: body.last_name,
      emailField: body.email,
      phoneNumberField: body.phone_number,
      birthDateField: body.birth_date,
      usernameField: body.username,
      passwordField: body.password,
    });

    if (!data && error) {
      throw error;
    }

    const { data: sessionData, error: sessionError } = await callAction(
      actions.auth.saveSession,
      {
        id: data.data!!.id,
        accessToken: data.data!!.access_token,
        refreshToken: data.data!!.refresh_token,
      }
    );

    if (!sessionData && sessionError) {
      const { error: logOutError } = await callAction(
        actions.auth.logOut,
        data.data!!.access_token
      );

      if (logOutError) {
        throw logOutError;
      }
      throw sessionError;
    }

    return new Response(JSON.stringify(data), {
      status: data.status,
      statusText: data.description,
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
