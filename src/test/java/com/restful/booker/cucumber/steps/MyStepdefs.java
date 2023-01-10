package com.restful.booker.cucumber.steps;


import com.restful.booker.bookininfo.BookingSteps;
import com.restful.booker.model.BookingPojo;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;

import java.util.HashMap;

public class MyStepdefs {
    static int id;
    static String token;
    static ValidatableResponse response;
    static int totalprice;

    @Steps
   BookingSteps bookingSteps;


    @Given("^User is on restful booker website$")
    public void userIsOnRestfulBookerWebsite() {
    }

    @When("^I send a GET request to booking endpoint$")
    public void userSendAGETRequestToBookingEndpoint() {
        response = bookingSteps.getAllBookingIDs();
    }

    @Then("^I must get back a valid response code 200$")
    public void userMustGetBackAValidResposeCode() {
        response.statusCode(200);
    }

    @When("^I send a POST request to create a new booking with firstName\"([^\"]*)\", lastName\"([^\"]*)\", totalprice \"([^\"]*)\", depositpaid \"([^\"]*)\", checkin \"([^\"]*)\", checkout\"([^\"]*)\" additionalneeds \"([^\"]*)\"$")
    public void iSendAPOSTRequestToCreateANewBookingWihtFirstNameLastNameTotalpriceDepositpaidCheckinCheckoutAdditionalneeds(String firstName, String lastName, int totalprice, boolean depositpaid, String checkin, String checkout, String additionalneeds) {
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        response = bookingSteps.createBooking(firstName, lastName, totalprice, depositpaid, bookingdates, additionalneeds);
    }

    @Then("^I must get back a valid response code 201$")
    public void iMustGetBackAValidResponseCode() {
        response.statusCode(200);
        id = response.extract().path("bookingid");
    }

    @And("^I verify that new booking is created by <\"([^\"]*)\">$")
    public void iVerfiyThatNewBookingIsCreatedBy(String _id) {
        bookingSteps.getSingleBookingIDs(id).statusCode(200);
    }

    @When("^I send Put request with  firstName\"([^\"]*)\", lastName\"([^\"]*)\", totalprice \"([^\"]*)\", depositpaid \"([^\"]*)\", checkin \"([^\"]*)\", checkout\"([^\"]*)\" additionalneeds \"([^\"]*)\"$")
    public void iSendPutRequestWithFirstNameLastNameTotalpriceDepositpaidCheckinCheckoutAdditionalneeds(String firstName, String lastName, String _totalprice, boolean depositpaid, String checkin, String checkout, String additionalneeds) {
        response = bookingSteps.getTokken();
        token = response.extract().path("token");
        firstName = "Nikko";
        lastName = "Khan";
        totalprice = 100;
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        response = bookingSteps.updateBookingWithID(id, token, firstName, lastName, totalprice, depositpaid, bookingdates, additionalneeds);
    }


    @When("^I send delete requested id$")
    public void iSendDeleteRequestedId() {
        response = bookingSteps.deleteABookingID(id, token);
    }

    @Then("^I should see the response code 201$")
    public void iShouldSeeTheResponseCode() {
        response.statusCode(201);
        bookingSteps.getSingleBookingIDs(id).statusCode(404);
    }
}
