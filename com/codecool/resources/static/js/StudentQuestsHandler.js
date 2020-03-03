import WalletHttpHandler from "./WalletHttpHandler.js";

export default class StudentQuestsHandler {
    constructor() {
    };

    handleStudentQuests() {
        const xmlHttpRequest = new XMLHttpRequest();
        const walletHttpHandler = new WalletHttpHandler();
        getFromServer();
        xmlHttpRequest.onreadystatechange = function () {
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                if (xmlHttpRequest.status === 200) {
                    let response = xmlHttpRequest.responseText;
                    response = JSON.parse(response);
                    console.log(response);
                    const quests = document.querySelector(".cards__wrapper");
                    for(let i =0; i <response.length; i++) {
                        quests.appendChild(createQuest(response, i));
                    }
                    getStudentCoins();
                }
            }
        };
        function  createQuest(cardJson, index) {
            const card = document.createElement('li');
            card.setAttribute('id', `card_${cardJson[index]["id"]}`);
            card.classList.add("card");
            const cardWrapper = document.createElement('div');
            cardWrapper.classList.add('card__content-wrapper');
            const cardTitle = document.createElement('h3');
            cardTitle.classList.add('card__title');
            cardTitle.innerText = cardJson[index]['title'];
            const cardImgWrapper = document.createElement('div');
            cardImgWrapper.classList.add('card__img-wrapper');
            const cardImg = document.createElement('img');
            cardImg.setAttribute('alt', 'card');
            cardImg.setAttribute('src', `${cardJson[index]["image"]}`);
            const cardCoin = document.createElement('span');
            cardCoin.classList.add('card__coin');
            cardCoin.innerText = cardJson[index]['cost'];
            const cardDescription = document.createElement('p');
            cardDescription.classList.add('card__description');
            cardDescription.innerText = cardJson[index]['description'];
            cardImgWrapper.append(cardImg, cardCoin);
            cardWrapper.append(cardTitle, cardImgWrapper, cardDescription);
            card.appendChild(cardWrapper);
            return card;
        }
        function getFromServer() {
            xmlHttpRequest.open("GET","/student/quests");
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