type Role = 'admin' | 'user';

declare const USER_ROLES: readonly ['admin', 'user'];

type UserResponse = {
  id: string;
  first_name: string;
  last_name: string;
  display_name: string;
  username: string;
  email: string;
  phone_number: string;
  birth_date: string;
  profile_img: string;
  role: Role;
};

type User = {
  id: string;
  firstName: string;
  lastName: string;
  displayName: string;
  username: string;
  email: string;
  phoneNumber: string;
  birthDate: string;
  profileImg: string;
  role: Role;
};

export { USER_ROLES, type User, type Role, type UserResponse };
