import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

public class OrderBook {
    private static LinkedList<OrderPrice> buyOrderBook = new LinkedList<OrderPrice>();
    private LinkedList<OrderPrice> sellOrderBook = new LinkedList<OrderPrice>();
//    private static Iterator buyIterator =  buyOrderBook.iterator();
//    private Iterator sellIterator =  sellOrderBook.iterator();

    public static void main(String[] args) throws IOException {
        // Enter data using BufferReader
        int i=0;
        while(true)
        {
            BufferedReader r = new BufferedReader(
                    new InputStreamReader(System.in));

            System.out.println("< - - - - - - - - - - - - - >");
            System.out.print("Enter Price: ");
            float price = Float.parseFloat(r.readLine());

            System.out.print("Enter Qty: ");
            int quantity = Integer.parseInt(r.readLine());

            System.out.print("Enter Side: ");
            String side = r.readLine();

            Order newOrder = new Order(i, price, quantity,side, LocalDateTime.now());
            Add(newOrder);
            i++;
            PrintOrderList();
        }
    }

    public void Delete(int id) {

    }

    public static void Add(Order newOrder){
        float price = newOrder.orderPrice;
        System.out.println("Adding item");
//        if(buyOrderBook.isEmpty())
//        {
//            System.out.println("Initializing Order Book");
//            OrderPrice newOrderPrice= new OrderPrice();
//            newOrderPrice.AddOrder(newOrder);
//        }

        if(Objects.equals(newOrder.orderSide, "Buy")) {
            System.out.println("Buy order");
            if(buyOrderBook.isEmpty())
            {
                buyOrderBook.add(CreateNewOrderPrice(newOrder));
                System.out.println("This is a new order");
                return;
            }
            if(price>buyOrderBook.getLast().orderPrice)
            {
                buyOrderBook.addLast(CreateNewOrderPrice(newOrder));
                System.out.println("The new biggest entry");
                return;
            }else if(price<buyOrderBook.getFirst().orderPrice)
            {
                buyOrderBook.addFirst(CreateNewOrderPrice(newOrder));
                System.out.println("The new smallest entry");
                return;
            }else{
                System.out.println("Running in the for loop");
                for(int i=0; i<buyOrderBook.size();i++)
                {

                    float currentOrderPrice = buyOrderBook.get(i).orderPrice;
                    System.out.println("Current price :" + currentOrderPrice +"Expected price: " + price );
                    if(price==currentOrderPrice){
                        buyOrderBook.get(i).AddOrder(newOrder);
                        System.out.println("Order added to an existing table");
                        return;
                    }
                    else if(price<currentOrderPrice){
                        buyOrderBook.add(i, CreateNewOrderPrice(newOrder));
                        System.out.println("No entry for this value, creating new entry");
                        return;
                    }
                }
                buyOrderBook.addLast(CreateNewOrderPrice(newOrder));
            }

            //System.out.println("This is a buy order");
        }else if(Objects.equals(newOrder.orderSide, "Sell")){
            System.out.println("This is a sell order");
        }else{
            System.out.println("Error: Side is not valid");
        }

    }
    static OrderPrice CreateNewOrderPrice(Order newOrder)
    {
        OrderPrice newOrderPrice= new OrderPrice();
        newOrderPrice.AddOrder(newOrder);
        newOrderPrice.SetOrderPrice(newOrder.orderPrice);
        return newOrderPrice;
    }
    static void PrintOrderList()
    {
        for (OrderPrice orderLine : buyOrderBook) {
            System.out.print(orderLine.orderPrice + ":    ");
            //System.out.print( " --> "+orderLine.orderPriceList.peek().orderQuantity);
            orderLine.PrintOrderPriceList();
            System.out.println();
        }
    }

    public void Modify(){

    }
}