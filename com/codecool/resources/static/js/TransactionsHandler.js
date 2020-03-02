import WalletHttpHandler from "./WalletHttpHandler.js";

export default class TransactionsHandler {
    constructor() {
    };

    handleStudentTransactions() {
        const xmlHttpRequest = new XMLHttpRequest();
        const walletHttpHandler = new WalletHttpHandler();
        getFromServer();
                xmlHttpRequest.onreadystatechange = function () {
                    if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                        if (xmlHttpRequest.status === 200) {
                            let response = xmlHttpRequest.responseText;
                            response = JSON.parse(response);
                            console.log(response);
                            const transactionsList = document.querySelector(".transactions__list");
                            for(let i =0; i <response.length; i++) {
                                transactionsList.appendChild(createTransaction(response, i));

                            }
                            getStudentCoins();
                        }
                    }
                };
        function  createTransaction(transactionJson, index) {
            const item = document.createElement('li');
            item.classList.add('transactions__item');
            const title = document.createElement('h3');
            title.classList.add('transactions__title');
            title.innerText = transactionJson[index]["item"]["title"];
            const wrapper = document.createElement('div');
            wrapper.classList.add('transactions__content-wrapper');
            const date = document.createElement('p');
            date.innerText = transactionJson[index]["date"];
            date.classList.add('transaction__date');
            const amount = document.createElement('p');
            amount.innerText = transactionJson[index]["cost"];
            amount.classList.add('transactions__amount');
            if(transactionJson[index]["cost"] < 0 ){
                item.classList.add("transactions__item--bought")
            }
            amount.innerText += " coins";
            wrapper.append(date, amount);
            item.append(title, wrapper);
            return item;
        }
        function getFromServer() {
            xmlHttpRequest.open("GET","/student/transactions");
            xmlHttpRequest.send();
        }
        function getStudentCoins(){
            const xmlHttpRequest = new XMLHttpRequest();
            xmlHttpRequest.open('GET', '/coins');
            xmlHttpRequest.send();
            walletHttpHandler.handleCoins(xmlHttpRequest);
        }
    }
}