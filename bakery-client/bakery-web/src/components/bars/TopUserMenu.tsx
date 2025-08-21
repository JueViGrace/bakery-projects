import { useEffect, useState } from 'react';
import LinkButton from '@components/buttons/LinkButton';
import { ProfileButton } from '@profile/components/ProfileButton';
import CartSVG from '@assets/shopping_cart.svg';
import { Skeleton } from '@components/ui/skeleton';

export default function TopUserMenu() {
  const [isLoading, setIsLoading] = useState(true);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    setIsAuthenticated(false);
    setIsLoading(false);
  }, []);

  return (
    <div className="flex items-center justify-center gap-2">
      {isLoading ? (
        <>
          <Skeleton className="h-8 w-16 rounded-md" />
          <Skeleton className="size-8 rounded-full" />
        </>
      ) : isAuthenticated ? (
        <>
          <LinkButton href="/cart" size="icon" variant="outline">
            <CartSVG />
          </LinkButton>
          <ProfileButton />
        </>
      ) : (
        <>
          <LinkButton variant="outline" href="/auth/signin">
            Sign in
          </LinkButton>
          <LinkButton href="/auth/signup">Sign up</LinkButton>
        </>
      )}
    </div>
  );
}
