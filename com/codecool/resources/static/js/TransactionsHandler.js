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
                            for(let i =0; i <response["Quests"].length; i++) {
                                transactionsList.appendChild(createTransaction(response, i, "Quests"));
                            }
                            for(let i =0; i <response["Cards"].length; i++) {
                                transactionsList.appendChild(createTransaction(response, i, "Cards"));
                            }
                            getStudentCoins();
                        }
                    }
                };
        function  createTransaction(transactionJson, index, type) {
            const item = document.createElement('li');
            item.classList.add('transactions__item');
            const title = document.createElement('h3');
            title.classList.add('transactions__title');
            title.innerText = transactionJson[type][index]["item"]["title"];
            const wrapper = document.createElement('div');
            wrapper.classList.add('transactions__content-wrapper');
            const date = document.createElement('p');
            date.innerText = transactionJson[type][index]["date"];
            date.classList.add('transactions__date');
            const amount = document.createElement('p');
            amount.innerText = transactionJson[type][index]["cost"];
            amount.classList.add('transactions__amount');
            if(type === "Cards"){
                amount.innerText = "-" + amount.innerText;
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