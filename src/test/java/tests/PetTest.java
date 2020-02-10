package tests;

import data.Category;
import data.Pet;
import data.Status;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.PetEndpoint;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@RunWith(SerenityRunner.class)
public class PetTest {
//    private steps.PetEndpoint petEndPoint = new steps.PetEndpoint();

    @Steps
    private PetEndpoint petEndPoint;
    //private Pet pet = new Pet(0, "string", "cat", Status.pending);
    private Pet pet = Pet.builder()
            .id(0)
            .category(Category.builder().name("string").build())
            .name("Cat")
            .status(Status.pending)
            .build();
    private static long petId;


    @Before
    public void beforeMethod() {

        ValidatableResponse response = petEndPoint.createPet(pet)
                .statusCode(200)
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
                .statusCode(200)
                .body("category.name", is("string"));
    }

    @Test ()
    public void GetPetById() {

        petEndPoint
                .getPet(petId)
                .statusCode(200)
                .body("name", is ("Cat"));
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
                .getPetByStatus(Status.pending)
                .statusCode(is(200))
                .body("status[]", everyItem(is(Status.pending.toString())));
    }

    /*@Test
    public void UpdatePet () {

        Pet petToUpdate = new Pet(petId, "pets", "catBasilio", Status.sold);

        petEndPoint
                .updatePet(petToUpdate)
                .statusCode(200)
                .body ("category.name", is ("pets"))
                .body("name", is("catBasilio"))
                .body("status", is(Status.sold.toString()));
    }*/

    @Test
    public void UpdatePetById () {
        petEndPoint
                .updatePetById(petId, "catBasilio", "turned")
                .statusCode(200);

        petEndPoint
                .getPet(petId)
                .statusCode(200)
                .body("name",is("catBasilio"))
                .body("status", is ("turned"));
    }

    @Test
    public void UploadPetImage () {
        petEndPoint
                .uploadPetImage(petId, "basilio.jpg") //src/test/resources/
                .statusCode(200)
                .body("message", containsString("basilio.jpg"));
    }
}