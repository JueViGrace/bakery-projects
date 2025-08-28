import { cn } from '@lib/utils';
import { Button } from '@components/ui/button';
import { type ReactNode } from 'react';

type Props = {
  href: string;
  className?: string;
  variant?:
    | 'default'
    | 'link'
    | 'ghost'
    | 'secondary'
    | 'outline'
    | 'destructive'
    | null
    | undefined;
  size?: 'default' | 'sm' | 'lg' | 'icon' | null | undefined;
  children?: ReactNode;
};

export default function LinkButton({
  href,
  className,
  variant = 'default',
  size = 'default',
  children,
}: Props) {
  return (
    <Button
      size={size}
      variant={variant}
      className={cn('cursor-pointer', className)}
      asChild
    >
      <a href={href}>{children}</a>
    </Button>
  );
}
