import data.Pet;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.core.Is.is;

public class PetTest {
    private PetEndpoint petEndPoint = new PetEndpoint();
    private Pet pet = new Pet(0, "string", "cat", "done");
    private static long petId;


    @Before
    public void beforeMethod() {

        ValidatableResponse response = petEndPoint.createPet(pet)
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
                .createPet(pet)
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

    @Test
    public void GetPetByStatus () {

        petEndPoint
                .getPetByStatus("done")
                .statusCode(is(200))
                .body("status[0]", is("done"));
    }

    @Test
    public void UpdatePet () {

        Pet petToUpdate = new Pet(petId, "pets", "cat", "done1");

        petEndPoint
                .updatePet(petToUpdate)
                .statusCode(200)
                .body ("category.name", is ("pets"));
    }

    @Test
    public void updatePetById () {
        petEndPoint
                .updatePetById(petId, "catBasilio", "turned")
                .statusCode(200);

        petEndPoint
                .getPet(petId)
                .statusCode(200)
                .body("name",is("catBasilio"))
                .body("status", is ("turned"));
    }
}
