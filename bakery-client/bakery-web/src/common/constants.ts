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
  icon: HomeImg,
};

const products: NavOption = {
  name: 'Products',
  href: '/products',
  icon: StackImg,
  expand: false,
};

const about: NavOption = {
  name: 'About us',
  href: '/about',
  icon: InfoCircleImg,
};

const contact: NavOption = {
  name: 'Contact',
  href: '/contact',
  icon: AddressImg,
};

const cart: NavOption = {
  name: 'Cart',
  href: '/cart',
  icon: CartImg,
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
  icon: WhatsAppSVG,
};

const instagram: NavOption = {
  name: 'Instagram',
  href: '#',
  icon: InstagramSVG,
};

const facebook: NavOption = {
  name: 'Facebook',
  href: '#',
  icon: FacebookSVG,
};

const tiktok: NavOption = {
  name: 'TikTok',
  href: '#',
  icon: TikTokSVG,
};

export const navOptions = {
  home: home,
  products: products,
  about: about,
  contact: contact,
  cart: cart,
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
