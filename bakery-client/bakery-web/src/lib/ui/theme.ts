type Theme = 'light' | 'dark' | 'system';

const getThemePreference = (): Theme => {
  if (typeof localStorage !== 'undefined' && localStorage.getItem('theme')) {
    return localStorage.getItem('theme') as Theme;
  }
  return window.matchMedia('(prefers-color-scheme: dark)').matches
    ? 'dark'
    : 'light';
};

function setTheme(theme: Theme) {
  document.documentElement.classList.toggle('dark', theme === 'dark');
  if (typeof localStorage !== 'undefined') {
    localStorage.setItem('theme', theme);
  }
}

function initTheme() {
  const isDark = getThemePreference() === 'dark';
  document.documentElement.classList[isDark ? 'add' : 'remove']('dark');

  if (typeof localStorage !== 'undefined') {
    const observer = new MutationObserver(() => {
      const isDark = document.documentElement.classList.contains('dark');
      localStorage.setItem('theme', isDark ? 'dark' : 'light');
    });
    observer.observe(document.documentElement, {
      attributes: true,
      attributeFilter: ['class'],
    });
  }
}

export { initTheme, getThemePreference, setTheme, type Theme };
