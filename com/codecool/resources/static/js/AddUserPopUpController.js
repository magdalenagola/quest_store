import FormValidator from './FormValidator.js';

export default class AddUserPopUpController {
    constructor() {};

    closeAddUserPopUp() {
        const userTable = document.querySelector('.user__list');
        const addUserCloseBtn = document.querySelector('.add_user_close-btn');
        const addUserWindow = document.querySelector('.add-user');
        if (addUserCloseBtn) {
            addUserCloseBtn.onclick = (e) => {
                e.preventDefault();
                addUserWindow.style.display = 'none';
                userTable.style.display = 'block';

            }
        }
    }

    openAddUserPopUp() {
        const userTable = document.querySelector('.user__list');
        const addUserBtn = document.querySelector('.user__btn--add');
        const addUserWindow = document.querySelector('.add-user');
        if (addUserBtn) {
            addUserBtn.onclick = () => {
                addUserWindow.style.display = 'block';
                userTable.style.display = 'none';
                this.validate();
            }
        }
    }

    validate() {
        const addUserForm = document.querySelector('.add-user__form');
        const addUserPopUpBtn = document.querySelector('.add-user__btn');
        const formValidator = new FormValidator(addUserForm, addUserPopUpBtn);
        console.log(formValidator.validate());
    }
}