package Auct;

public class Ticket {
    private int close_time;
    private int cur_time;
    private String item;
    private int buyer_id;
    private int seller_id;
    private String status;
    private float price;
    private float previous_price;
    private int total_bid_count;
    private float highest_bid;
    private float lowest_bid;


   public Ticket(AuctionRequest req){
        close_time = req.getCloseTime();
        cur_time = req.getTimestamp();
        item = req.getItemName();
        seller_id=req.getUserId();
        buyer_id = -1;
        price = req.getPrice();
        previous_price = 0;
        status = "UNSOLD";
        total_bid_count = 0;
        lowest_bid = -1;
        highest_bid = -1;
    }

    public int getTimestamp(){
        return close_time;
    }

    /**
     * @return userID of the user selling the item.
     */
    public int getSellerID(){ return seller_id; }

    /**
     * @return Total valid bids made for this item.
     */
    public int getTotalBidCount(){ return total_bid_count; }

    /**
     * @return userID of the user who has placed the biggest bid.
     */
    public int getUserId(){
        return buyer_id;
    }

    /**
     * @return Current timestamp.
     */
    public int getCurTime(){ return cur_time; }

    /**
     * @return Status of item ,"SOLD" OR "UNSOLD".
     */
    public String getAction(){
        return status;
    }

    /**
     * @return Item's name
     */
    public String getItemName(){
        return item;
    }

    /**
     * @return Item's current price.
     */
    public float getPrice(){
        return price;
    }

    /**
     * @return Item's previous higgest bid.
     */
    public float getPreviousPrice(){ return previous_price;}

    /**
     * @return Price of the lowest valid bid made for the item.
     */
    public float getLowestBid(){ return lowest_bid; }

    /**
     * @return Price of the highest valid bid made for the item.
     */
    public float getHighestBid(){ return highest_bid; }

    /**
     * @return Timestamp of when the item will be come unlisted.
     */
    public int getCloseTime(){
        return close_time;
    }


    /**
     * @param uid sets the userID for highest bidder.
     */
    public void setUserID(int uid){ buyer_id = uid; }

    /**
     * @param tbcount sets the number of number of valid bids.
     */
    public void setTotalBidCount(int tbcount){ total_bid_count = tbcount; }

    /**
     * @param st sets the items status ,to "SOLD" OR "UNSOLD".
     */
    public void setStatus(String st){ status = st; }

    /**
     * @param lb change the value of lowest bid.
     */
    public void setLowestBid(float lb){ lowest_bid = lb ;}

    /**
     * @param hb change the value of highest bid.
     */
    public void setHighestBid(float hb){ highest_bid = hb ;}

    /**
     * @param p sets the Price of the item.
     */
    public void setPrice(float p){ price = p; }

    /**
     * @param cTime set Current timestamp to a new one.
     */
    public void setCurTime(int cTime){ cur_time = cTime; }

    /**
     * @param p Changes the value of the previous highest bid.
     */
    public void setPreviousPrice(float p){ previous_price = p; }

    /**
     * @return True if listed item must expire,or else ,return False.
     */
    public boolean isExpired(){ return (cur_time >= close_time); }



    public boolean isSold(){
       return (seller_id!=buyer_id && buyer_id!=-1);
    }

    /**
     * @return A ticket in a string format close_time|item|user_id|status|price_paid|total_bid_count|highest_bid|lowest_bid.
     *
     */
    public String exportTicket(){
        String tk;
        String sep="|";
        String ct=String.valueOf(cur_time);
        String uid="";
        String p_paid=String.format("%.2f",previous_price).replace(",",".");
        String tbc=String.valueOf(total_bid_count);
        String hbid;
        if(highest_bid!=-1){
            hbid=String.format("%.2f",highest_bid).replace(",",".");
        }
        else{
            hbid="0.00";
        }
        String lbid;
        if(lowest_bid!=-1){
            lbid=String.format("%.2f",lowest_bid).replace(",",".");
        }
        else{
            lbid="0.00";
        }
        if(buyer_id!=seller_id && buyer_id!=-1) {
            uid = String.valueOf(buyer_id);
        }
        tk=ct+sep+item+sep+uid+sep+status+sep+p_paid+sep+tbc+sep+hbid+sep+lbid+"\n";

        return tk;

    }
}
