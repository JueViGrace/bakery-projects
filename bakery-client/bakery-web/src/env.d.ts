import type { ImageMetadata } from 'astro';

interface ImportMetaEnv {
  readonly SERVER_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}

export type Theme = 'light' | 'dark' | 'system' | undefined;

export type FooterOptions = {
  title: string;
  options: NavOption[];
};

export type NavOption = {
  name: string;
  href: string;
  icon?: ((_props: astroHTML.JSX.SVGAttributes) => any) & ImageMetadata;
  expand?: boolean;
};

export type APIResponse<T> = {
  status: int;
  description: string;
  data: T;
  message: string;
  time: string;
};

//------------------------
export type SignInRequest = {
  username: string;
  password: string;
};

export type SignInErrors = {
  username_error: string;
  password_error: string;
};

export type SignUpRequest = {
  first_name: string;
  last_name: string;
  phone_number: string;
  birth_date: string;
  email: string;
  username: string;
  password: string;
};

export type AuthResponse = {
  id: string;
  access_token: string;
  refresh_token: string;
};
//------------------------

export type User = {
  name: string;
  profileImgUrl: string;
};

export type Category = {
  name: string;
};
