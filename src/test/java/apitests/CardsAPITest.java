
package apitests;

import org.junit.Assert;
import org.testng.annotations.Test;

import model.CardBaseResponse;
import model.DrawCard;
import utils.ApiUtils;

/**
 * Author : Shanthan
 */
public class CardsAPITest extends BaseTest {
	
	/********* GET NEW DECK API TEST CASES **************/

	/*****
	 * 1. Status code should be 200
	 * 2. Response success value should be true
	 * 3. Response shuffle value should be false since we are not doing shuffling here
	 * 4. Deck id should not be null
	 * *****/
    @Test
    public void statusCodeTest() {
    	 //Verify the http response status returned. Check Status Code is 200?
    	res = ApiUtils.getResponsebyPath("/new");
        testUtils.checkStatusIs200(res);
    }

    @Test
    public void getsuccessShouldBeTrueTest() {
    	CardBaseResponse cardRes = ApiUtils.getResponsebyPath("/new").then().log().all()
                .extract().body().jsonPath().getObject("", CardBaseResponse.class);
    	Assert.assertEquals(cardRes.isSuccess(), true);
    	Assert.assertEquals(cardRes.isShuffled(), false);
    	Assert.assertNotNull(cardRes.getDeckId());
    }
    
    /********* GET NEW DECK API TEST CASES **************/
    
    
    /********* GET/POST NEW DECK API TEST CASES WITH JOKER ADDED *****/
    /*****
	 * 1. Status code should be 200
	 * 2. Response success value should be true
	 * 3. Response shuffle value should be false since we are not doing shuffling here
	 * 4. Deck id should not be null
	 * 5. Remaining cards should be 54
	 * *****/
    
    @Test
    public void postStatusCodeTest() {
        //Verify the http response status returned. Check Status Code is 200?
        res = ApiUtils.getResponsebyPath(ApiUtils.createSearchQueryPath("/new", "jokers_enabled", "true"));
        testUtils.checkStatusIs200(res);
    }
    
   @Test
    public void postsuccessShouldBeTrueTest() {
	   CardBaseResponse cardRes = ApiUtils.getResponsebyPath(ApiUtils.createSearchQueryPath("/new", "jokers_enabled", "true")).then().log().all()
               .extract().body().jsonPath().getObject("", CardBaseResponse.class);
	   Assert.assertEquals(cardRes.isSuccess(), true);
   	   Assert.assertEquals(cardRes.isShuffled(), false);
   	   Assert.assertNotNull(cardRes.getDeckId());
   	   Assert.assertEquals(cardRes.getRemaining().longValue(), 54l	);
    }
    
    /********* GET/POST NEW DECK API TEST CASES WITH JOKER ADDED **************/
   
    
   /********* GET DRAW CARDS API TEST CASES WITH JOKER ADDED **************/
   
   /*****
	 * 1. Status code should be 200
	 * 2. Response success value should be true
	 * 3. Deck id should not be null
	 * 4. drawn card list size should be equal to what we are passing..
	 * *****/
    
    @Test
    public void drawStatusCodeTest() {
        //Verify the http response status returned. Check Status Code is 200?
        res = ApiUtils.getResponsebyPath(ApiUtils.createSearchQueryPath("/new/draw", "count", "1"));
        testUtils.checkStatusIs200(res);
    }
    
    // Draw 1 card
    @Test
    public void drawResponseSuccessShouldBeTrue() {
    	DrawCard drawCardRes = ApiUtils.getResponsebyPath(ApiUtils.createSearchQueryPath("/new/draw", "count", "1")).then().log().all()
                .extract().body().jsonPath().getObject("", DrawCard.class);
        Assert.assertEquals("Success is true", drawCardRes.isSuccess(), true);
        Assert.assertNotNull(drawCardRes.getDeckId());
    }
    
    // Draw 3 cards...
    @Test
    public void reducedCardSizeCountTest() {
    	String countToBeReduced = "3"; // 3 cards needs to be drawn so drawn cards array size should be 3 and remaining should be 49.
    	DrawCard drawCardRes = ApiUtils.getResponsebyPath(ApiUtils.createSearchQueryPath("/new/draw", "count", countToBeReduced)).then().log().all()
                .extract().body().jsonPath().getObject("", DrawCard.class);
    	Assert.assertEquals("Success is true", drawCardRes.isSuccess(), true);
    	Assert.assertEquals("Response size should be 3", drawCardRes.getCards().size(), 3);
    	Assert.assertEquals(drawCardRes.getRemaining().longValue(), 49l	);
    }
    
    /********* GET DRAW CARDS API TEST CASES WITH JOKER ADDED **************/

}
