'use strict';

import toggleMenu from './menu.js';
import expandTableContent from './table_expander.js';
import AddMentorPopUpController from './AddMentorPopUpController.js';
import EditMentorPopUpController from './MentorEditor.js';

toggleMenu();
expandTableContent();

const addMentorPopUpController = new AddMentorPopUpController();
const editMentorPopUpController = new EditMentorPopUpController();
addMentorPopUpController.openAddMentorPopUp();
addMentorPopUpController.closeAddMentorPopUp();
editMentorPopUpController.openEditMentorPopUp();
editMentorPopUpController.closeEditMentorPopUp();