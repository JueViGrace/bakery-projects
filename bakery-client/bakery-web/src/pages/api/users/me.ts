import { type APIContext } from 'astro';
import { ActionError, actions } from 'astro:actions';

export async function GET({ callAction }: APIContext): Promise<Response> {
  try {
    const { data: session, error: sessionErr } = await callAction(
      actions.session.getSession,
      null
    );
    if (!session && sessionErr) {
      throw sessionErr;
    }

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
    const { data: savedUserData, error: saveUserErr } =
      await actions.user.saveUserData({
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
    if (!savedUserData && saveUserErr) {
      throw saveUserErr;
    }

    return new Response(JSON.stringify(userData), {
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
