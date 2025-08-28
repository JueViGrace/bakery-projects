import { Avatar, AvatarFallback, AvatarImage } from '@components/ui/avatar';
import { Skeleton } from '@components/ui/skeleton';
import { Button } from '@components/ui/button';
import { LogOutIcon } from 'lucide-react';
import {
  Popover,
  PopoverTrigger,
  PopoverContent,
} from '@components/ui/popover';
import { toast } from 'sonner';
import { navigate } from 'astro:transitions/client';
import LinkButton from '@/components/buttons/LinkButton';

export default function ProfileButton() {
  const src = 'https://github.com/shadcn.png';
  const alt = 'GU';

  const logOut = async () => {
    try {
      const req = await fetch('/api/auth/logout', {
        method: 'POST',
      });

      if (!req.ok) {
        const res = await req.json();
        throw Error(res.message);
      }

      navigate('/');
    } catch (e) {
      console.error(e);
      toast.error('Unable to request log out');
    }
  };

  return (
    <Popover>
      <PopoverTrigger asChild>
        <Avatar className="cursor-pointer">
          <AvatarImage loading="eager" src={src} alt={alt} />
          <AvatarFallback>
            <Skeleton className="rounded-full" />
          </AvatarFallback>
        </Avatar>
      </PopoverTrigger>
      <PopoverContent className="w-auto">
        <div className="flex max-w-max flex-col items-center justify-center gap-2">
          <LinkButton href="/profile" className="inline-flex w-full">
            Your profile
          </LinkButton>
          <Button
            type="button"
            onClick={logOut}
            className="inline-flex w-full cursor-pointer items-center justify-center gap-x-2"
            variant="destructive"
          >
            Log out <LogOutIcon />
          </Button>
        </div>
      </PopoverContent>
    </Popover>
  );
}
