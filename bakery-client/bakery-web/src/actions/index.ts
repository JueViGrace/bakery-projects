import { userActions } from '@user/actions';
import { authActions, sessionActions } from '@auth/actions';

export const server = {
  session: sessionActions,
  auth: authActions,
  user: userActions,
};
