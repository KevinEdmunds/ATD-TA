import java.util.LinkedList;
import java.util.Queue;

public class OrderPrice {
    public Queue<Order> orderPriceList = new LinkedList<Order>();
    public float orderPrice;

    //queue order based on priority (date/time submitted)
    public void AddOrder(Order newOrder) {
        System.out.println("Adding an item to an existing price line");
        orderPriceList.add(newOrder);
    }
    public void SetOrderPrice(float price)
    {
        orderPrice=price;
    }
    public void PrintOrderPriceList(){
        //System.out.println(orderPriceList.size());
        for(Order orders: orderPriceList)
        {
            System.out.print(orders.orderQuantity + " - - > ");
        }
    }
}