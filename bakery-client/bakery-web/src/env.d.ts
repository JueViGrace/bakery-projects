export type ButtonColors = {
  containerColor: string;
  focusedContainerColor: string;
  disabledContainerColor: string;
};

export type FooterOptions = {
  title: string;
  options: NavOption[];
};

export type NavOption = {
  name: string;
  href: string;
  icon?: string;
  expand?: boolean;
};

export type User = {
  name: string;
  profileImgUrl: string;
};

export type Category = {
  name: string;
};
