type Role = 'admin' | 'user';

declare const USER_ROLES: readonly ['admin', 'user'];

type User = {
  firstName: string;
  lastName: string;
  displayName: string;
  username: string;
  email: string;
  phoneNumber: string;
  birthDate: string;
  profileImgUrl: string;
  role: Role;
};

export { USER_ROLES, type User, type Role };
