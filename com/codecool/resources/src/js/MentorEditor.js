import FormValidator from './FormValidator.js';


export default class MentorEditor {
    constructor() {};
    closeEditMentorPopUp() {
        const mentorsTable = document.querySelector('.mentors__list');
        const mentorsTableMobile = document.querySelector('.mentors__list--mobile');
        const editMentorCloseBtn = document.querySelector('.edit_mentor_close-btn');
        const editMentorWindow = document.querySelector('.edit-mentor');
        editMentorCloseBtn.onclick = (e) => {
            e.preventDefault();
            editMentorWindow.style.display = 'none';
            if (window.outerWidth > 768) {
                mentorsTable.style.display = 'table';
            } else {
                mentorsTableMobile.style.display = 'table';
            }

        }
    }

    openEditMentorPopUp() {
        const mentorsTables = document.querySelectorAll('.mentors__list');
        const mentorsTableMobile = document.querySelector('.mentors__list--mobile');
        const editMentorBtns = document.querySelectorAll('.mentors__btn--edit');
        const editMentorWindow = document.querySelector('.edit-mentor');
        for (let i = 0; i < editMentorBtns.length; i++) {
            editMentorBtns[i].onclick = () => {
                editMentorWindow.style.display = 'block';
                mentorsTables[i].style.display = 'none';
                this.validate();
            }
        }
        editMentorBtns[0].onclick = () => {
            editMentorWindow.style.display = 'block';
            mentorsTables[0].style.display = 'none';
            this.validate();
        }

        editMentorBtns[1].onclick = () => {
            editMentorWindow.style.display = 'block';
            mentorsTableMobile.style.display = 'none';
            this.validate();
        }
    }

    validate() {
        const editMentorForm = document.querySelector('edit-mentor__form');
        const editMentorPopUpBtn = document.querySelector('edit-mentor__btn');
        const formValidator = new FormValidator(editMentorForm, editMentorPopUpBtn);
        formValidator.validate();
    }
}