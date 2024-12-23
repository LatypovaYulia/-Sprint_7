import api.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetListOfOrdersTest {

    protected OrderApi orderApi;

    @Before
    public void setUp() {
        orderApi = new OrderApi();
    }

    @Test
    @DisplayName("Get List Of Orders")
    @Description("Check that response contains list of orders")
    public void getListOfOrdersTest() {

        ValidatableResponse listResponse = orderApi.getListOfOrders(10);
        listResponse.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("orders", is(notNullValue()));
    }
}






