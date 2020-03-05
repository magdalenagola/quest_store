import InteractiveStyles from "./InteractiveStyles.js";
import MentorStudentHandler from "./MentorStudentHandler.js";

export default class EditStudent {

    editStudent(studentId) {
        const xmlHttpRequest =  new XMLHttpRequest();
        editNewStudent(studentId);
        xmlHttpRequest.onreadystatechange = function () {
            console.log(xmlHttpRequest.responseText);
            const interactiveStyles = new InteractiveStyles();
            const popup = document.querySelector('.popup');
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                if (xmlHttpRequest.status === 200) {
                    if (xmlHttpRequest.responseText == "updated") {
                        const message = 'Successfully updated!';
                        interactiveStyles.showPopup(popup, message);
                    }
                }
            }
        };


        function editNewStudent(userId) {
            const name = document.getElementById('edit_user_name').value;
            const lastName = document.getElementById('edit_user_surname').value;
            const email = document.getElementById('edit_user_email').value;
            const password = document.getElementById('edit_user_password').value;

            let newUser = {
                "id": userId,
                "login": email,
                "password": password,
                "name": name,
                "surname": lastName
            };
            console.log(userId);
            if (window.location.pathname === "/static/mentor_students_list.html") {
                postToServer(xmlHttpRequest, newUser, userId);
            }

            if (window.location.pathname === "/static/manager_mentors_list.html") {
                postMentorToServer(xmlHttpRequest, newUser, userId);
            }
        }

        function postMentorToServer(xmlHttpRequest, newUser, userId)
        {
            xmlHttpRequest.open('POST', `/manager/mentor/add/${userId}`);
            xmlHttpRequest.send(JSON.stringify(newUser));
        }

        function postToServer(xmlHttpRequest, newStudent, userId)
        {
            xmlHttpRequest.open('POST', `/mentor/students/add/${userId}`);
            xmlHttpRequest.send(JSON.stringify(newStudent));
        }
    }
}