package za.co.entelect.bootcamp.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Objects;

public class OrderBook {
    private static LinkedList<OrderPrice> buyOrderBook = new LinkedList<OrderPrice>();
    private static LinkedList<OrderPrice> sellOrderBook = new LinkedList<OrderPrice>();
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

        System.out.print("Side: ");
        String side = r.readLine();

        Modify(id, quantity, side);
    }

    private static void DeleteOrderDetails() throws IOException {
        BufferedReader r = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.print("Order ID: ");
        int id = Integer.parseInt(r.readLine());

        System.out.print("Side: ");
        String side = r.readLine();


        Delete(id, side);
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
        Add(newOrder, side);

        LinkedList<OrderPrice> orderSide;
        if(Objects.equals(side, "Buy"))
        {
            orderSide=buyOrderBook;
        }else{
            orderSide=sellOrderBook;
        }
        orderId++;
        PrintOrderList(orderSide);
    }

    public static void Modify(int id, int quantity, String side){
        int index = 0;
        LinkedList<OrderPrice> orderSide;
        if(Objects.equals(side, "Buy"))
        {
            orderSide=buyOrderBook;
        }else{
            orderSide=sellOrderBook;
        }
        for(OrderPrice orderPrice: orderSide){
            index = orderPrice.FindOrderByID(id);
            if(index!=-1)
            {
                orderPrice.ModifyOrder(index, quantity);
                break;
            }
        }
        PrintOrderList(orderSide);
    }

    public static void Delete(int id, String side ) {
        int index = 0;
        LinkedList<OrderPrice> orderSide;
        if(Objects.equals(side, "Buy"))
        {
            orderSide=buyOrderBook;
        }else{
            orderSide=sellOrderBook;
        }
        for(OrderPrice orderPrice: orderSide){
           index = orderPrice.FindOrderByID(id);
            if(index!=-1)
            {
                orderPrice.DeleteOrder(index);
                break;
            }
        }
        PrintOrderList(orderSide);
    }

    public static void Add(Order newOrder, String side) {
        float price = newOrder.orderPrice;
        LinkedList<OrderPrice> orderSide;
        boolean isBuy;

        if(Objects.equals(newOrder.orderSide, "Buy")) {
            orderSide = buyOrderBook;
            isBuy = true;
        } else {
            orderSide = sellOrderBook;
            isBuy = false;
        }

        if(orderSide.isEmpty()) {
            orderSide.add(CreateNewOrderPrice(newOrder));
            return;
        }

        if((isBuy && price > orderSide.getFirst().orderPrice) ||
                (!isBuy && price < orderSide.getFirst().orderPrice)) {
            orderSide.addFirst(CreateNewOrderPrice(newOrder));
            return;
        } else if((isBuy && price < orderSide.getLast().orderPrice) ||
                (!isBuy && price > orderSide.getLast().orderPrice)) {
            orderSide.addLast(CreateNewOrderPrice(newOrder));
            return;
        } else {
            for(int i = 0; i < orderSide.size(); i++) {
                float currentOrderPrice = orderSide.get(i).orderPrice;

                if(price == currentOrderPrice) {
                    orderSide.get(i).AddOrder(newOrder);
                    return;
                } else if((isBuy && price > currentOrderPrice) ||
                        (!isBuy && price < currentOrderPrice)) {
                    orderSide.add(i, CreateNewOrderPrice(newOrder));
                    return;
                }
            }
            orderSide.addLast(CreateNewOrderPrice(newOrder));
        }
    }

    public static OrderPrice CreateNewOrderPrice(Order newOrder)
    {
        OrderPrice newOrderPrice= new OrderPrice();
        newOrderPrice.AddOrder(newOrder);
        newOrderPrice.SetOrderPrice(newOrder.orderPrice);
        return newOrderPrice;
    }
    static void PrintOrderList(LinkedList<OrderPrice> orderSide)
    {

        System.out.println("Buy Orders -------------------------------------------");
        for (OrderPrice orderLine : buyOrderBook) {
            System.out.print(orderLine.orderPrice + ":    ");
            orderLine.PrintOrderPriceList();
            System.out.println();
        }
        System.out.println("Sell Orders -------------------------------------------");
        for (OrderPrice orderLine : sellOrderBook) {
            System.out.print(orderLine.orderPrice + ":    ");
            orderLine.PrintOrderPriceList();
            System.out.println();
        }
    }
}