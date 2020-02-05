package codecool.java.controller;

import codecool.java.model.Mentor;
import codecool.java.view.View;

import java.util.ArrayList;

public class MentorController {
    CardService cardService = new CardService();
    QuestService questService = new QuestService();
    StudentService studentService = new StudentService();
    public void run(Mentor mentor){
        String[] options = {"Add new codecooler.","Add new quest.","Add new card","Update card",
        "Rate student's assigment","Display students statistics"};
        View.displayOptions(options);
        int userInput = View.getOptionInput(options.length);
        switch(userInput){
            case 1:
                addStudent();
                break;
            case 2:
                addQuest();
                break;
            case 3:
                addCard();
                break;
            case 4:
                updateCard();
                break;
            case 5:
                rateAssigment();
                break;
            case 6:
                displayStatistics();
                break;
        }
    }

    private void displayStatistics() {
        studentService.displayStudentsSummaryCoins();
    }

    private void rateAssigment() {
        ArrayList<Student> students = studentService.selectStudents();
        View.displayUsers(students);
        int choose = View.getUserChoose();
        questService.rateAssigment(students.get(choose));
    }

    private void updateCard() {
        cardService.updateCard();
    }

    private void addCard() {
        cardService.addCard();
    }

    private void addQuest() {
        questService.addQuest();
    }

    private void addStudent() {
        studentService.addStudent();
    }
}
