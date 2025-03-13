package za.co.entelect.bootcamp.java;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class OrderPrice {
    public LinkedList<Order> orderPriceList = new LinkedList<Order>();
    public float orderPrice;

    //queue order based on priority (date/time submitted)
    public void AddOrder(Order newOrder) {
        //System.out.println("Adding an item to an existing price line");
        orderPriceList.add(newOrder);
    }
    public void DeleteOrder(int id)
    {
        orderPriceList.remove(id);
    }
    public void ModifyOrder(int id, int quanitiy)
    {
        Order modifiedOrder = orderPriceList.get(id);
        modifiedOrder.orderQuantity=quanitiy;
        modifiedOrder.orderDate= LocalDateTime.now();

        orderPriceList.remove(id);
        orderPriceList.addLast(modifiedOrder);
    }
    public void SetOrderPrice(float price)
    {
        orderPrice=price;
    }
    public void PrintOrderPriceList(){
        //System.out.println(orderPriceList.size());
        for(Order orders: orderPriceList)
        {
            System.out.print(orders.orderQuantity + " (" + orders.orderId+") "+ " - - > ");
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