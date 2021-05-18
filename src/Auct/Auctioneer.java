package Auct;

import java.util.*;



public class Auctioneer {
    private HashMap<String , Ticket> activeItems;
    private HashMap<String , Ticket> expiredItems;
    private int AuctTime;



   public Auctioneer() {
        activeItems = new HashMap<>();
        expiredItems = new HashMap<>();
        AuctTime = 0;

    }


    /**
     * @param req ActionRequest provided by a user or system
     * @return True if the Request is executable or else,return False
     * This method,given a Request,tries to execute it.
     * Request could be of type:
     *  1.SELL/LIST Item
     *  2.BID on ListedItem
     *  3.UpdateTime
     */
    public boolean getUserRequest(AuctionRequest req) {
        int uid=req.getUserId();
        boolean flag=true;
        //if request has no missing fields
        if (req.getisValid() && AuctTime <= req.getTimestamp()) {
            //UpdateType or ActionType
            if (req.getisRequest()) {
                //TypeSell
                if (req.getAction().equals("SELL")) {
                    flag = listNewItem(req);
                }
                //TypeBid
                else {
                    flag = getOffer(req);
                }
            }
            //UpdateCurrentTime
            updateTime(req.getTimestamp());
            return flag;
        }
    return false;
    }

    /**
     * @param req ItemRequest to be added to the listed items
     * @return True if insertion was successful,false if item already exists.
     *
     */
    private boolean listNewItem(AuctionRequest req) {
        String item=req.getItemName();
        if(activeItems.containsKey(item) == false) {
            Ticket t = new Ticket(req);
            activeItems.put(t.getItemName(), t);
            return true;
        }
    return false;
   }


    /**
     * @param bid Make a BidRequest for an item name,given that
     *            it exists and item has not expired
     * @return True if Bid was Valid,or else return False
     * A bid is considered valid :
     *  1.Has a timestamp smaller than item's CloseTime
     *  2.Was not made by its own Seller.
     */
    private boolean getOffer(AuctionRequest bid) {
        Ticket listedItem = activeItems.get(bid.getItemName());
        if (listedItem != null) {
            if (bid.getTimestamp() < listedItem.getCloseTime() && (bid.getUserId()!=listedItem.getUserId())) {
                return updateValues(listedItem,bid);
            }
        }
        return false;
    }

    /**
     * @param item name to removed from active/listed items
     * @param it location of the given item.
     */
    private void unlistItem(String item,Iterator<Map.Entry<String, Ticket>> it) {
        expiredItems.put(item, activeItems.get(item));
        it.remove();
        if (expiredItems.get(item).isSold()) {
            expiredItems.get(item).setStatus("SOLD");
        }

    }


    /**
     * @param t Listed Item
     * @param bid Bid made for the Item
     * @return True if bid was made by user other than seller,or else return False.
     */
    private boolean updateValues(Ticket t,AuctionRequest bid){
        float bidPrice = bid.getPrice();
        int uid = bid.getUserId();
        //checks if bid was made by seller
        if(uid != t.getSellerID()) {
            //if this is the first bid assign value to lowest bid or else compare values
            if ((t.getTotalBidCount() == 0) || (t.getLowestBid() > bidPrice)) {
                updateLowestBid(t, bidPrice);
            }
            //if this is the first bid assign value to highest bid or else compare values
            if ((t.getTotalBidCount() == 0) || (t.getHighestBid() < bidPrice)) {
                updateHighestBid(t, bidPrice);
            }
            //increment total bids for item
            updateBidCount(t);

            //if bid is greater than previous update it
            if (bidPrice > t.getPrice()) {
                updatePrice(t, bidPrice);
                updateUserID(t, uid);
            }
            //if this is the first bid with value exactly the same as listed price
            if (t.getUserId() == -1 && bidPrice == t.getPrice()) {

                updatePrice(t, bidPrice);
                updateUserID(t, uid);
            }
            return true;
        }
        return false;
    }


    /**
     * @param t Item for which to increment number of valid bids.
     */
    private void updateBidCount(Ticket t){
        t.setTotalBidCount(t.getTotalBidCount()+1);
    }


    /**
     * @param t Item to update price
     * @param p Price to be set to the item.
     */
    private void updatePrice(Ticket t,float p){
        t.setPreviousPrice(t.getPrice());
        t.setPrice(p);
    }

    /**
     * @param t Item to update UserID.
     *
     * @param uid buyer UserID to be set to item.
     */
    private void updateUserID(Ticket t,int uid){
        t.setUserID(uid);
    }

    /**
     * @param t Item to update Lowest bid price
     * @param p Price to be set to the item.
     */
    private void updateLowestBid(Ticket t,float p){
        t.setLowestBid(p);
    }

    /**
     * @param t Item to update Highest bid price
     * @param p Price to be set to the item.
     */
    private void updateHighestBid(Ticket t,float p){
        t.setHighestBid(p);
    }


    /**
     * @param cTime TimeUpdate for all listed items.
     * This method updates the current time for all listed items
     * After update any expired items and removed from listed items.
     *
     */
    private void updateTime(int cTime) {
        AuctTime = cTime;
        Iterator<Map.Entry<String, Ticket>> it = activeItems.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String , Ticket> entry=it.next();
            String item=entry.getKey();
            activeItems.get(item).setCurTime(AuctTime);
            if (activeItems.get(item).isExpired()) {
                unlistItem(item,it);
            }

        }

    }


    /**
     * @return All expired items in a string format
     * close_time|item|user_id|status|price_paid|total_bid_count|highest_bid|lowest_bid.
     *
     */
    public String exportAuctions() {
        String f_auct="";
        for(String item:expiredItems.keySet()) {
            f_auct = f_auct + expiredItems.get(item).exportTicket();
        }
       return f_auct;
    }




}
