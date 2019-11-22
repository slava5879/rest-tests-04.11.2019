import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.post;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.core.Is.is;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreatePetTest {
    static long petId;

    public RequestSpecification given() {
        return RestAssured
                .given()
                .baseUri("https://petstore.swagger.io/v2")
                .log().all()
                .contentType(ContentType.JSON);
    }

    @Test
    public void test1CreatePet(){

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

        ValidatableResponse response = given()
                .body(body)
                .post(PetEndpoint.CREATE_PET)
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .body("category.name", is("string"))
                .log().all();

        petId = response.extract().path("id");
    }

    @Test ()
    public void test2GetPetById() {

        given()
                .get(PetEndpoint.GET_PET, petId)
                .then()
                .statusCode(is(200))
                .body("name", is ("cat"))
                .log().all();
    }

    @Test
    public void test3DeletePetById () {

        given()
                .delete(PetEndpoint.DELETE_PET, petId)
                .then()
                .statusCode(200)
                .log().all();
    }
}
