import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Button } from '../ui/button';
import { cn } from '@/lib/utils';
import { Skeleton } from '../ui/skeleton';

type Props = {
  src: string;
  alt?: string;
  className?: string;
};

export function ProfileButton({ className, src, alt = 'GU' }: Props) {
  const handleClick = () => {
    window.open('/profile', '_self');
  };

  return (
    <Button
      onClick={handleClick}
      variant="link"
      className="cursor-pointer rounded-full"
      size="icon"
    >
      <Avatar className={cn('cursor-pointer', className)}>
        <AvatarImage src={src} alt={alt} />
        <AvatarFallback>
          <Skeleton className="rounded-full" />
        </AvatarFallback>
      </Avatar>
    </Button>
  );
}
