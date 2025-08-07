import type { FooterOptions, NavOption } from '../env';
import HomeImg from '../assets/home.svg';
import StackImg from '../assets/stack.svg';
import InfoCircleImg from '../assets/info_circle.svg';
import CartImg from '../assets/shopping_cart.svg';
import AddressImg from '../assets/address_book.svg';
import WhatsAppSVG from '../assets/brand_whatsapp.svg';
import FacebookSVG from '../assets/brand_facebook.svg';
import TikTokSVG from '../assets/brand_tiktok.svg';
import InstagramSVG from '../assets/brand_instagram.svg';

/**
 * Main navigation
 * */
const home: NavOption = {
  name: 'Home',
  href: '/',
  Icon: HomeImg,
};

const products: NavOption = {
  name: 'Products',
  href: '/products',
  Icon: StackImg,
  expand: false,
};

const about: NavOption = {
  name: 'About us',
  href: '/about',
  Icon: InfoCircleImg,
};

const contact: NavOption = {
  name: 'Contact',
  href: '/contact',
  Icon: AddressImg,
};

/**
 * Other
 * */

const terms: NavOption = {
  name: 'Terms and conditions',
  href: '/terms',
};

const privacy: NavOption = {
  name: 'Privacy policy',
  href: '/privacy',
};

const faq: NavOption = {
  name: 'FAQ',
  href: '/faq',
};

/**
 * Social navigation
 * */

const whatsApp: NavOption = {
  name: 'WhatsApp',
  href: '#',
  Icon: WhatsAppSVG,
};

const instagram: NavOption = {
  name: 'Instagram',
  href: '#',
  Icon: InstagramSVG,
};

const facebook: NavOption = {
  name: 'Facebook',
  href: '#',
  Icon: FacebookSVG,
};

const tiktok: NavOption = {
  name: 'TikTok',
  href: '#',
  Icon: TikTokSVG,
};

export const navOptions = {
  home: home,
  products: products,
  about: about,
  contact: contact,
};

export const socialOptionList: NavOption[] = [
  whatsApp,
  instagram,
  facebook,
  tiktok,
];

export const navOptionList: NavOption[] = [home, products, contact, about];

/**
 * Footer navigation options
 * */

const links: FooterOptions = {
  title: 'Links',
  options: [home, products, about, contact],
};

const help: FooterOptions = {
  title: 'Help',
  options: [about, terms, privacy, faq],
};

export const footerOptions = [links, help];
