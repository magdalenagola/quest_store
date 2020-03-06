import InteractiveStyles from "./InteractiveStyles.js";

export default class EditUserHandler {

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

            if (window.location.pathname === "/static/mentor_students_list.html") {
                postToServer(xmlHttpRequest, newUser, userId);
            }

            if (window.location.pathname === "/static/manager_mentors_list.html") {
                const primarySkill = document.getElementById('edit_user_primary-skill').value;
                const earnings  = document.getElementById('edit_user_salary').value;
                console.log(primarySkill);
                newUser["earnings"] = earnings;
                newUser["primarySkill"] = primarySkill;
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