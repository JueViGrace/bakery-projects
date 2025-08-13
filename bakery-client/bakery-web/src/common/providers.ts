import type { NavOption } from '@/env';
import GoogleSVG from '@assets/google_logo.svg';

const google: NavOption = {
  name: 'Google',
  icon: GoogleSVG,
  href: '/auth/google',
};

const providers = {
  google: google,
};

const providerList = [google];

export { providers, providerList };
