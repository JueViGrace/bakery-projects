import type { ImageMetadata } from 'astro';
import type { Session } from '@lib/auth/types';
import type { User } from '@lib/user/types';

interface ImportMetaEnv {
  readonly SERVER_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}

declare namespace App {
  interface SessionData {
    session: Session;
    user: User;
  }
}

//------------Requests-----------

export type APIResponse<T> = {
  status: number;
  description: string;
  data: T;
  message: string;
  time: string;
};

//------------Requests-----------

//------------UI-----------

export type FooterOptions = {
  title: string;
  options: NavOption[];
};

export type NavOption = {
  name: string;
  href: string;
  icon?: ((_props: astroHTML.JSX.SVGAttributes) => any) & ImageMetadata;
  description?: string;
  expand?: boolean;
};

//------------UI-----------
