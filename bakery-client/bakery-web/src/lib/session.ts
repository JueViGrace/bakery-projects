import type { Session } from '@/env';
import { actions } from 'astro:actions';

async function callSessionAction(): Promise<Session | undefined> {
  let session: Session | undefined = undefined;
  const { data, error } = await actions.session.getSession();

  if (data && data.session && !error) {
    session = data.session;
  }

  return session;
}

export { callSessionAction as getSession };
