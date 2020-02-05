package codecool.java.model;

import codecool.java.controller.MentorController;

public class Mentor extends User{
    @Override
    public void start() {
        MentorController controller = new MentorController();
        controller.run(this);
    }

}
