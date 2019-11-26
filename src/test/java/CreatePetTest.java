import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.core.Is.is;

public class CreatePetTest {
    private PetEndpoint petEndPoint = new PetEndpoint();
    private static long petId;

    private String body = "{\n" +
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

    @Before
    public void beforeMethod() {

        ValidatableResponse response = petEndPoint.createPet(body)
                .statusCode(anyOf(is(200), is(201)))
                .body("category.name", is("string"))
                //.log().all()
        ;

        petId = response.extract().path("id");
        System.out.println(petId);
    }

    @Test
    public void CreatePetTest(){

        petEndPoint
                .createPet(body)
                .statusCode(anyOf(is(200), is(201)))
                .body("category.name", is("string"));
    }

    @Test ()
    public void GetPetById() {

        petEndPoint
                .getPet(petId)
                .statusCode(is(200))
                .body("name", is ("cat"));
    }

    @Test
    public void DeletePetById () {

        petEndPoint
                .deletePet(petId)
                .statusCode(200);

        petEndPoint
                .getPet(petId)
                .statusCode(is(404))
                .body("message", is ("Pet not found"));
    }
}
