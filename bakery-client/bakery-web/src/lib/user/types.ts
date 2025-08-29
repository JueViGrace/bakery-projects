type Role = 'admin' | 'user';

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

export type { User };
