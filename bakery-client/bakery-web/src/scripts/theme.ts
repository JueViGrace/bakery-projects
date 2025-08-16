import type { Theme } from '@/env';

function getThemePreference(): Theme {
  if (typeof localStorage !== 'undefined' && localStorage.getItem('theme')) {
    return localStorage.getItem('theme') as Theme;
  }
  return window.matchMedia('(prefers-color-scheme: dark)').matches
    ? 'dark'
    : 'light';
}

function setTheme(theme: Theme) {
  document.documentElement.classList.toggle('dark', theme === 'dark');
  if (typeof localStorage !== 'undefined' && theme) {
    localStorage.setItem('theme', theme);
  }
}

export { getThemePreference, setTheme };
