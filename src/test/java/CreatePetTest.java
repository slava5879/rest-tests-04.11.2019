import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.post;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.core.Is.is;

public class CreatePetTest {

    @Test
    public void cretePet(){

        String body = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"cat\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        RestAssured
                .given()
                .log().all()
                //.header("ContentType","application/json")
                .contentType(ContentType.JSON)
                .body(body)
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .body("category.name", is("string"))
                .log().all();
    }
}
