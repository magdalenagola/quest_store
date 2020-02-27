export default function toggleMenu() {
    const pageHeader = document.querySelector('.header');
    const btn = document.querySelector('.menu__btn');

    btn.addEventListener('click', event => {
        event.preventDefault();
        pageHeader.classList.toggle('header--opened');
    })
}