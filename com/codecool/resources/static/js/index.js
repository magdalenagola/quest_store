'use strict';

import toggleMenu from './menu.js';
import expandTableContent from './table_expander.js';
import AddUserPopUpController from './AddUserPopUpController.js';
import EditUserPopUpController from './UserEditor.js';

toggleMenu();
expandTableContent();

const addUserPopUpController = new AddUserPopUpController();
const editUserPopUpController = new EditUserPopUpController();
addUserPopUpController.openAddUserPopUp();
addUserPopUpController.closeAddUserPopUp();
editUserPopUpController.openEditUserPopUp();
editUserPopUpController.closeEditUserPopUp();