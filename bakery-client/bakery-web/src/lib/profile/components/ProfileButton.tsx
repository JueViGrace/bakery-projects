import { Avatar, AvatarFallback, AvatarImage } from '@components/ui/avatar';
import { Skeleton } from '@components/ui/skeleton';
import { Button } from '@components/ui/button';
import { LogOutIcon } from 'lucide-react';
import {
  Popover,
  PopoverTrigger,
  PopoverContent,
} from '@components/ui/popover';
import { actions } from 'astro:actions';
import { toast } from 'sonner';
import { navigate } from 'astro/virtual-modules/transitions-router.js';

export function ProfileButton() {
  const src = 'https://github.com/shadcn.png';
  const alt = 'GU';

  const logOut = async () => {
    const { error } = await actions.auth.logOut();

    if (error) {
      toast.error(
        `${error.name}: ${error.message}${error.cause ?? `, ${error.cause}`}`
      );
      return;
    }

    const { error: sessionError } = await actions.auth.deleteSession();
    if (sessionError) {
      toast.error(
        `${sessionError.name}: ${sessionError.message}${sessionError.cause ?? `, ${sessionError.cause}`}`
      );
      return;
    }

    navigate('/');
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
