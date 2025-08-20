import type { ImageMetadata } from 'astro';

interface ImportMetaEnv {
  readonly SERVER_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}

declare namespace App {
  interface Locals {
    user: User;
  }
  interface SessionData {
    session: Session;
  }
}

type Session = {
  id: string;
  accessToken: string;
  refreshToken: string;
};

type User = {
  firstName: string;
  lastName: string;
  displayName: string;
  username: string;
  email: string;
  phoneNumber: string;
  birthDate: string;
  profileImgUrl: string;
};

type Category = {
  name: string;
};

//------------Requests-----------
export type APIResponse<T> = {
  status: int;
  description: string;
  data: T;
  message: string;
  time: string;
};

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
  birth_date: number;
  email: string;
  username: string;
  password: string;
};

export type AuthResponse = {
  id: string;
  access_token: string;
  refresh_token: string;
};
//------------Requests-----------

//------------UI-----------
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
//------------UI-----------
