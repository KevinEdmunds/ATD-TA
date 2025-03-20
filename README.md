
# Limit order book and matching engine

## Efficiency Mechanisms

This Order Book Simulator focuses on order matching and order management:

*  Price-Level Organization: Orders are grouped by price points in `OrderPrice` objects, which allows for order priority to be determined
    
* Sorted Order Books: The buy and sell order books maintain sorted LinkedLists of `OrderPrice` objects
	*	Buy orders are sorted in descending price order (highest bids first)
	*	Sell orders are in ascending price order (lowest asks first)
    
*  Order matching:
	* Recursive Matching: After executing an order, `FindMatches()` is recursively called to process any potentially new order matches
	* Partial Order Execution: The system handles partial fills by modifying order quantities rather than creating new orders.
    
    

## Solution Approach


* Order Books: New orders are added to the appropriate order book (buy/sell) and sorted by price, with time priority maintained within each order price level.
	* The system supports three primary operations:    
	    *  `Add`: Places new orders in the appropriate price-ordered location
	    *   `Modify`: Updates order quantities while maintaining or updating time priority
	    *   `Delete`: Removes orders and cleans up empty price levels

* Order Matching: The system automatically checks for matches between the highest bid and lowest ask upon a successful order addition or modification
    *   When a crossover is detected (max bid price ≥ min ask price), orders are matched and executed
    *   Partial fills are handled by modifying order quantities
    *   Fully filled orders are removed from the book
    *  After any order match, the system checks again for new potential matches through the `FindMatches()` method.
    

## Data Structures


* Primary Order Books: Two `LinkedList<OrderPrice>` collections:
    
    *   `buyOrderBook`: Contains buy orders sorted by descending price
    *   `sellOrderBook`: Contains sell orders sorted by ascending price
* Price Level Organization: Each `OrderPrice` object contains:
    
    *   A specific price point
    *   A `LinkedList<Order>` of orders at that price level, sorted by time priority
* Order Representation: Each `Order` object stores:
    
    *   `orderId`: Unique identifier
    *   `orderPrice`: Limit price
    *   `orderQuantity`: Number of units
    *   `orderSide`: Buy or Sell indicator
    *   `orderDate`: Timestamp

This approach achieves:

*   O(1) access to best bid/ask prices	
	* Fast order matching by always comparing the first elements of each order book
*   O(n) order addition complexity
*   Time-prioritized ordering within each price level
