import FormValidator from './FormValidator.js';


export default class UserEditor {
    constructor() {};
    closeEditUserPopUp() {
        const userTable = document.querySelector('.user__list');
        const userTableMobile = document.querySelector('.user__list--mobile');
        const editUserCloseBtn = document.querySelector('.edit_user_close-btn');
        const editUserWindow = document.querySelector('.edit-user');
        if (editUserCloseBtn) {
            editUserCloseBtn.onclick = (e) => {
                e.preventDefault();
                editUserWindow.style.display = 'none';
                if (window.outerWidth > 768) {
                    userTable.style.display = 'table';
                } else {
                    userTableMobile.style.display = 'table';
                }

            }
        }
    }

    openEditUserPopUp() {
        const userTables = document.querySelectorAll('.user__list');
        const userTableMobile = document.querySelector('.user__list--mobile');
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
        if (editUserBtns[0]) {
            editUserBtns[0].onclick = () => {
                editUserWindow.style.display = 'block';
                userTables[0].style.display = 'none';
                this.validate();
            }
        }

        if (editUserBtns[1]) {
            editUserBtns[1].onclick = () => {
                editUserWindow.style.display = 'block';
                userTableMobile.style.display = 'none';
                this.validate();
            }
        }
    }

    validate() {
        const editUserForm = document.querySelector('.edit-user__form');
        const editUserPopUpBtn = document.querySelector('.edit-user__btn');
        const formValidator = new FormValidator(editUserForm, editUserPopUpBtn);
        formValidator.validate();
    }
}