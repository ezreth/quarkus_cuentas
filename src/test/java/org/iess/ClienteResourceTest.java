package org.iess;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ClienteResourceTest {

    @Test
    public void testListarClientesEndpoint() {
        given()
                .when().get("/clientes")
                .then()
                .statusCode(200);
    }
}
