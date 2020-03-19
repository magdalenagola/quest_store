import InteractiveStyles from "./InteractiveStyles.js";

export default class CreateUserHandler {

    createStudent(studentId) {
        const interactiveStyles = new InteractiveStyles();
        const popup = document.querySelector('.popup');
        const xmlHttpRequest = new XMLHttpRequest();
        createNewStudent();
        xmlHttpRequest.onreadystatechange = function () {
            console.log(xmlHttpRequest.responseText)
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                if (xmlHttpRequest.status === 200) {
                    if(xmlHttpRequest.responseText == "saved"){
                        const message = 'Successfully added!';
                        interactiveStyles.showPopup(popup, message);
                    }
                }
            }
        };

        function postToServer(xmlHttpRequest, newStudent, userId) {
            xmlHttpRequest.open('POST', `/mentor/students/add/${userId}`);
            xmlHttpRequest.send(JSON.stringify(newStudent));
        }

        function postMentorToServer(xmlHttpRequest, newUser, userId) {
            xmlHttpRequest.open('POST', `/manager/mentor/add/${userId}`);
            xmlHttpRequest.send(JSON.stringify(newUser));
        }

        function createNewStudent() {
            const name = document.getElementById('add_user_name').value;
            console.log(name);
            const lastName = document.getElementById('add_user_surname').value;
            const email = document.getElementById('add_user_email').value;
            const password = document.getElementById('add_user_password').value;

            let newUser = {
                "login": email,
                "password": password,
                "name": name,
                "surname": lastName
            };

            if (window.location.pathname === "/static/manager_mentors_list.html") {
                const primarySkill = document.getElementById('add_user_primary-skill').value;
                const earnings  = document.getElementById('add_user_salary').value;
                console.log(primarySkill);
                newUser["earnings"] = earnings;
                newUser["primarySkill"] = primarySkill;
                postMentorToServer(xmlHttpRequest, newUser, "");
            } else {
                postToServer(xmlHttpRequest, newUser, "");
            }
        }
    }
}