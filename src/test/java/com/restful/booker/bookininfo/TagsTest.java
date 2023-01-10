package com.restful.booker.bookininfo;

import com.restful.booker.testbase.TestBase;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTags;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class TagsTest extends TestBase {
    @WithTag("restfulbookerfeature:NEGATIVE")
    @Title("Provide a 405 status code when incorrect HTTP method is used to access resource")
    @Test
    public void invalidMethod() {
        SerenityRest.rest()
                .given()
                .when()
                .post("/booking")
                .then()
                .statusCode(415)
                .log().all();
    }
    @WithTags({
            @WithTag("restfulbookerfeature:SMOKE"),
            @WithTag("restfulbookerfeature:POSITIVE")
    })

    @Title("This test will verify if a status code of 200 is returned for GET request")
    @Test
    public void verifyIfTheStatusCodeIs200() {
        SerenityRest.rest()
                .given()
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .log().all();
    }
    @WithTags({
            @WithTag("restfulbookerfeature:SMOKE"),
            @WithTag("restfulbookerfeature:NEGATIVE")
    })

    @Title("This test will provide an error code of 400 when user tries to access an invalid resource")
    @Test
    public void inCorrectResource() {
        SerenityRest.rest()
                .given()
                .when()
                .get("/booking123")
                .then()
                .statusCode(404)
                .log().all();
    }





}
