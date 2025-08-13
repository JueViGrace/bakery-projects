import type { FooterOptions, NavOption } from '@/env';
import HomeSVG from '@assets/home.svg';
import StackSVG from '@assets/stack.svg';
import InfoCircleSVG from '@assets/info_circle.svg';
import AddressSVG from '@assets/address_book.svg';
import WhatsAppSVG from '@assets/brand_whatsapp.svg';
import FacebookSVG from '@assets/brand_facebook.svg';
import TikTokSVG from '@assets/brand_tiktok.svg';
import InstagramSVG from '@assets/brand_instagram.svg';

/**
 * Main navigation
 * */
const home: NavOption = {
  name: 'Home',
  href: '/',
  icon: HomeSVG,
};

const products: NavOption = {
  name: 'Products',
  href: '/products',
  icon: StackSVG,
  expand: false,
};

const about: NavOption = {
  name: 'About us',
  href: '/about',
  icon: InfoCircleSVG,
};

const contact: NavOption = {
  name: 'Contact',
  href: '/contact',
  icon: AddressSVG,
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
  href: '/',
  icon: WhatsAppSVG,
};

const instagram: NavOption = {
  name: 'Instagram',
  href: '/',
  icon: InstagramSVG,
};

const facebook: NavOption = {
  name: 'Facebook',
  href: '/',
  icon: FacebookSVG,
};

const tiktok: NavOption = {
  name: 'TikTok',
  href: '/',
  icon: TikTokSVG,
};

const navOptions = {
  home: home,
  products: products,
  about: about,
  contact: contact,
};

const navOptionList: NavOption[] = [home, products, contact, about];

const socialOptionList: NavOption[] = [whatsApp, instagram, facebook, tiktok];

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

const footerOptionList = [links, help];

export { navOptions, navOptionList, socialOptionList, footerOptionList };
