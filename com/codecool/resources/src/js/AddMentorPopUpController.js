import FormValidator from './FormValidator.js';

export default class AddMentorPopUpController {
    constructor() {};

    closeAddMentorPopUp() {
        const mentorsTable = document.querySelector('.mentors__list');
        const mentorsTableMobile = document.querySelector('.mentors__list--mobile');
        const addMentorCloseBtn = document.querySelector('.add_mentor_close-btn');
        const addMentorWindow = document.querySelector('.add-mentor');
        addMentorCloseBtn.onclick = (e) => {
            e.preventDefault();
            addMentorWindow.style.display = 'none';
            if (window.outerWidth > 768) {
                mentorsTable.style.display = 'table';
            } else {
                mentorsTableMobile.style.display = 'table';
            }
            
        }
    }

    openAddMentorPopUp() {
        const mentorsTables = document.querySelectorAll('.mentors__list');
        const mentorsTableMobile = document.querySelector('.mentors__list--mobile');
        const addMentorBtns = document.querySelectorAll('.mentors__btn--add');
        const addMentorWindow = document.querySelector('.add-mentor');
        addMentorBtns[0].onclick = () => {
            addMentorWindow.style.display = 'block';
            mentorsTables[0].style.display = 'none';
            this.validate();
        }

        addMentorBtns[1].onclick = () => {
            addMentorWindow.style.display = 'block';
            mentorsTableMobile.style.display = 'none';
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