import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.entelect.bootcamp.java.Order;
import za.co.entelect.bootcamp.java.OrderPrice;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderPriceTest {

    private OrderPrice orderPrice;

    @BeforeEach
    void setUp() {
        orderPrice = new OrderPrice();
        orderPrice.SetOrderPrice(100.0f);
    }

    @Test
    void testAddOrder() {
        Order order = new Order(1, 100.0f, 10, "Buy", LocalDateTime.now());

        orderPrice.AddOrder(order);

        assertEquals(1, orderPrice.orderPriceList.size());
        assertEquals(order, orderPrice.orderPriceList.getFirst());
    }

    @Test
    void testDeleteOrder() {
        Order order1 = new Order(1, 100.0f, 10, "Buy", LocalDateTime.now());
        Order order2 = new Order(2, 100.0f, 20, "Buy", LocalDateTime.now());
        orderPrice.AddOrder(order1);
        orderPrice.AddOrder(order2);

        orderPrice.DeleteOrder(0);

        assertEquals(1, orderPrice.orderPriceList.size());
        assertEquals(order2, orderPrice.orderPriceList.getFirst());
    }

    @Test
    void testModifyOrder() {

        Order order1 = new Order(1, 100.0f, 10, "Buy", LocalDateTime.now());
        Order order2 = new Order(2, 100.0f, 20, "Buy", LocalDateTime.now());

        orderPrice.AddOrder(order1);
        orderPrice.AddOrder(order2);

        orderPrice.ModifyOrder(0, 15);

        assertEquals(2, orderPrice.orderPriceList.size());
        assertEquals(15, orderPrice.orderPriceList.getFirst().orderQuantity);
        assertEquals(2, orderPrice.orderPriceList.getLast().orderId);
        assertEquals(order2, orderPrice.orderPriceList.getLast());
    }

    @Test
    void testUpdateOrderPriority(){
        Order order1 = new Order(1, 100f, 10, "Buy" , LocalDateTime.now());
        Order order2 = new Order(2, 100f, 20, "Buy" , LocalDateTime.now());

        orderPrice.AddOrder(order1);
        orderPrice.AddOrder(order2);

        orderPrice.UpdateOrderPriority(1);

        assertEquals(2, orderPrice.orderPriceList.size());
        assertEquals(20, orderPrice.orderPriceList.getLast().orderQuantity);
        assertEquals(1, orderPrice.orderPriceList.getFirst().orderId);
        assertEquals(order1, orderPrice.orderPriceList.getFirst());
    }

    @Test
    void testSetOrderPrice() {
        orderPrice.SetOrderPrice(150.5f);

        assertEquals(150.5f, orderPrice.orderPrice);
    }

    @Test
    void testFindOrderById() {
        Order order1 = new Order(1, 100.0f, 10, "Buy", LocalDateTime.now());
        Order order2 = new Order(2, 100.0f, 20, "Buy", LocalDateTime.now());
        Order order3 = new Order(3, 100.0f, 30, "Buy", LocalDateTime.now());
        orderPrice.AddOrder(order1);
        orderPrice.AddOrder(order2);
        orderPrice.AddOrder(order3);

        int index = orderPrice.FindOrderByID(2);

        assertEquals(1, index);
    }

    @Test
    void testFindNonExistentOrderById() {
        Order order1 = new Order(1, 100.0f, 10, "Buy", LocalDateTime.now());
        orderPrice.AddOrder(order1);

        int index = orderPrice.FindOrderByID(999);

        assertEquals(-1, index);
    }
}
