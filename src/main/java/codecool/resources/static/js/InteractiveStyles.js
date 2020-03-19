export default class InteractiveClass {
    constructor(){};
    styleAddCard() {
        const cardAdder = document.querySelector('.card__content-wrapper');
        if(cardAdder) {
            const secondCard = document.querySelectorAll('.card')[1];
            cardAdder.style.height = secondCard.offsetHeight + 'px';
        }        
    }

    async showPopup(popup, message) {
        const p = popup.querySelector('.popup__description');
        p.innerHTML = message;
        popup.classList.add('popup--opened');
        await setTimeout(() => {
            popup.classList.remove('popup--opened');
            location.reload();
            },2200);
    }


}