'use strict';

import toggleMenu from './menu.js';
import expandTableContent from './TableExpander.js';
import AddUserPopUpController from './AddUserPopUpController.js';
import LoginDataHandler from "./LoginDataHandler.js";
import CardsHttpHandler from "./CardsHttpHandler.js";
import InteractiveStyles from './InteractiveStyles.js';
import TransactionsHandler from "./TransactionsHandler.js";
import MentorStudentHandler from "./MentorStudentHandler.js";

const loginDataHandler = new LoginDataHandler();
loginDataHandler.handleUserData();

if (window.location.pathname === "/static/student_store.html") {
    const cardsHttpHandler = new CardsHttpHandler();
    cardsHttpHandler.handleCards();
}

if (window.location.pathname === "/static/student_transactions.html") {
    const transactionsHandler = new TransactionsHandler();
    transactionsHandler.handleStudentTransactions();
}

if (window.location.pathname === "/static/mentor_students_list.html") {
    const mentorStudentHandler = new MentorStudentHandler();
    mentorStudentHandler.handleStudentList();
}

if (!document.querySelector('.index')) {
    toggleMenu();
    expandTableContent();
}

const addUserPopUpController = new AddUserPopUpController();

const interactiveStyles = new InteractiveStyles();
interactiveStyles.styleAddCard();

addUserPopUpController.openAddUserPopUp();
addUserPopUpController.closeAddUserPopUp();