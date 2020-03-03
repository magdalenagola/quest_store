export default class MentorStudentHandler {

    handleStudentList() {
        const xmlHttpRequest = new XMLHttpRequest();
        getFromServer();
        xmlHttpRequest.onreadystatechange = function () {
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                if (xmlHttpRequest.status === 200) {
                    let response = xmlHttpRequest.responseText;
                    response = JSON.parse(response);
                    console.log(response);
                    const studentList = document.querySelector(".user__list");
                    for(let i = 0; i < response.length; i++) {
                        studentList.appendChild(createStudentFromDb(response[i]));
                    }
                }
            }
        };

        function getFromServer() {
            xmlHttpRequest.open("GET","/mentor/students");
            xmlHttpRequest.send();
        }

        function createStudentFromDb(json) {
            const student = document.createElement('li');
            student.classList.add('user__item');
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