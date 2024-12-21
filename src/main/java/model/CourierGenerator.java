package ru.practicum.yandex;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    public static CourierData getRandomCourier(String loginParam, String passwordParam,
                                               String firstNameParam){
        String login = loginParam + RandomStringUtils.randomAlphabetic(4);
        String password = passwordParam + RandomStringUtils.randomAlphabetic(4);
        String firstName = firstNameParam + RandomStringUtils.randomAlphabetic(4);

        return new CourierData(login, password, firstName);
    }
}
