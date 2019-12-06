package steps;

import data.Pet;
import data.Status;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.io.File;

public class PetEndpoint {
    public final static String CREATE_PET = "/pet";
    public final static String GET_PET = "/pet/{petId}";
    public final static String DELETE_PET = "/pet/{petId}";
    public final static String GET_PET_BY_STATUS = "/pet/findByStatus";
    public final static String UPDATE_PET = "/pet";
    public final static String UPDATE_PET_BY_ID = "/pet/{petId}";
    public final static String UPLOAD_PET_IMAGE ="/pet/{petId}/uploadImage";

    static {
        // logging instead of .log().all() in response
        SerenityRest.filters(new ResponseLoggingFilter(LogDetail.ALL));

        // logging instead of .log().all() in request
        SerenityRest.filters(new RequestLoggingFilter(LogDetail.ALL));
    }

    public RequestSpecification given() {
        return SerenityRest
                .given()
                .baseUri("https://petstore.swagger.io/v2")
                //.log().all()
                .contentType(ContentType.JSON);
    }

    @Step
    public ValidatableResponse createPet(Pet pet) {
        return given()
                .body(pet)
                .post(CREATE_PET)
                .then();
    }

    @Step
    public ValidatableResponse getPet(long petId) {
        return given()
                .get(GET_PET, petId)
                .then();
    }

    @Step
    public ValidatableResponse deletePet(long petId) {
        return given()
                .delete(DELETE_PET, petId)
                .then();
    }

    @Step
    public ValidatableResponse getPetByStatus(Status status) {
        return given()
                .queryParam("status", status)
                .get(GET_PET_BY_STATUS)
                .then();
    }

    @Step
    public ValidatableResponse updatePet(Pet pet) {
        return given()
                .body(pet)
                .put(UPDATE_PET)
                .then();
    }

    @Step
    public ValidatableResponse updatePetById (long petId, String petName, String petStatus) {
        return given()
                .contentType(ContentType.URLENC)
                .formParam("name",petName)
                .formParam("status",petStatus)
                .post(UPDATE_PET_BY_ID, petId)
                .then();
    }

    public ValidatableResponse uploadPetImage (long petId, String resourceFilePath) {
        File file = new File(getClass().getResource(resourceFilePath).getFile());

        return given()
                .contentType("multipart/form-data")
                .multiPart(file)
                //.formParam("file", fileName)
                //.formParam("type", "image/jpg")
                .post(UPLOAD_PET_IMAGE, petId)
                .then();
    }

}
