export default class CreateStudent {

    createStudent(studentId) {
        const xmlHttpRequest = new XMLHttpRequest();
        createNewStudent();
        xmlHttpRequest.onreadystatechange = function () {
            console.log(xmlHttpRequest.responseText)
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                if (xmlHttpRequest.status === 200) {
                    if(xmlHttpRequest.responseText == "saved"){
                        alert("SUCCESSFULLY ADDED")
                        location.reload();
                    }
                }
            }
        };

        function postToServer(xmlHttpRequest, newStudent, userId) {
            xmlHttpRequest.open('POST', `/mentor/students/add/${userId}`);
            xmlHttpRequest.send(JSON.stringify(newStudent));
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