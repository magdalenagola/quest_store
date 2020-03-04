import WalletHttpHandler from "./WalletHttpHandler.js";

export default class InventoryHttpHandler {
    constructor() {
    };

    handleInventory() {
        const xmlHttpRequest = new XMLHttpRequest();
        const walletHttpHandler = new WalletHttpHandler();
        getFromServer();
        const inventoryList = document.querySelector('.cards__wrapper');
        xmlHttpRequest.onreadystatechange = function () {
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                if (xmlHttpRequest.status === 200) {
                    let response = xmlHttpRequest.responseText;
                    response = JSON.parse(response);
                    for (let i = 0; i < response.length; i++) {
                        inventoryList.appendChild(createInventoryCard(response, i));
                    }
                    getStudentCoins();
                }
                if(xmlHttpRequest.status === 303){
                    window.location.replace("index.html")
                }
            }
        };

                function createInventoryCard(cardJson, index) {
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
                    const cardDescription = document.createElement('p');
                    cardDescription.classList.add('card__description');
                    cardDescription.innerText = cardJson[index]['description'];
                    cardImgWrapper.append(cardImg);
                    cardWrapper.append(cardTitle, cardImgWrapper, cardDescription);
                    card.appendChild(cardWrapper);
                    return card;
                }

                function getFromServer() {
                    xmlHttpRequest.open("GET", "/student/inventory");
                    xmlHttpRequest.send();
                }

                function getStudentCoins() {
                    const xmlHttpRequest = new XMLHttpRequest();
                    xmlHttpRequest.open('GET', '/coins');
                    xmlHttpRequest.send();
                    walletHttpHandler.handleCoins(xmlHttpRequest);
                }
            }
        }



