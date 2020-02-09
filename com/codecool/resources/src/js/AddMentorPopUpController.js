import FormValidator from './FormValidator.js';

export default class AddMentorPopUpController {
    constructor() {
    };

    closeAddMentorPopUp() {
        const mentorsTable = document.querySelector('.mentors__list');
        const addMentorCloseBtn = document.querySelector('.add_mentor_close-btn');
        const addMentorWindow = document.querySelector('.add-mentor');
        addMentorCloseBtn.onclick = (e) => {
            e.preventDefault();
            addMentorWindow.style.display = 'none';
            mentorsTable.style.display = 'table';
        }
    }
    
    openAddMentorPopUp() {
        const mentorsTable = document.querySelector('.mentors__list');
        const addMentorBtn = document.querySelector('.mentors__btn--add');
        const addMentorWindow = document.querySelector('.add-mentor');
        addMentorBtn.onclick = () => {
            addMentorWindow.style.display = 'block';
            mentorsTable.style.display = 'none';
            this.validate();
        }
    }

    validate() {
        const addMentorForm = document.querySelector('.add-mentor__form');
        const addMentorPopUpBtn = document.querySelector('.add-mentor__btn');
        const formValidator = new FormValidator(addMentorForm, addMentorPopUpBtn);
        formValidator.validate();
    }
}