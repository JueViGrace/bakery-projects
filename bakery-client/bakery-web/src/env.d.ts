import type { ImageMetadata } from 'astro';

export type FooterOptions = {
  title: string;
  options: NavOption[];
};

export type NavOption = {
  name: string;
  href: string;
  icon?: ((_props: astroHTML.JSX.SVGAttributes) => any) & ImageMetadata;;
  expand?: boolean;
};

export type User = {
  name: string;
  profileImgUrl: string;
};

export type Category = {
  name: string;
};

