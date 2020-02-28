import WalletHttpHandler from "./WalletHttpHandler.js";

export default class CardsHttpHandler{
    constructor() {}
    handleCards() {
        const xmlHttpRequest = new XMLHttpRequest();
        const walletHttpHandler = new WalletHttpHandler();
        getFromServer(xmlHttpRequest);
        const cardList = document.querySelector('.cards__wrapper');
        xmlHttpRequest.onreadystatechange = function () {
            function buy() {
                const cardId = this.parentNode.parentElement.getAttribute("id").split("_")[1];
                postToServer(xmlHttpRequest,cardId);
            }

            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                const responseUrl = xmlHttpRequest.responseURL.split('/');
                const responseContext = responseUrl[responseUrl.length - 1];
                if (xmlHttpRequest.status === 200 && responseContext === 'cards') {
                    let response = xmlHttpRequest.responseText;
                    response = JSON.parse(response);

                    for(let i =0; i <response.length; i++) {
                        cardList.appendChild(createCard(response, i));

                    }
                    const baskets = document.querySelectorAll(".card__basket");
                    let basket;
                    for(basket of baskets){
                        basket.addEventListener("click",buy);
                    }
                    getStudentCoins();
                }
                if(xmlHttpRequest.status === 200 && responseContext !== 'cards'){
                    alert("SUCCESSFUL TRANSACTION! :)");
                    getStudentCoins();
                }
                if(xmlHttpRequest.status === 303){
                    window.location.replace("index.html")
                }
                if(xmlHttpRequest.status === 403){
                    alert("You can't afford it ! :C")
                }
            } else {
                console.log("No response yet");
            }
        };
        function createCard(cardJson, index) {
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
            const basket = document.createElement('a');
            basket.classList.add('card__basket');
            cardImgWrapper.append(cardImg, cardCoin);
            cardWrapper.append(cardTitle, cardImgWrapper, cardDescription, basket);
            card.appendChild(cardWrapper);
            return card;
        }
        function getFromServer(xmlHttpRequest) {
            xmlHttpRequest.open('GET', '/cards');
            xmlHttpRequest.send();
        }
        function getStudentCoins(){
            const xmlHttpRequest = new XMLHttpRequest();
            xmlHttpRequest.open('GET', '/coins');
            xmlHttpRequest.send();
            walletHttpHandler.handleCoins(xmlHttpRequest);
        }
        function postToServer(xmlHttpRequest,cardId) {
            xmlHttpRequest.open('POST', `/cards/buy/${cardId}`);
            xmlHttpRequest.send(JSON.stringify({"name":3}));

        }
    }
}