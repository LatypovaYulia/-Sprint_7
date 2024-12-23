import api.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static model.CourierGenerator.getRandomCourierForLogIn;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {

    protected CourierData courierData;
    protected CourierData courierIncorrectData;
    protected Integer courierId;
    protected CourierApi courierApi;

    @Before
    public void setUp() {
        courierApi = new CourierApi();
    }

    @After
    public void cleanUp() {
        if (courierData != null) {

            ValidatableResponse loginResponse = courierApi.loginCourier(courierData);
            courierId = loginResponse.extract().path("id");

            if (courierId != null) {
                courierApi.deleteCourier(courierId)
                        .log().all()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .body("ok", is(true));
            }
        }
    }

    @Test
    @DisplayName("Check courier can log in")
    @Description("Check that a courier can log in if all fields are passed")
    public void courierCanBeLogInTest() {
        courierData = getRandomCourierForLogIn("Rinat", "password");
        ValidatableResponse response = courierApi.createCourier(courierData);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));

        ValidatableResponse loginResponse = courierApi.loginCourier(courierData);
        loginResponse.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Return error if incorrect login")
    @Description("System will return error if enter incorrect login")
    public void incorrectLoginReturnErrorTest() {
        courierData = new CourierData("Denis", "password123");
        ValidatableResponse response = courierApi.createCourier(courierData);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));

        courierIncorrectData = new CourierData("Denis123", "password123");
        ValidatableResponse loginResponse = courierApi.loginCourier(courierIncorrectData);
        loginResponse.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Return error if incorrect password")
    @Description("System will return error if enter incorrect password")
    public void incorrectPasswordReturnErrorTest() {
        courierData = new CourierData("Denis", "password123");
        ValidatableResponse response = courierApi.createCourier(courierData);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));

        courierIncorrectData = new CourierData("Denis", "password456");
        ValidatableResponse loginResponse = courierApi.loginCourier(courierIncorrectData);
        loginResponse.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Return error if empty field login")
    @Description("System will return error if send empty field login")
    public void  emptyFieldLoginReturnErrorTest() {

        courierIncorrectData = new CourierData("", "password444");
        ValidatableResponse loginResponse = courierApi.loginCourier(courierIncorrectData);
        loginResponse.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Return error if empty field password")
    @Description("System will return error if send empty field password")
    public void  emptyFieldPasswordReturnErrorTest() {

        courierIncorrectData = new CourierData("Yulia21", "");
        ValidatableResponse loginResponse = courierApi.loginCourier(courierIncorrectData);
        loginResponse.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Return error if nonexistent courier")
    @Description("System will return error if send nonexistent courier")
    public void  nonexistentCourierReturnErrorTest() {
        courierData = getRandomCourierForLogIn("Rinat", "password");
        ValidatableResponse loginResponse = courierApi.loginCourier(courierData);
        loginResponse.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }
}
