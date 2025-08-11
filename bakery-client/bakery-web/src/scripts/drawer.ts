function getElement(id: string): HTMLElement {
  const element: HTMLElement | null = document.querySelector(id);
  if (!element) {
    throw Error(`DrawerController: Element ${element} not found`);
  }
  return element;
}

const navDrawer: HTMLElement = getElement('[data-nav-drawer]');
const backdropEl: HTMLElement = getElement('[data-drawer-backdrop]');
const openBtn: HTMLElement = getElement('[data-open-drawer-button]');
const closeBtn: HTMLElement = getElement('[data-close-drawer-button]');

function openDrawer() {
  backdropEl.classList.remove('hidden');
  navDrawer.classList.remove('hidden');
  setTimeout(() => {
    navDrawer.classList.remove('-translate-x-full');
    navDrawer.classList.add('translate-x-0');
    backdropEl.classList.remove('opacity-0');
    backdropEl.classList.add('opacity-100');
  }, 100);
}

function closeDrawer() {
  navDrawer.classList.remove('translate-x-0');
  navDrawer.classList.add('-translate-x-full');
  backdropEl.classList.remove('opacity-100');
  backdropEl.classList.add('opacity-0');
  setTimeout(() => {
    backdropEl.classList.add('hidden');
    navDrawer.classList.add('hidden');
  }, 300);
}

window.addEventListener('resize', () => {
  if (window.innerWidth > 1024) {
    closeDrawer();
  }
});
backdropEl.addEventListener('click', () => {
  closeDrawer();
});
openBtn.addEventListener('click', (e: MouseEvent) => {
  e.stopPropagation();
  openDrawer();
});
closeBtn.addEventListener('click', () => {
  closeDrawer();
});
