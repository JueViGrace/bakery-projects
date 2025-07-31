const drawer = document.getElementById('drawer-menu') as HTMLElement;
const drawerButton = document.getElementById('drawer-button') as HTMLElement;
const overlay = document.getElementById('drawer-overlay') as HTMLElement;
const pageWrapper = document.getElementById('page-wrapper') as HTMLElement;

function openDrawer() {
  drawer.classList.remove('-translate-x-full');
  overlay.classList.remove('opacity-0', 'pointer-events-none');
  overlay.classList.add('opacity-100', 'pointer-events-auto');
}

function closeDrawer() {
  drawer.classList.add('-translate-x-full');
  overlay.classList.add('opacity-0', 'pointer-events-none');
  overlay.classList.remove('opacity-100', 'pointer-events-auto');
}

drawerButton.addEventListener('click', (e: MouseEvent) => {
  e.stopPropagation();
  openDrawer();
});

overlay.addEventListener('click', () => {
  closeDrawer();
});

pageWrapper.addEventListener('click', (e: MouseEvent) => {
  if (
    !drawer.contains(e.target as Node) &&
    !drawerButton.contains(e.target as Node)
  ) {
    closeDrawer();
  }
});

window.addEventListener('resize', () => {
  if (window.innerWidth > 768) {
    closeDrawer();
  }
});
