class DrawerController {
  private navDrawer: HTMLElement;
  private backdropEl: HTMLElement;
  private openBtn: HTMLElement;
  private closeBtn: HTMLElement;

  constructor() {
    this.navDrawer = this.getSelector('[data-nav-drawer]');
    this.backdropEl = this.getSelector('[data-drawer-backdrop]');
    this.openBtn = this.getSelector('[data-open-drawer-button]');
    this.closeBtn = this.getSelector('[data-close-drawer-button]');
  }

  private getSelector(selector: string): HTMLElement {
    const element = document.querySelector<HTMLElement>(selector);
    if (!element) {
      throw Error(`DrawerController: Element ${element} not found`);
    }
    return element;
  }
}

export { DrawerController };
