function onButtonClick(id: string, href: string, target: string = '_self') {
  document.getElementById(id)?.addEventListener('click', () => {
    window.open(href, target);
  });
}
