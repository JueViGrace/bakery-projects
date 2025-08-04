import type { FooterOptions, NavOption } from '../env';

const products: NavOption = {
  name: 'Products',
  href: '/products',
  icon: './src/assets/stack.png',
  expand: false,
};

const about: NavOption = {
  name: 'About us',
  href: '/about',
  icon: './src/assets/info_circle.png',
};

const contact: NavOption = {
  name: 'Contact',
  href: '/contact',
  icon: './src/assets/address_book.png',
};

const cart: NavOption = {
  name: 'Cart',
  href: '/cart',
  icon: './src/assets/shopping_cart.png',
};

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

export const navOptions = {
  products: products,
  about: about,
  contact: contact,
  cart: cart,
};

export const navOptionList: NavOption[] = [products, contact, about];

const services: FooterOptions = {
  title: 'Customer services',
  options: [about, contact, terms, privacy, faq],
};

export const footerOptions = [services];
