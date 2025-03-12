import java.time.LocalDateTime;

public class Order {
    public int orderId;
    public float orderPrice;
    public int orderQuantity;
    public String orderSide;
    public LocalDateTime orderDate;

    public Order(int id, float price, int quantity, String side, LocalDateTime date)
    {
        orderId=id;
        orderPrice=price;
        orderQuantity=quantity;
        orderSide=side;
        orderDate=date;
    }
}
