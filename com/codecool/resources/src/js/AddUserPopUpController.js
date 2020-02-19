import FormValidator from './FormValidator.js';

export default class AddUserPopUpController {
    constructor() {};

    closeAddUserPopUp() {
        const userTable = document.querySelector('.user__list');
        const userTableMobile = document.querySelector('.user__list--mobile');
        const addUserCloseBtn = document.querySelector('.add_user_close-btn');
        const addUserWindow = document.querySelector('.add-user');
        addUserCloseBtn.onclick = (e) => {
            e.preventDefault();
            addUserWindow.style.display = 'none';
            if (window.outerWidth > 768) {
                userTable.style.display = 'table';
            } else {
                userTableMobile.style.display = 'table';
            }
            
        }
    }

    openAddUserPopUp() {
        const userTables = document.querySelectorAll('.user__list');
        const userTableMobile = document.querySelector('.user__list--mobile');
        const addUserBtns = document.querySelectorAll('.user__btn--add');
        const addUserWindow = document.querySelector('.add-user');
        addUserBtns[0].onclick = () => {
            addUserWindow.style.display = 'block';
            userTables[0].style.display = 'none';
            this.validate();
        }

        addUserBtns[1].onclick = () => {
            addUserWindow.style.display = 'block';
            userTableMobile.style.display = 'none';
            this.validate();
        }
    }

    validate() {
        const addUserForm = document.querySelector('.add-user__form');
        const addUserPopUpBtn = document.querySelector('.add-user__btn');
        const formValidator = new FormValidator(addUserForm, addUserPopUpBtn);
        formValidator.validate();
    }
}