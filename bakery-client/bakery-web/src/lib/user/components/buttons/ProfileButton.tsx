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
        <div className="flex flex-col items-center justify-center gap-4">
          <Button
            type="button"
            onClick={logOut}
            className="inline-flex cursor-pointer items-center justify-center gap-x-2"
            variant="destructive"
          >
            Log out <LogOutIcon />
          </Button>
        </div>
      </PopoverContent>
    </Popover>
  );
}
