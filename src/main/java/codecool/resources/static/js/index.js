import toggleMenu from './menu.js';
import expandTableContent from './TableExpander.js';
import LoginDataHandler from "./LoginDataHandler.js";
import CardsHttpHandler from "./CardsHttpHandler.js";
import TransactionsHandler from "./TransactionsHandler.js";
import MentorStudentHandler from "./MentorStudentHandler.js";
import InventoryHttpHandler from "./InventoryHttpHandler.js";
import MentorQuestHandler from "./MentorQuestHandler.js";
import StudentQuestsHandler from "./StudentQuestsHandler.js";
import ManagerMentorsHandler from "./ManagerMentorsHandler.js";

const loginDataHandler = new LoginDataHandler();
loginDataHandler.handleUserData();

if (window.location.pathname === "/static/manager_mentors_list.html") {
    const managerMentorsHandler = new ManagerMentorsHandler();
    managerMentorsHandler.handleMentorsList();
}

if (window.location.pathname === "/static/student_inventory.html") {
    const inventoryHttpHandler = new InventoryHttpHandler();
    inventoryHttpHandler.handleInventory();
}

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

if (window.location.pathname === "/static/mentor_quests.html") {
    const mentorQuestHandler = new MentorQuestHandler();
    mentorQuestHandler.handleMentorQuests();
}
if (window.location.pathname === "/static/student_quests.html") {
    const studentQuestsHandler = new StudentQuestsHandler();
    studentQuestsHandler.handleStudentQuests();

}

if (!document.querySelector('.index')) {
    toggleMenu();
    expandTableContent();
}
;