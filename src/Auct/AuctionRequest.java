package Auct;

public class AuctionRequest {

    private int timestamp;
    private int user_id;
    private String action;
    private String item;
    private float price;
    private int close_time;
    private boolean isRequest;
    private boolean isValid;


    /**
     * @param req A given auction string request by user or the system
     *            (string format is $timestamp|$user_id|$action|$item|$reserved_price|$close_time)
     *            The constructor first initializes all the values for the request
     *            and then calls the getRequest method to change the according fields depended on the input.
     */
    public AuctionRequest(String req) {
        timestamp = -1;
        user_id = -1;
        action = "";
        item = "";
        price = -1;
        close_time = -1;
        isRequest = true;
        isValid = false;
        makeRequest(req);
    }


    /**
     * @param req string provided by a user or system
     *            This string is checked for being valid ,based on its actiontype,and the
     *            corresponding value is returned.
     * @return True if Request is valid,or else return False.
     */
        public boolean makeRequest(String req) {
            String[] tokens = req.split("\\|");

            boolean corInput = true;

            if (tokens.length == 1) {
                try {
                    timestamp = Integer.parseInt(tokens[0]);
                    isRequest = false;
                } catch (NumberFormatException e) {
                    return false;
                }
            } else if (tokens.length >= 5 && tokens.length <= 6) {
                try {
                    timestamp = Integer.parseInt(tokens[0]);
                    user_id = Integer.parseInt(tokens[1]);
                    action = tokens[2];
                    item = tokens[3];
                    price = Float.parseFloat(tokens[4]);
                    if (tokens.length == 6) {
                        close_time = Integer.parseInt(tokens[5]);
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            isValid = checkValidity();
            return isValid;
        }


    /**
     * @return Whether the given request is valid
     * Validity is defined as :
     *      1.Request has one int field -> Time Update
     *      2.Request is SELL action,has valid values for all 6 fields
     *          ($timestamp|$user_id|$action|$item|$reserved_price|$close_time)
     *      3.Request is BID action,has valid values for all 5 fields
     *          ($timestamp|$user_id|$action|$item|$reserved_price)
     *
     */
    private boolean checkValidity() {
        //check for TimeUpdate case
        if(!isRequest && timestamp > 0){
            if(timestamp > 0){
                return true;
            }

        }
        //check for negative time
        if (timestamp <= 0){
            return false;
        }
        //check for negative userID
        if(user_id<=0){
            return false;
        }
        //check for missing item name
        if (item.length()==0){
            return false;
        }
        //check for negative item price
        if (price<0){
            return false;
        }
        //check for invalid action type
        if (!action.equals("SELL") && !action.equals("BID")){
            return false;
        }

        //check for bid value is 0
        if (action.equals("BID") && (price == 0)) {
            return false;
        }

        //check for valid close item time
        if (action.equals("SELL")){
            if(close_time <= 0 ||  (close_time < timestamp)){
                return false;
            }
        }

        return true;
    }


    /**
     * @return Request Timestamp
     */
    public int getTimestamp(){ return timestamp; }

    /**
     * @return Request userID
     */
    public int getUserId(){ return user_id; }

    /**
     * @return Request ActionType
     */
    public String getAction(){ return action; }

    /**
     * @return Request ItemName
     */
    public String getItemName(){ return item; }

    /**
     * @return Request ItemPrice/Bid
     */
    public float getPrice(){ return price; }

    /**
     * @return Request ItemClosingTime
     */
    public int getCloseTime(){ return close_time; }

    /**
     * @return true if Request is UpdateTime type
     *         false if Request is Action type
     */
    public boolean getisRequest(){ return isRequest; }

    /**
     * @return true if Request fields are valid,or else returns false
     */
    public boolean getisValid(){return isValid; }


}
