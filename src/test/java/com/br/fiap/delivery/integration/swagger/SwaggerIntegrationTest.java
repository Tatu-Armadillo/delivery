package com.br.fiap.delivery.integration.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.br.fiap.delivery.integration.config.TestConfigs;
import com.br.fiap.delivery.integration.containers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void shouldDisplaySwaggerUiPage() {
        var content = given()
                .basePath("/api/swagger-ui/index.html")
                .port(TestConfigs.SERVER_PORT)
                .when().get().then()
                .statusCode(200)
                .extract().body().asString();
        assertTrue(content.contains("Swagger UI"));
    }

}
