import FormValidator from "./FormValidator.js";
import UserEditor from "./UserEditor.js";
import AddUserPopUpController from "./AddUserPopUpController.js";

export default class MentorStudentHandler {

    handleStudentList() {
        const addUserPopUpController = new AddUserPopUpController();
        addUserPopUpController.openAddUserPopUp();
        addUserPopUpController.closeAddUserPopUp();
        const userEditor = new UserEditor();
        userEditor.openEditUserPopUp();
        userEditor.closeEditUserPopUp();

        const xmlHttpRequest = new XMLHttpRequest();
        getStudentsFromServer();
        xmlHttpRequest.onreadystatechange = function () {
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                if (xmlHttpRequest.status === 200) {
                    let response = xmlHttpRequest.responseText;
                    if (response === 'saved') {
                        alert('successfully saved');
                    } else {
                        response = JSON.parse(response);
                    }
                    const studentList = document.querySelector(".user__list");
                    for(let i = 0; i < response.length; i++) {
                        studentList.appendChild(createStudentFromDb(response[i]));
                        toggleForm();
                    }
                }
            }
        };

        // function invokeListeners() {
        //     listenAddBtn();
        //     listenEditBtn();
        // }
        //
        // function listenAddBtn() {
        //     const addBtn = document.querySelector('.user__btn--add').getElementsByTagName('svg')[0];
        //
        //
        //     addBtn.onclick = () => {
        //         const submit = document.querySelector('.add-user__btn');
        //         checkAndSendForm();
        //         function checkAndSendForm() {
        //             submit.onclick = (e) => {
        //                 e.preventDefault();
        //                 if (validate()) {
        //                     createNewStudent();
        //                 } else {
        //                     checkAndSendForm();
        //                 }
        //             }
        //         }
        //     }
        // }

//         function validate() {
//             const addUserForm = document.querySelector('.add-user__form');
//             const addUserPopUpBtn = document.querySelector('.add-user__btn');
//             const formValidator = new FormValidator(addUserForm, addUserPopUpBtn);
//             console.log(formValidator.validate())
//             return formValidator.validate();
//         }

        function toggleForm() {
            const editUserPopUpController = new UserEditor();
            editUserPopUpController.openEditUserPopUp();
            editUserPopUpController.closeEditUserPopUp();
        }
        //
        // function listenEditBtn() {
        //     const editBtn = document.querySelector('.user__btn--edit').getElementsByTagName('svg')[0];
        //     editBtn.onclick = () => {
        //         const studentId = editBtn.parentNode.parentNode.parentNode.parentNode;
        //         console.log(studentId);
        //     }
        // }

        function getStudentByIdFromServer() {
            xmlHttpRequest.open("GET",`/mentor/students/get/${id}`);
            xmlHttpRequest.send();
        }

        function getStudentsFromServer() {
            xmlHttpRequest.open("GET","/mentor/students");
            xmlHttpRequest.send();
        }

        function postToServer(xmlHttpRequest, newStudent, userId) {
            xmlHttpRequest.open('POST', `/mentor/students/add/${userId}`);
            xmlHttpRequest.send(JSON.stringify(newStudent));
        }

        function createStudentFromDb(json) {
            const student = document.createElement('li');
            student.classList.add('user__item');
            student.setAttribute('id', `student_${json["id"]}`);
            const name = document.createElement('h3');
            name.classList.add('user__name');
            name.innerText = json["name"];
            const contentWrapper = document.createElement('div');
            contentWrapper.classList.add('user__content-wrapper');
            const email = document.createElement('p');
            email.classList.add('user__email');
            email.innerText = json["login"];
            const btnWrapper = document.querySelector('.user__btn-wrapper');
            contentWrapper.append(email, btnWrapper.cloneNode(true));
            student.append(name, contentWrapper);
            return student;
        }

        function createNewStudent() {
            const name = document.getElementById('add_user_name').value;
            const lastName = document.getElementById('add_user_surname').value;
            const email = document.getElementById('add_user_email').value;
            const password = document.getElementById('add_user_password').value;

            let newStudent = {
                "login": email,
                "password": password,
                "name": name,
                "surname": lastName
            };
            postToServer(xmlHttpRequest, newStudent, "");
        }
    }
}