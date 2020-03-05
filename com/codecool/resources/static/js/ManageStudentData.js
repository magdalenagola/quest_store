export default class ManageStudentData {
    xmlHttpRequest = new XMLHttpRequest();

    deleteStudent(studentId) {
        this.xmlHttpRequest.open('POST', `/mentor/students/delete/${studentId}`);
        this.xmlHttpRequest.send(JSON.stringify(studentId));
    }

    handleStudent(studentId) {
        const xmlHttpRequest = this.xmlHttpRequest;
        createNewStudent();
        editNewStudent(studentId);
        this.deleteStudent(studentId);
        xmlHttpRequest.onreadystatechange = function () {
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                if (xmlHttpRequest.status === 200) {
                    if(xmlHttpRequest.responseText == "saved"){
                        alert("SUCCESSFULLY ADDED")
                        location.reload();
                    }
                    if(xmlHttpRequest.responseText == "updated"){
                        alert("SUCCESSFULLY UPDATED")
                        location.reload();
                    }
                    if(xmlHttpRequest.responseText == "deleted"){
                        alert("SUCCESSFULLY DELETED")
                        location.reload();
                    }
                }
            }
        };
        function postToServer(xmlHttpRequest, newStudent, userId) {
            xmlHttpRequest.open('POST', `/mentor/students/add/${userId}`);
            xmlHttpRequest.send(JSON.stringify(newStudent));
        }

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

        function createNewStudent() {
            const name = document.getElementById('add_user_name').value;
            console.log(name);
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