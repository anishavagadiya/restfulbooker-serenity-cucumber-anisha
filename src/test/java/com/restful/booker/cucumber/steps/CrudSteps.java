package com.restful.booker.cucumber.steps;

import com.restful.booker.bookininfo.BookingSteps;
import com.restful.booker.model.BookingPojo;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class CrudSteps {
    static ValidatableResponse response;
    static int id;
    static int totalprice;

    static String token;

    @Steps
    BookingSteps bookingSteps;


    @When("^I send POST request to create a new booking with firstName\"([^\"]*)\", lastName\"([^\"]*)\", totalprice \"([^\"]*)\", depositpaid \"([^\"]*)\", checkin \"([^\"]*)\", checkout\"([^\"]*)\" additionalneeds \"([^\"]*)\"$")
    public void iSendPOSTRequestToCreateANewBookingWithFirstNameLastNameTotalpriceDepositpaidCheckinCheckoutAdditionalneeds(String firstName, String lastName, int totalprice, boolean depositpaid, String checkin, String checkout, String additionalneeds) {
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        totalprice = 400;
        response = bookingSteps.createBooking(firstName, lastName, totalprice, depositpaid, bookingdates, additionalneeds);
        id = response.extract().path("bookingid");

    }

    @And("^I send a Put request with  firstName\"([^\"]*)\", lastName\"([^\"]*)\", totalprice \"([^\"]*)\", depositpaid \"([^\"]*)\", checkin \"([^\"]*)\", checkout\"([^\"]*)\" additionalneeds \"([^\"]*)\"$")
    public void iSendAPutRequestWithFirstNameLastNameTotalpriceDepositpaidCheckinCheckoutAdditionalneeds(String firstName, String lastName, String _totalprice, boolean depositpaid, String checkin, String checkout, String additionalneeds) {
        response = bookingSteps.getTokken();
        token = response.extract().path("token");
        totalprice = 200;
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        response = bookingSteps.updateBookingWithID(id, token, firstName, lastName, totalprice, depositpaid, bookingdates, additionalneeds);
    }

    @And("^The totalprice \"([^\"]*)\" is successfully updated$")
    public void theTotalpriceIsSuccessfullyUpdated(String _id) {
        response = bookingSteps.getSingleBookingIDs(id);
        HashMap<String, ?> booking = response.extract().path("");
        Assert.assertThat(booking, hasValue(totalprice));
    }


    @Then("^The booking is successfully deleted from the record$")
    public void theBookingIsSuccessfullyDeletedFromTheRecord() {
        bookingSteps.getSingleBookingIDs(id).statusCode(404);
    }

    @Then("^I verfiy that new booking is created by id$")
    public void iVerfiyThatNewBookingIsCreatedById() {
        bookingSteps.getSingleBookingIDs(id);
    }

    @And("^I send delete booking by id$")
    public void iSendDeleteBookingById() {
        bookingSteps.deleteABookingID(id, token).statusCode(201);
    }

}
