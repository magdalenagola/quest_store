import AddUserPopUpController from "./AddUserPopUpController.js";
import UserEditor from "./UserEditor.js";

export default class MentorStudentHandler {

    manageStudentData() {
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
                    }
                }
            }
        };

        function getStudentByIdFromServer() {
            xmlHttpRequest.open("GET",`/mentor/students/get/${id}`);
            xmlHttpRequest.send();
        }

        function postToServer(xmlHttpRequest, newStudent, userId) {
            xmlHttpRequest.open('POST', `/mentor/students/add/${userId}`);
            xmlHttpRequest.send(JSON.stringify(newStudent));
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