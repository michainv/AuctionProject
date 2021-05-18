package Auct.TESTS;
import org.junit.jupiter.api.Test;

import Auct.AuctionRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuctionRequestTest {

    @Test
    void emptyInput() {
        // testing blank input
        String r="";
        AuctionRequest req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
    }



    @Test
    void normalInput() {
        String r="10|1|SELL|toaster_1|10.00|20";
        AuctionRequest req=new AuctionRequest(r);
        assertEquals(true,req.getisValid());

        r="10|1|BID|toaster_1|10.00";
        req=new AuctionRequest(r);
        assertEquals(true,req.getisValid());

        r="10|1|BID|toaster_1|15";
        req=new AuctionRequest(r);
        assertEquals(true,req.getisValid());


        r="10";
        req=new AuctionRequest(r);
        assertEquals(true,req.getisValid());

    }


    @Test
    void TimeUpdate() {
        //normal time update
        String r="10";
        AuctionRequest req=new AuctionRequest(r);
        assertEquals(true,req.getisValid());
        //negative time update
        r="-10";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //charachers times update
        r="aaa";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //blank time update
        r="";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //int+char time update
        r="21a";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //float time update
        r="3.00";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //0 time update
        r="0";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());

    }


    @Test
    void ActionTime(){
        //action time update
        String r="2|1|SELL|toaster_1|10.00|20";
        AuctionRequest req=new AuctionRequest(r);
        assertEquals(true,req.getisValid());

        r="-1|1|SELL|toaster_1|10.00|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());

        r="X|1|SELL|toaster_1|10.00|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());

        r="|1|SELL|toaster_1|10.00|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());

        r="3.00|1|SELL|toaster_1|10.00|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());

        r="0|1|SELL|toaster_1|10.00|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());

        r="daa20|1|SELL|toaster_1|10.00|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
    }


    @Test
    void UserId(){
        //normal id
        String r="1|1|SELL|toaster_1|10.00|20";
        AuctionRequest req=new AuctionRequest(r);
        assertEquals(true,req.getisValid());
        //id=0
        r="1|0|SELL|toaster_1|10.00|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //negative id
        r="1|-1|SELL|toaster_1|10.00|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //float id
        r="1|1.00|SELL|toaster_1|10.00|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //string id
        r="1|a9310|SELL|toaster_1|10.00|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //blank id
        r="1||SELL|toaster_1|10.00|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
    }


    @Test
    void Bid(){
        //normal bid
        String r="1|1|BID|toaster_1|10.00";
        AuctionRequest req=new AuctionRequest(r);
        assertEquals(true,req.getisValid());
        //character bid
        r="1|1|BID|toaster_1|aa";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //negative bid
        r="1|1|BID|toaster_1|-10";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //0 bid
        r="1|1|BID|toaster_1|0";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //missing bid
        r="1|1|BID|toaster_1|";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //blank bid
        r="1|1|BID|toaster_1||";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
    }

    @Test
    void Sell(){
        //normal sell
        String r="1|1|SELL|toaster_1|10.00|20";
        AuctionRequest req=new AuctionRequest(r);
        assertEquals(true,req.getisValid());
        //list item item  earlier close time
        r="21|1|SELL|toaster_1|10.00|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //negative item close_time
        r="21|1|SELL|toaster_1|10.00|-20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //float item close_time
        r="21|1|SELL|toaster_1|10.00|20.00";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //0 item close time
        r="21|1|SELL|toaster_1|10.00|0";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //missing item value
        r="1|1|SELL|toaster_1||20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //negative item value
        r="1|1|SELL|toaster_1|-1|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //0 item value
        r="1|1|SELL|toaster_1|0|20";
        req=new AuctionRequest(r);
        assertEquals(true,req.getisValid());
        //char item value
        r="1|1|SELL|toaster_1|aaa|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());


        r="1|1|SELL|toaster_1|321a1213|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());

        r="1|1|SELL|toaster_1|aaa|a";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());

        //missing item close time
        r="1|1|SELL|toaster_1|aaa|";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());

        //negative close time
        r="1|1|SELL|toaster_1|20|-12";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());

        r="1|1|SELL||20|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //int item name
        r="1|1|SELL|1|20|20";
        req=new AuctionRequest(r);
        assertEquals(true,req.getisValid());

        //missing item name
        r="1|1|SELL||20|20";
        req=new AuctionRequest(r);
        assertEquals(false,req.getisValid());
        //int item name
        r="1|1|SELL|-1|20|20";
        req=new AuctionRequest(r);
        assertEquals(true,req.getisValid());
    }

    @Test
    void correctRead(){
        String r="1|1|SELL|toaster_1|10.50|20";
        AuctionRequest req=new AuctionRequest(r);

        assertEquals(1,req.getTimestamp());
        assertEquals(true,req.getisRequest());
        assertEquals(1,req.getUserId());
        assertEquals("SELL",req.getAction());
        assertEquals("toaster_1",req.getItemName());
        assertEquals(10.5,req.getPrice());
        assertEquals(20,req.getCloseTime());

        r="5|3|BID|toaster_1|10.53";
        req=new AuctionRequest(r);
        assertEquals("BID",req.getAction());
        assertEquals(req.getPrice(),10.53,0.001);
    }


}