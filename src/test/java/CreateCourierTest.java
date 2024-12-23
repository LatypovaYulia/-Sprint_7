import api.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.CourierData;
import static model.CourierGenerator.*;
import static org.hamcrest.CoreMatchers.is;

public class CreateCourierTest {

    protected CourierData courierData;
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
    @DisplayName("Check courier can be created")
    @Description("Check that a courier can be created if all fields are passed")
    public void courierCanBeCreatedTest() {
        courierData = getRandomCourier("Yulia", "password", "Yulia");

        ValidatableResponse response = courierApi.createCourier(courierData);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Cannot create two identical couriers")
    @Description("Check that it is impossible to create couriers with the same data and return error")
    public void createTwoIdenticalCouriersTest() {
        courierData = getRandomCourier("Olga", "password", "Olga");

        ValidatableResponse firstResponse = courierApi.createCourier(courierData);

        firstResponse.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));

        ValidatableResponse secondResponse = courierApi.createCourier(courierData);

        secondResponse.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", is("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Cannot create courier without login")
    @Description("Check that it is impossible to create a courier without login and return error")
    public void createCourierWithoutLoginTest() {
        courierData = getRandomCourierWithoutLogin("", "password", "Mary");

        ValidatableResponse response = courierApi.createCourier(courierData);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Cannot create courier without password")
    @Description("Check that it is impossible to create a courier without password and return error")
    public void createCourierWithoutPasswordTest() {
        courierData = getRandomCourierWithoutPassword("Sergey", "", "Sergey");

        ValidatableResponse response = courierApi.createCourier(courierData);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Courier can be created without firstName")
    @Description("Check that a courier can be created without firstName")
    public void createCourierWithoutFirstNameTest() {
        courierData = getRandomCourierWithoutFirstName("Roman", "password", "");

        ValidatableResponse response = courierApi.createCourier(courierData);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }
}
