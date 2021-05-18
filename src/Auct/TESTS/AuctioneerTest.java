package Auct.TESTS;


import org.junit.jupiter.api.Test;
import Auct.Auctioneer;
import Auct.AuctionRequest;

import static org.junit.jupiter.api.Assertions.*;

class AuctioneerTest {

    @Test
    void AcceptNormalRequest(){
        //AcceptSellRequest
        String r="10|1|SELL|toaster_1|10.00|20";
        AuctionRequest req = new AuctionRequest(r);
        Auctioneer auct = new Auctioneer();
        auct.getUserRequest(req);
        boolean flag;

        //Accept bid on listed item
        r = "11|2|BID|toaster_1|22.00";
        req = new AuctionRequest(r);
        flag = auct.getUserRequest(req);
        assertEquals(true,flag);

        r = "20";
        req = new AuctionRequest(r);
        flag = auct.getUserRequest(req);
        assertEquals(true,flag);

        r=auct.exportAuctions();
        String exp ="20|toaster_1|2|SOLD|10.00|1|22.00|22.00\n";
        assertEquals(exp,r);
    }

    @Test
    void doubleListing(){
        //double list item with same name
        Auctioneer auct = new Auctioneer();

        String r = "10|1|SELL|toaster_1|10.00|20";
        AuctionRequest req = new AuctionRequest(r);
        boolean flag = auct.getUserRequest(req);
        assertEquals(true,flag);


        r = "10|2|SELL|toaster_1|12.00|23";
        req=new AuctionRequest(r);
        flag = auct.getUserRequest(req);
        assertEquals(false,flag);

        r = "10|1|SELL|toaster_1|11.00|21";
        req=new AuctionRequest(r);
        flag = auct.getUserRequest(req);
        assertEquals(false,flag);
    }

    @Test
    void bidOnOwnedItem(){
        Auctioneer auct = new Auctioneer();

        String r = "10|1|SELL|toaster_1|10.00|20";
        AuctionRequest req = new AuctionRequest(r);
        boolean flag = auct.getUserRequest(req);


        r = "12|1|BID|toaster_1|13.00";
        req = new AuctionRequest(r);
        flag = auct.getUserRequest(req);
        assertEquals(false,flag);


    }

    @Test
    void expiredBid(){
        Auctioneer auct = new Auctioneer();

        String r = "10|1|SELL|toaster_1|10.00|20";
        AuctionRequest req = new AuctionRequest(r);
        boolean flag = auct.getUserRequest(req);

        r = "22|2|BID|toaster_1|13.00";
        req = new AuctionRequest(r);
        flag = auct.getUserRequest(req);
        assertEquals(false,flag);

    }

    @Test
    void earlierTimeBid(){
        Auctioneer auct = new Auctioneer();
        boolean flag;
        String r = "10|1|SELL|toaster_1|10.00|20";
        AuctionRequest req = new AuctionRequest(r);

        r = "7|2|BID|toaster_1|13.00";
        req = new AuctionRequest(r);
        flag = auct.getUserRequest(req);
        assertEquals(false,flag);
    }

    @Test
    void unlistedItemBid(){
        Auctioneer auct = new Auctioneer();
        boolean flag;
        String r = "10|1|SELL|toaster_1|10.00|20";
        AuctionRequest req = new AuctionRequest(r);

        r = "11|2|BID|toaster_2|13.00";
        req = new AuctionRequest(r);
        flag = auct.getUserRequest(req);
        assertEquals(false,flag);

    }

}