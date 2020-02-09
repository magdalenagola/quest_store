'use strict';

import toggleMenu from './menu.js';
import expandTableContent from './table_expander.js';
import AddMentorPopUpController from './AddMentorPopUpController.js';

toggleMenu();
expandTableContent();

const addMentorPopUpController = new AddMentorPopUpController();
addMentorPopUpController.openAddMentorPopUp();
addMentorPopUpController.closeAddMentorPopUp();