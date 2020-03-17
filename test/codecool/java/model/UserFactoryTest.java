package codecool.java.model;

import codecool.java.dao.NotInDatabaseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {
    UserFactory userFactory = new UserFactory();


    @Test
    public void shouldThrowNotInDatabaseExceptionWhenUserTypeIdDoesNotExist(){
        assertThrows(NotInDatabaseException.class, ()-> {
            userFactory.createUser(1, "some@emial.com", "xxxxx", "John", "Kowalski", 22, true);
        });
    }

    @Test
    public void shouldReturnMentorUserTypeObjectWhenUserTypeIdMatches() throws NotInDatabaseException {
        int mentorTypeId = 2;
        User user = userFactory.createUser(1, "some@emial.com", "xxxxx", "John", "Kowalski", mentorTypeId, true);
        assertEquals("Mentor", user.getClass().getSimpleName() );
    }

    @Test
    public void shouldReturnManagerUserTypeObjectWhenUserTypeIdMatches() throws NotInDatabaseException {
        int managerTypeId = 3;
        User user = userFactory.createUser(1, "some@emial.com", "xxxxx", "John", "Kowalski", managerTypeId, true);
        assertEquals("Manager", user.getClass().getSimpleName() );
    }

    @Test
    public void shouldReturnStudentUserTypeObjectWhenUserTypeIdMatches() throws NotInDatabaseException {
        int studentTypeId = 1;
        User user = userFactory.createUser(1, "some@emial.com", "xxxxx", "John", "Kowalski", studentTypeId, true);
        assertEquals("Student", user.getClass().getSimpleName() );
    }

}