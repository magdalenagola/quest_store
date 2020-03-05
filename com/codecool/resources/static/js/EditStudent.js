import InteractiveStyles from "./InteractiveStyles.js";

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


        function editNewStudent(studentId) {
            const name = document.getElementById('edit_user_name').value;
            const lastName = document.getElementById('edit_user_surname').value;
            const email = document.getElementById('edit_user_email').value;
            const password = document.getElementById('edit_user_password').value;

            let newStudent = {
                "id": studentId,
                "login": email,
                "password": password,
                "name": name,
                "surname": lastName
            };
            console.log(newStudent);
            postToServer(xmlHttpRequest, newStudent, studentId);
        }

        function postToServer(xmlHttpRequest, newStudent, userId)
        {
            xmlHttpRequest.open('POST', `/mentor/students/add/${userId}`);
            xmlHttpRequest.send(JSON.stringify(newStudent));
        }
    }
}