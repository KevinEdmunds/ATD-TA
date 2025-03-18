package za.co.entelect.bootcamp.java;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class OrderPrice {
    public LinkedList<Order> orderPriceList = new LinkedList<Order>();
    public float orderPrice;

    public void AddOrder(Order newOrder)
    {
        orderPriceList.add(newOrder);
    }
    public void DeleteOrder(int id)
    {
        orderPriceList.remove(id);
    }
    public void ModifyOrder(int id, int quanitiy) {
        Order modifiedOrder = orderPriceList.get(id);
        modifiedOrder.orderQuantity=quanitiy;
        modifiedOrder.orderDate= LocalDateTime.now();
    }
    public void UpdateOrderPriority(int id){
        try{
            Order modifiedOrder = orderPriceList.get(id);
            orderPriceList.remove(id);
            orderPriceList.addLast(modifiedOrder);

        } catch (Exception e) {
            System.err.println("Something went wrong: " + e.getMessage());
        }
     }
    public void SetOrderPrice(float price)
    {
        orderPrice=price;
    }
    public float GetOrderPrice()
    {
        return orderPrice;
    }
    public void PrintOrderPriceList(){
        for(Order orders: orderPriceList)
        {
            System.out.print(orders.orderId);
            System.out.print(" | ");
            System.out.print(orders.orderPrice);
            System.out.print(" | ");
            System.out.print(orders.orderQuantity);
            System.out.print(" | ");
            System.out.print(orders.orderDate);
            System.out.println();
        }
    }
    public Integer FindOrderByID(Integer id){
        int index =0;
        for(Order order: orderPriceList){
            if(order.orderId==id){
                return index;
            }
            index++;
        }
        return -1;
    }
}