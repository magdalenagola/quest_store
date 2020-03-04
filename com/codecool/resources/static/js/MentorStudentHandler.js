import UserEditor from "./UserEditor.js";
import AddUserPopUpController from "./AddUserPopUpController.js";

export default class MentorStudentHandler {


    handleStudentList() {
        const addUserPopUpController = new AddUserPopUpController();
        addUserPopUpController.openAddUserPopUp();
        addUserPopUpController.closeAddUserPopUp();
        const xmlHttpRequest = new XMLHttpRequest();
        getStudentsFromServer();
        xmlHttpRequest.onreadystatechange = function () {
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                if (xmlHttpRequest.status === 200) {
                    let response = xmlHttpRequest.responseText;
                    response = JSON.parse(response);
                    const studentList = document.querySelector(".user__list");
                    for(let i = 0; i < response.length; i++) {
                        studentList.appendChild(createStudentFromDb(response[i]));
                    }
                    const userEditor = new UserEditor();
                    userEditor.openEditUserPopUp();
                    userEditor.closeEditUserPopUp();
                }
            }
        };

        function listenAddBtn() {
            const submitBtn = document.querySelector('.add-user__btn');
            const form = document.querySelector('.add-user__form');
            console.log(submitBtn);
            submitBtn.onclick = (e) => {
                e.preventDefault();
                console.log(form.getAttributeNames());
                if (form.getAttribute('isValid') === 'true') {
                    createNewStudent();
                    console.log('create');
                }
            }
        }

        function getStudentByIdFromServer() {
            xmlHttpRequest.open("GET",`/mentor/students/get/${id}`);
            xmlHttpRequest.send();
        }

        function getStudentsFromServer() {
            xmlHttpRequest.open("GET","/mentor/students");
            xmlHttpRequest.send();
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
    }
}