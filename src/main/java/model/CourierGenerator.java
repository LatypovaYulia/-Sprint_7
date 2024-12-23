package model;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    @Step("Generate random courier")
    public static CourierData getRandomCourier(String loginParam, String passwordParam,
                                               String firstNameParam){
        String login = loginParam + RandomStringUtils.randomAlphabetic(4);
        String password = passwordParam + RandomStringUtils.randomAlphabetic(4);
        String firstName = firstNameParam + RandomStringUtils.randomAlphabetic(4);

        return new CourierData(login, password, firstName);
    }

    @Step("Generate random courier without login")
    public static CourierData getRandomCourierWithoutLogin(String loginParam, String passwordParam,
                                               String firstNameParam){
        String login = loginParam;
        String password = passwordParam + RandomStringUtils.randomAlphabetic(4);
        String firstName = firstNameParam + RandomStringUtils.randomAlphabetic(4);

        return new CourierData(login, password, firstName);
    }

    @Step("Generate random courier without password")
    public static CourierData getRandomCourierWithoutPassword(String loginParam, String passwordParam,
                                                           String firstNameParam){
        String login = loginParam + RandomStringUtils.randomAlphabetic(4);
        String password = passwordParam;
        String firstName = firstNameParam + RandomStringUtils.randomAlphabetic(4);

        return new CourierData(login, password, firstName);
    }
    @Step("Generate random courier without firstName")
    public static CourierData getRandomCourierWithoutFirstName(String loginParam, String passwordParam,
                                               String firstNameParam){
        String login = loginParam + RandomStringUtils.randomAlphabetic(4);
        String password = passwordParam + RandomStringUtils.randomAlphabetic(4);
        String firstName = firstNameParam;

        return new CourierData(login, password, firstName);
    }

    @Step("Generate random courier for log in")
    public static CourierData getRandomCourierForLogIn(String loginParam, String passwordParam){
        String login = loginParam + RandomStringUtils.randomAlphabetic(4);
        String password = passwordParam + RandomStringUtils.randomAlphabetic(4);

        return new CourierData(login, password);
    }

}
