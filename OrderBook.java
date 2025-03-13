import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Objects;

public class OrderBook {
    private static LinkedList<OrderPrice> buyOrderBook = new LinkedList<OrderPrice>();
    private LinkedList<OrderPrice> sellOrderBook = new LinkedList<OrderPrice>();
    public static int orderId=0;

    public static void main(String[] args) throws IOException {
        while(true)
        {
            BufferedReader r = new BufferedReader(
                    new InputStreamReader(System.in));

            System.out.println("< - - - - - - - - - - - - - >");
            System.out.print("Select operation: ");
            String operation = r.readLine();

            switch (operation) {
                case "Add" -> AddOrderDetails();
                case "Delete" -> DeleteOrderDetails();
                case "Modify" -> ModifyOrderDetails();
                case null, default -> System.out.println("Invalid operation");
            }
        }
    }

    private static void ModifyOrderDetails() throws IOException{
        BufferedReader r = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.print("Order ID: ");
        int id = Integer.parseInt(r.readLine());

        System.out.print("Quantity change: ");
        int quantity = Integer.parseInt(r.readLine());

        Modify(id, quantity);
    }


    private static void DeleteOrderDetails() throws IOException {
        BufferedReader r = new BufferedReader(
                new InputStreamReader(System.in));

        int id = Integer.parseInt(r.readLine());

        Delete(id);
    }

    private static void AddOrderDetails() throws IOException {
        BufferedReader r = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("< - - - - - - - - - - - - - >");
        System.out.print("Enter Price: ");
        float price = Float.parseFloat(r.readLine());

        System.out.print("Enter Qty: ");
        int quantity = Integer.parseInt(r.readLine());

        System.out.print("Enter Side: ");
        String side = r.readLine();

        Order newOrder = new Order(orderId, price, quantity,side, LocalDateTime.now());
        Add(newOrder);
        orderId++;
        PrintOrderList();
    }

    public static void Modify(int id, int quantity){
        int index = 0;
        for(OrderPrice orderPrice: buyOrderBook){
            index = orderPrice.FindOrderByID(id);
            if(index!=-1)
            {
                orderPrice.ModifyOrder(index, quantity);
                break;
            }
        }
        PrintOrderList();
    }

    public static void Delete(int id) {
        int index = 0;
        for(OrderPrice orderPrice: buyOrderBook){
           index = orderPrice.FindOrderByID(id);
            if(index!=-1)
            {
                orderPrice.DeleteOrder(index);
                break;
            }
        }
        PrintOrderList();
        System.out.println("This is the item to delete: " +index );
    }

    public static void Add(Order newOrder){
        float price = newOrder.orderPrice;
        //System.out.println("Adding item");

        if(Objects.equals(newOrder.orderSide, "Buy")) {
            //System.out.println("Buy order");
            if(buyOrderBook.isEmpty())
            {
                buyOrderBook.add(CreateNewOrderPrice(newOrder));
               //System.out.println("This is a new order");
                return;
            }
            if(price>buyOrderBook.getLast().orderPrice)
            {
                buyOrderBook.addLast(CreateNewOrderPrice(newOrder));
                //System.out.println("The new biggest entry");
                return;
            }else if(price<buyOrderBook.getFirst().orderPrice)
            {
                buyOrderBook.addFirst(CreateNewOrderPrice(newOrder));
                //System.out.println("The new smallest entry");
                return;
            }else{
                //System.out.println("Running in the for loop");
                for(int i=0; i<buyOrderBook.size();i++)
                {

                    float currentOrderPrice = buyOrderBook.get(i).orderPrice;
                    //System.out.println("Current price :" + currentOrderPrice +"Expected price: " + price );
                    if(price==currentOrderPrice){
                        buyOrderBook.get(i).AddOrder(newOrder);
                        //System.out.println("Order added to an existing table");
                        return;
                    }
                    else if(price<currentOrderPrice){
                        buyOrderBook.add(i, CreateNewOrderPrice(newOrder));
                        //System.out.println("No entry for this value, creating new entry");
                        return;
                    }
                }
                buyOrderBook.addLast(CreateNewOrderPrice(newOrder));
            }//System.out.println("This is a buy order");
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
}