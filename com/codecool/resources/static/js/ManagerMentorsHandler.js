import FormValidator from "./FormValidator.js";
import AddUserPopUpController from "./AddUserPopUpController.js";
import UserEditor from "./UserEditor.js";
import InteractiveStyles from "./InteractiveStyles.js";

export default class ManagerMentorsHandler {

    handleMentorsList() {
        const interactiveStyles = new InteractiveStyles();
        const popup = document.querySelector('.popup');
        const addUserPopUpController = new AddUserPopUpController();
        addUserPopUpController.openAddUserPopUp();
        addUserPopUpController.closeAddUserPopUp();
        const xmlHttpRequest = new XMLHttpRequest();
        getMentorsFromServer();
        xmlHttpRequest.onreadystatechange = function () {
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                if (xmlHttpRequest.status === 200) {
                    let response = xmlHttpRequest.responseText;
                    if (response === 'saved') {
                        const message = 'Successfully updated!';
                        interactiveStyles.showPopup(popup, message);
                    } else {
                        response = JSON.parse(response);
                       // console.log(response);
                    }
                    const mentorsList = document.querySelector(".user__list");
                    for (let i = 0; i < response.length; i++) {
                       mentorsList.appendChild(createMentorFromDb(response[i]));
                       //toggleForm();
                    }
                    const userEditor = new UserEditor();
                    userEditor.openEditUserPopUp();
                    userEditor.closeEditUserPopUp();
                }
            }
        }
        function getMentorsFromServer() {
            xmlHttpRequest.open("GET","/manager/mentors");
            xmlHttpRequest.send();
        }

        function postMentorToServer(xmlHttpRequest, newMentor, userId)
        {
            xmlHttpRequest.open('POST', `/manager/mentors/add/${userId}`);
            xmlHttpRequest.send(JSON.stringify(newMentor));
        }

        function editNewMentor(mentorId) {
            const name = document.getElementById('edit_user_name').value;
            const lastName = document.getElementById('edit_user_surname').value;
            const email = document.getElementById('edit_user_email').value;
            const password = document.getElementById('edit_user_password').value;

            let newMentor = {
                "id": mentorId,
                "login": email,
                "password": password,
                "name": name,
                "surname": lastName
            };
            console.log(newMentor);
            postMentorToServer(xmlHttpRequest, newStudent, studentId);
        }

        function createMentorFromDb(json) {
            const mentor = document.createElement('li');
            mentor.classList.add('user__item');
            mentor.setAttribute('id', `mentor_${json["id"]}`);
            const name = document.createElement('h3');
            name.classList.add('user__name');
            name.innerText = json["name"];
            const contentWrapper = document.createElement('div');
            contentWrapper.classList.add('user__content-wrapper');
            const skill = document.createElement('p');
            skill.classList.add('user__skill');
            const salary = document.createElement('p');
            salary.classList.add('user__salary');
            const email = document.createElement('p');
            email.classList.add('user__email');
            skill.innerText = json["primarySkill"];
            salary.innerText = json["earnings"] + " $";
            email.innerText = json["login"];
            const btnWrapper = document.querySelector('.user__btn-wrapper');
            contentWrapper.append(skill, salary, email, btnWrapper.cloneNode(true));
            mentor.append(name, contentWrapper);
            return mentor;
        }
    }
}