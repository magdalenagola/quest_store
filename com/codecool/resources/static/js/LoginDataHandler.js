export default class LoginDataHandler {
    constructor() {};
    handleUserData() {
        const xmlHttpRequest = new XMLHttpRequest();
        const submitBtn = document.querySelector('.login__btn');
        const logOutBtn = document.querySelector('.logout__icon');
        if (submitBtn) {
            submitBtn.onclick = (e) => {
                e.preventDefault();
                const loginData = getLoginData();
                postToServer(xmlHttpRequest, JSON.stringify(loginData));
                //TODO dodac cos, zeby czekal na odpowiedz z javy
                xmlHttpRequest.onreadystatechange = function () {
                    if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                        if (xmlHttpRequest.status === 200) {
                            if (xmlHttpRequest.response == "Student"){
                                window.location.replace("student_store.html")
                            }else if (xmlHttpRequest.response == "Mentor"){
                                window.location.replace("mentor_students_list.html")
                            }else if (xmlHttpRequest.response == "Manager"){
                                window.location.replace("manager_mentors_list.html")
                            }
                        } else if(xmlHttpRequest.status === 303){
                            window.location.replace("index.html")
                        }
                    } else {
                        console.log("No response yet");
                    }
                }

                }
            }
        window.onload = () => {
            if (logOutBtn) {
                logOutBtn.onclick = () => {
                    const cookie = document.cookie;
                    document.cookie = "sessionId=; Expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
                    xmlHttpRequest.open('POST', '/login/expired_cookie');
                    xmlHttpRequest.send(cookie);
                }
            }
        }




        function getFromServer(xmlHttpRequest) {
            xmlHttpRequest.open('GET', '/login');
            xmlHttpRequest.send();
        }

        function postToServer(xmlHttpRequest, loginData) {
            xmlHttpRequest.open('POST', '/login');
            xmlHttpRequest.send(loginData);

        }

        function getLoginData() {
            const login = document.querySelector('.login__input').value;
            const password = document.querySelector('.password__input').value;
            return [login, password];
        }
    }
}