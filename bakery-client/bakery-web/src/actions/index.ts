import { userActions } from '@user/actions';
import { authActions } from '@auth/actions';

export const server = {
  auth: authActions,
  user: userActions,
};
