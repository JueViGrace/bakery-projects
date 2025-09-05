import type { Session } from '@/lib/auth/types';
import { type APIContext } from 'astro';
import { ActionError, actions } from 'astro:actions';

export async function POST({
  request,
  callAction,
}: APIContext): Promise<Response> {
  try {
    const session: Session = await request.json();

    const { data: userData, error: userDataErr } = await callAction(
      actions.user.requestUserData,
      {
        token: session.accessToken,
      }
    );
    if (!userData && userDataErr) {
      throw userDataErr;
    }

    const { data, status } = userData;
    const { error: saveUserErr } = await callAction(actions.user.saveUserData, {
      id: data.id,
      firstName: data.first_name,
      lastName: data.last_name,
      displayName: data.display_name,
      username: data.username,
      email: data.email,
      phoneNumber: data.phone_number,
      birthDate: data.birth_date,
      profileImg: data.profile_img,
      role: data.role,
    });
    if (saveUserErr) {
      throw saveUserErr;
    }

    const { data: user, error: userErr } = await callAction(
      actions.user.getUserData,
      null
    );
    if (!user && userErr) {
      throw userErr;
    }

    return new Response(JSON.stringify(user), {
      status: status,
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

    return new Response(JSON.stringify(body), {
      status: body.status,
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }
}
