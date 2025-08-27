import ProfileMenu from '@user/components/menu/ProfileMenu.astro';
import AuthMenu from '@/lib/auth/components/menu/AuthMenu.astro';
import { experimental_AstroContainer } from 'astro/container';
import { getContainerRenderer as reactContainerRenderer } from '@astrojs/react';
import { actions } from 'astro:actions';
import reactRenderer from '@astrojs/react/server.js';
import type { APIContext } from 'astro';

export async function GET({ callAction }: APIContext): Promise<Response> {
  const container = await experimental_AstroContainer.create();
  container.addServerRenderer({ renderer: reactRenderer });
  container.addClientRenderer({
    name: '@astrojs/react',
    entrypoint: '@astrojs/react/client.js',
  });

  const { data, error } = await callAction(actions.auth.getSession, null);

  if (!data && error) {
    return await container.renderToResponse(AuthMenu);
  } else {
    return await container.renderToResponse(ProfileMenu);
  }
}
