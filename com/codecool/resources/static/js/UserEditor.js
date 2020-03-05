import FormValidator from './FormValidator.js';
import EditStudent from "./EditStudent.js";
import DeleteStudent from "./DeleteStudent.js";


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
        const userTables = document.querySelector('.user__list');
        console.log(userTables);
        const students = document.querySelectorAll('.user__item');
        const editUserBtns = document.querySelectorAll('.user__btn--edit');
        const editUserWindow = document.querySelector('.edit-user');
        const deleteBtns = document.querySelectorAll('.user__btn--remove');
        const editStudent = new EditStudent();
        const deleteStudent = new DeleteStudent();
        if (editUserBtns.length > 0) {
            for (let i = 0; i < editUserBtns.length - 1; i++) {
                let userId;
                if (i > 0) {
                    userId = students[i+1].getAttribute('id').split('_')[1];
                }
                deleteBtns[i].onclick = () => {
                    console.log(i);
                    deleteStudent.deleteStudent(userId);
                    console.log(userId);
                }
                editUserBtns[i].onclick = () => {
                    editUserWindow.style.display = 'block';
                    userTables.style.display = 'none';
                    const btns = editUserWindow.getElementsByTagName('button');
                    let submitBtn;
                    for (let i = 0; i < btns.length; i++) {
                        if (btns[i].type === 'submit') {
                            submitBtn = btns[i];
                        }
                    }
                    editUserWindow.style.display = 'block';
                    userTables.style.display = 'none';
                    submitBtn.onclick = (e) => {
                        e.preventDefault();
                        if (this.validate()) {
                            editStudent.editStudent(userId);
                        }
                    }
                }
            }
        }
    }

    validate() {
        const editUserForm = document.querySelector('.edit-user__form');
        const editUserPopUpBtn = document.querySelector('.edit-user__btn');
        const formValidator = new FormValidator(editUserForm, editUserPopUpBtn);
        return formValidator.validate();
    }
}