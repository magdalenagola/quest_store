export default class InteractiveClass {
    constructor(){};
    styleAddCard() {
        const cardAdder = document.querySelector('.card__content-wrapper');
        if(cardAdder) {
            const secondCard = document.querySelectorAll('.card')[1];
            cardAdder.style.height = secondCard.offsetHeight + 'px';
        }        
    }
}