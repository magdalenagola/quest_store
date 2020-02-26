export default class LoginDataHandler {
    constructor() {};
    handleUserData() {
        const xmlHttpRequest = new XMLHttpRequest();
        const submitBtn = document.querySelector('.login__btn');
        if (submitBtn) {
            submitBtn.onclick = (e) => {
                e.preventDefault();
                const loginData = getLoginData();
                postToServer(xmlHttpRequest, JSON.stringify(loginData));
                //TODO dodac cos, zeby czekal na odpowiedz z javy
                xmlHttpRequest.onreadystatechange = function () {
                    if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                        if (xmlHttpRequest.status === 200) {
                            if (xmlHttpRequest.response == "student"){
                                window.location.replace("student_store.html")
                            }
                            console.log("200");
                            console.log(document.cookie);
                        } else {
                            alert(xmlHttpRequest.status);
                        }
                    } else {
                        console.log("No response yet");
                        //console.log('onreadystatechange error');
                    }
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
            console.log("POST");
            // if (xmlHttpRequest.response == "") {
            //     alert('wrong data provided')
            // }
        }

        function getLoginData() {
            const login = document.querySelector('.login__input').value;
            const password = document.querySelector('.password__input').value;
            return [login, password];
        }
    }
}