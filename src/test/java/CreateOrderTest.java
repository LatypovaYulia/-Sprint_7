import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.OrderData;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import api.OrderApi;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private static final String COLOR_BLACK = "BLACK";
    private static final String COLOR_GREY = "GREY";

    private final List<String> color;
    protected OrderApi orderApi;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Test with color: {0}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {List.of(COLOR_BLACK)},
                {List.of(COLOR_GREY)},
                {List.of(COLOR_BLACK, COLOR_GREY)},
                {List.of()}
        };
    }

        @Before
        public void setUp() {
            orderApi = new OrderApi();
        }

        @Test
        @DisplayName("Check order can be created with different color")
        @Description("Check order can be created with BLACK color, or GREY color, or BLACK and GREY colors, or no color")
        public void orderCanBeCreatedTest() {
            OrderData orderData = new OrderData(
                    "Yulia",
                    "Latypova",
                    "Lenina, 55, 55",
                    "Сокол",
                    "+7 800 999 33 33",
                    4,
                    "2024-12-25",
                    "-",
                    color
            );

            ValidatableResponse response = orderApi.createOrder(orderData);
            response.log().all()
                    .assertThat()
                    .statusCode(HttpStatus.SC_CREATED)
                    .body("track", notNullValue());
        }
    }




