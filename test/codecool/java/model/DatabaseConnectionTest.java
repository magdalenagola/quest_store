package codecool.java.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {

//    @Test
//    public void shouldReturnEnvFromFile() {
//        String expected = "prod";
//        String actual = DatabaseConnection.getEnv();
//        assertEquals(expected, actual);
//    }

    @Test
    public void shouldReturnProdDbLoginCredentials() {
        String[] actual = DatabaseConnection.INSTANCE.getDbCredentials();
        String expectedUrl = "jdbc:postgresql://ec2-176-34-237-141.eu-west-1.compute.amazonaws.com:5432/dbc5jifafq3j1h?sslmode=require";
        String expectedUser = "utiuhfgjckzuoq";
        String expectedPassword = "17954f632e3663cbadb55550dd636f4c3a645ade56c3342ee89f71fc732c9672";
        assertEquals(expectedUrl, actual[0]);
        assertEquals(expectedUser, actual[1]);
        assertEquals(expectedPassword, actual[2]);
    }

    @Test
    public void shouldReturnTestDbLoginCredentials() {
        DatabaseConnection.INSTANCE.setEnv("test");
        String[] actual = DatabaseConnection.INSTANCE.getDbCredentials();
        String expectedUrl = "jdbc:postgresql://ec2-54-247-169-129.eu-west-1.compute.amazonaws.com:5432/de3ku5gmultp9?sslmode=require";
        String expectedUser = "xxgyliphwfrqpv";
        String expectedPassword = "53bfd94262a849f08a7ef8f353b8b816a1aaecb11d5d7a7d0b5124e01173b86a";
        assertEquals(expectedUrl, actual[0]);
        assertEquals(expectedUser, actual[1]);
        assertEquals(expectedPassword, actual[2]);
    }


}