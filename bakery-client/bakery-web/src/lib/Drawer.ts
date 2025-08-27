class DrawerController {
  private navDrawer: HTMLElement = getElement('[data-nav-drawer]');
  private backdropEl: HTMLElement = getElement('[data-drawer-backdrop]');
  private openBtn: HTMLElement = getElement('[data-open-drawer-button]');
  private closeBtn: HTMLElement = getElement('[data-close-drawer-button]');

  constructor() {
    this.initDrawer();
  }

  initDrawer() {
    window.addEventListener('resize', () => {
      if (window.innerWidth > 1024) {
        this.closeDrawer();
      }
    });
    this.backdropEl.addEventListener('click', () => {
      this.closeDrawer();
    });
    this.openBtn.addEventListener('click', (e: MouseEvent) => {
      e.stopPropagation();
      this.openDrawer();
    });
    this.closeBtn.addEventListener('click', () => {
      this.closeDrawer();
    });
  }

  openDrawer() {
    this.backdropEl.classList.remove('hidden');
    this.navDrawer.classList.remove('hidden');
    setTimeout(() => {
      this.navDrawer.classList.remove('-translate-x-full');
      this.navDrawer.classList.add('translate-x-0');
      this.backdropEl.classList.remove('opacity-0');
      this.backdropEl.classList.add('opacity-100');
    }, 100);
  }

  closeDrawer() {
    this.navDrawer.classList.remove('translate-x-0');
    this.navDrawer.classList.add('-translate-x-full');
    this.backdropEl.classList.remove('opacity-100');
    this.backdropEl.classList.add('opacity-0');
    setTimeout(() => {
      this.backdropEl.classList.add('hidden');
      this.navDrawer.classList.add('hidden');
    }, 300);
  }
}

function getElement(id: string): HTMLElement {
  const element: HTMLElement | null = document.querySelector(id);
  if (!element) {
    throw Error(`Element ${id} not found`);
  }
  return element;
}

const drawerController = new DrawerController();

export { drawerController };
