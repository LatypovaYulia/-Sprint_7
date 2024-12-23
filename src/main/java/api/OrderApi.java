package api;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.OrderData;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class OrderApi extends RestApi {
    public static final String CREATE_ORDER_URI = "/api/v1/orders";

    @Step("Create order")
    public ValidatableResponse createOrder(OrderData order) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_URI)
                .then();
    }

    @Step("Get list of orders")
    public ValidatableResponse getListOfOrders(int limit) {
        return given()
                .spec(requestSpecification())
                .and()
                .queryParam("limit", limit)
                .when()
                .get(CREATE_ORDER_URI)
                .then()
                .time(lessThan(250000L));
    }
}
