import InteractiveStyles from "./InteractiveStyles.js";

export default class MentorQuestsHandler {
    constructor() {
    };

    handleMentorQuests() {
        const xmlHttpRequest = new XMLHttpRequest();
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
                    const interactiveStyles = new InteractiveStyles();
                    interactiveStyles.styleAddCard();
                    //TODO Get card height after load image
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
            xmlHttpRequest.open("GET","/mentor/quests");
            xmlHttpRequest.send();
        }
    }
}