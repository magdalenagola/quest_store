'use strict';

import toggleMenu from './menu.js';
import expandTableContent from './TableExpander.js';
import AddUserPopUpController from './AddUserPopUpController.js';
import EditUserPopUpController from './UserEditor.js';
import LoginDataHandler from "./LoginDataHandler.js";
import CardsHttpHandler from "./CardsHttpHandler.js";

const loginDataHandler = new LoginDataHandler();
loginDataHandler.handleUserData();

if (window.location.pathname === "/static/student_store.html") {
    const cardsHttpHandler = new CardsHttpHandler();
    cardsHttpHandler.handleCards();
}

// toggleMenu();
// expandTableContent();
//
// const addUserPopUpController = new AddUserPopUpController();
// const editUserPopUpController = new EditUserPopUpController();
// addUserPopUpController.openAddUserPopUp();
// addUserPopUpController.closeAddUserPopUp();
// editUserPopUpController.openEditUserPopUp();
// editUserPopUpController.closeEditUserPopUp();