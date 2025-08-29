import { useEffect, useState } from 'react';
import { Toaster as SonnerToaster } from 'sonner';
import { getThemePreference, type Theme } from '@lib/ui/theme';

export function Toaster() {
  const [theme, setTheme] = useState<Theme>(getThemePreference());

  useEffect(() => {
    const observer = new MutationObserver(() => {
      const isDark = document.documentElement.classList.contains('dark');
      const newTheme = isDark ? 'dark' : 'light';
      setTheme(newTheme);
    });

    observer.observe(document.documentElement, {
      attributes: true,
      attributeFilter: ['class'],
    });

    return () => observer.disconnect();
  }, []);

  return (
    <SonnerToaster theme={theme} expand={true} richColors visibleToasts={3} />
  );
}
