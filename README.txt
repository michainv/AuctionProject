Internship onboarding project.

This small java project solves the Auction House problem .

In main
	The input.txt file is loaded,given a path in arguments or under src\input.txt
	Also a paramater exported is used ,to ask whether the output.txt should be exported.
	The default value is true but can be given as parameter in args
	For each line in that file the commands are executed and then the string to be exported is created.
	

It is consisted of 3 classes.
   -ActionRequest :
		 This Class creates the requests made by the user or the system.
		 The constructor takes  an input a string with the desired fields all separated by “|” 
			String format:(int timestamp|int user_id|String action|String item|float reserved_price).
		 Based on the type of action some fields might be missing.
		 After the input is checked for having all the fields required ,based on its type,
		 the input is checked for valid values on the given request.
			Request is valid when:
				If request is TimeUpdate ,only requirement is:
					 Tmestamp > 0
				In rest of the cases:
					Timestamp > 0
					userID  > 0
					item name is not missing
					Item price > 0
					Action name is either SELL or BID 
		Examples.
			1.Time update -> Request is a string containing only an integer (e.g “20”)
			2.Sell item ->Request is a string containing 6 fields (e.g 10|1|SELL|toaster_1|10.00|20)
			(int timestamp,int userID,string action,string item, float reserved_prive, separated,int close_time all seperated by “|”) 
			3.Bid on item     ->Request is a string containing 5 fields (e.g 15|2|BID|toaster_1|15.00)

	-Ticket :
		This class creates a ticket ,given an ActionRequest.
		This ticket contains auction information about a listed item.
		This information can be exported in a string formatted  like: 
			String format:int close_time|String item|int user_id|String status|float price_paid|int total_bid_count|float highest_bid|float lowest_bid\n.

    -Auctioneer :
    	This class handles the auction and ensures the requests are executed correctly.
    	Consists of two HashMap containing the listed and expired items accordingly.
    	User requests are made by a method (makeRequest) and the request given is checked for rules and logical assumptions made.
    	If is is valid and according action is taken based on its type.



	Assumptions made:
		-A file “input.txt” exists in the src folder ,or in a desired path given in arguments, with the given auction commands.
		-Timestamps of actions on the “input.txt” that are of earlier time from any of their previous,are ignored,
		for example:
					10|1|SELL|toaster_1|10.00|20
					12|8|BID|toaster_1|7.50
					11|3|BID|toaster_1|17.50
				The bid from user 3 will be ignored.
		-Timeupdate commands or bids ensure the expiration for all listed items
		-A bid is considered valid:
			-Is a positive number.
			-Arrives after the auction start time and before the closing time.
			-Is not made by the user listing the Item.
			-A bid is considered valid even if its price is smaller than the current running price,so the bidcounter is incremented and lowestbid may be changed.
		-At the end of the auction the winner will pay the price of the second highest bidder, if there
		is only a single valid bid they will pay the reserve price of the auction. If two bids are received
		for the same amount then the earliest bid wins the item.
