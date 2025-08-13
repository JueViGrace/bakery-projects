import { Avatar, AvatarFallback, AvatarImage } from '@ui/ui/avatar';
import { cn } from '@/lib/utils';
import { Skeleton } from '@ui/ui/skeleton';
import { LinkButton } from '@ui/buttons/LinkButton';

type Props = {
  src: string;
  alt?: string;
  className?: string;
};

export function ProfileButton({ className, src, alt = 'GU' }: Props) {
  return (
    <LinkButton
      href="/profile"
      variant="link"
      className={cn('rounded-full', className)}
      size="icon"
    >
      <Avatar>
        <AvatarImage loading="eager" src={src} alt={alt} />
        <AvatarFallback>
          <Skeleton className="rounded-full" />
        </AvatarFallback>
      </Avatar>
    </LinkButton>
  );
}
