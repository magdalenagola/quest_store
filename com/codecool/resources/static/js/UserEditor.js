import FormValidator from './FormValidator.js';


export default class UserEditor {
    constructor() {};
    closeEditUserPopUp() {
        const userTable = document.querySelector('.user__list');
        const editUserCloseBtn = document.querySelector('.edit_user_close-btn');
        const editUserWindow = document.querySelector('.edit-user');
        if (editUserCloseBtn) {
            editUserCloseBtn.onclick = (e) => {
                e.preventDefault();
                editUserWindow.style.display = 'none';
                userTable.style.display = 'flex';

            }
        }
    }

    openEditUserPopUp() {
        const userTables = document.querySelectorAll('.user__list');
        const editUserBtns = document.querySelectorAll('.user__btn--edit');
        const editUserWindow = document.querySelector('.edit-user');
        if (editUserBtns.length > 0) {
            for (let i = 0; i < editUserBtns.length; i++) {
                editUserBtns[i].onclick = () => {
                    editUserWindow.style.display = 'block';
                    userTables[i].style.display = 'none';
                    this.validate();
                }
            }
        }
    }

    validate() {
        const editUserForm = document.querySelector('.edit-user__form');
        const editUserPopUpBtn = document.querySelector('.edit-user__btn');
        const formValidator = new FormValidator(editUserForm, editUserPopUpBtn);
        console.log(formValidator.validate());
    }
}