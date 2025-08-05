import type { FooterOptions, NavOption } from '../env';
import HomeImg from '../assets/home.png';
import StackImg from '../assets/stack.png';
import InfoCircleImg from '../assets/info_circle.png';
import CartImg from '../assets/shopping_cart.png';
import AddressImg from '../assets/address_book.png';

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
  home: home,
  products: products,
  about: about,
  contact: contact,
  cart: cart,
};

export const navOptionList: NavOption[] = [home, products, contact, about];

const help: FooterOptions = {
  title: 'Help',
  options: [about, terms, privacy, faq],
};

const links = {
  title: 'Links',
  options: [home, products, about, contact],
};

export const footerOptions = [links, help];
