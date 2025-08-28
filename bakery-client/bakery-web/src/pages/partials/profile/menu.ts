import ProfileMenu from '@user/components/menu/ProfileMenu.astro';
import AuthMenu from '@auth/components/menu/AuthMenu.astro';
import { experimental_AstroContainer } from 'astro/container';
import { actions } from 'astro:actions';
import type { APIContext } from 'astro';
import serverRenderer from '@astrojs/react/server.js';

export async function GET({ callAction }: APIContext): Promise<Response> {
  const container = await experimental_AstroContainer.create();
  container.addServerRenderer({
    renderer: serverRenderer,
    name: serverRenderer.name,
  });
  container.addClientRenderer({
    name: '@astrojs/react',
    entrypoint: '@astrojs/react/client.js',
  });

  const { data, error } = await callAction(actions.auth.getSession, null);
  let res: string;

  if (!data && error) {
    res = await container.renderToString(AuthMenu, { partial: true });
  } else {
    res = await container.renderToString(ProfileMenu, { partial: true });
  }

  return new Response(res, {
    headers: {
      'Content-Type': 'text/html',
    },
  });
}
