const publicRoutes = [
  '/',
  '/auth/signin',
  '/auth/signup',
  '/about',
  '/cart',
  '/contact',
  '/faq',
  '/privacy',
  '/products',
  '/terms',
];

const securedRoutes = ['/profile', '/admin'];

export { publicRoutes, securedRoutes };
