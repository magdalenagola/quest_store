export default class WalletHttpHandler {
    constructor() {
    }

    handleCoins(xmlHttpRequest) {
        xmlHttpRequest.onreadystatechange = function () {
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                if(xmlHttpRequest.status === 200){
                    let response = xmlHttpRequest.responseText;
                    updateStudentCoins(response);
                }
            }
        };
        function  updateStudentCoins(response) {
            const coinWrapper = document.querySelector(".student__wallet");
            coinWrapper.innerText = JSON.parse(response);
        }
    }

}