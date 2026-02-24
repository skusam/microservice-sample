package de.infodesign.microservice.customer;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CustomerResourceTest {

    @Test
    void createAndGet() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Max Mustermann");
        requestBody.put("email", "max@example.com");

        String id = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/customers")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .path("id").toString();

        Customer c = given()
                .accept(ContentType.JSON)
                .when()
                .get("/customers/" + id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(Customer.class);

        assertEquals(requestBody.get("name"), c.getName());
        assertEquals(requestBody.get("email"), c.getEmail());
    }

    @Test
    void getUnknownCustomer() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/customers/0")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}