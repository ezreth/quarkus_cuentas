package org.iess;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class MovimientoResourceTest {

    @Test
    public void testRegistrarMovimientoSaldoInsuficiente() {
        String json = """
            {
                "tipoMovimiento": "DEBITO",
                "valor": 1000.0,
                "cuenta": { "cuentaId": 1 }
            }
            """;

        given()
                .contentType("application/json")
                .body(json)
                .when().post("/movimientos")
                .then()
                .statusCode(400)
                .body(containsString("Saldo no disponible"));
    }
}
