package za.co.entelect.bootcamp.java;


//TODO: Find error that is happening when rolling over from one buy order to the next one.
public class MatchingEngine {
    public static void FindMatches()
    {
        System.out.println("Checking for matches");
        float maxBid;
        float minAsk;
        Order ask;
        Order bid;

        if(!OrderBook.buyOrderBook.isEmpty()&&!OrderBook.sellOrderBook.isEmpty())
        {
            System.out.println("Both lists are populated");
            ask = OrderBook.sellOrderBook.getFirst().orderPriceList.getFirst();
            bid = OrderBook.buyOrderBook.getFirst().orderPriceList.getFirst();
            maxBid= bid.orderPrice;
            minAsk = ask.orderPrice;

            VerifyOrderCrossover(maxBid, minAsk, bid, ask);
        }
    }

    private static void VerifyOrderCrossover(float maxBid, float minAsk, Order bid, Order ask) {
        if(maxBid >= minAsk){
            ExecuteOrder(bid, ask);
            return;
        }
    }

    public static void ExecuteOrder(Order bid, Order ask)
    {
        System.out.println("Running the numbers shandies");
        if(bid.orderQuantity>ask.orderQuantity) {
            OrderBook.Modify(bid.orderId, bid.orderQuantity-ask.orderQuantity, "Buy", true);
            OrderBook.Delete(ask.orderId, "Sell");
        } else if (bid.orderQuantity==ask.orderQuantity) {
            OrderBook.Delete(ask.orderId, "Sell");
            OrderBook.Delete(bid.orderId, "Buy");
        }else{
            OrderBook.Modify(ask.orderId, ask.orderQuantity-bid.orderQuantity, "Sell",true);
            OrderBook.Delete(bid.orderId, "Buy");
        }
        FindMatches();
    }
}
