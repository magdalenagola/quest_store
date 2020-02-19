export default class LoginDataHandler {
    constructor (){};
    handleUserData() {
        const xmlHttpRequest = new XMLHttpRequest();
        const submitBtn = document.querySelector('.login__btn');
        submitBtn.onclick = () => {
            const loginData = getLoginData();
            postToServer(xmlHttpRequest, JSON.stringify(loginData));
        }

        xmlHttpRequest.onreadystatechange = function () {
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE ) {
                if (xmlHttpRequest.status === 200) {
                    // let response = xmlHttpRequest.responseText;
                    // response = JSON.parse(response);
                } else {
                    alert(xmlHttpRequest.status);
                }
            } else {
                console.log('onreadystatechange error');
            }
        }

        function getFromServer(xmlHttpRequest) {
            xmlHttpRequest.open('GET', '/login');
            xmlHttpRequest.send();
        }

        function postToServer(xmlHttpRequest, loginData) {
            xmlHttpRequest.open('POST', '/login');
            xmlHttpRequest.send(loginData);
            if (xmlHttpRequest.response == "") {
                alert('wrong data provided')
            }
        }

        function getLoginData() {
            const login = document.querySelector('.login__input').value;
            const password = document.querySelector('.password__input').value;
            return [login, password];
        }
    }
}