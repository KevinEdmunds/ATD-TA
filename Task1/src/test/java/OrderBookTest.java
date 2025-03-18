import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.entelect.bootcamp.java.Order;
import za.co.entelect.bootcamp.java.OrderBook;
import za.co.entelect.bootcamp.java.OrderPrice;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderBookTest {



    @BeforeEach
    void resetStaticFields() throws Exception {
        Field buyOrderBookField = OrderBook.class.getDeclaredField("buyOrderBook");
        buyOrderBookField.setAccessible(true);
        LinkedList<OrderPrice> emptyBuyList = new LinkedList<>();
        buyOrderBookField.set(null, emptyBuyList);

        Field sellOrderBookField = OrderBook.class.getDeclaredField("sellOrderBook");
        sellOrderBookField.setAccessible(true);
        LinkedList<OrderPrice> emptySellList = new LinkedList<>();
        sellOrderBookField.set(null, emptySellList);

        Field orderIdField = OrderBook.class.getDeclaredField("orderId");
        orderIdField.setAccessible(true);
        orderIdField.set(null, 0);
    }

    @Test
    void testAddBuyOrder() throws Exception {
        Order buyOrder = new Order(0, 100.0f, 10, "Buy", LocalDateTime.now());

        OrderBook.Add(buyOrder, "Buy");

        Field buyOrderBookField = OrderBook.class.getDeclaredField("buyOrderBook");
        buyOrderBookField.setAccessible(true);
        LinkedList<OrderPrice> buyOrderBook = (LinkedList<OrderPrice>) buyOrderBookField.get(null);

        assertEquals(1, buyOrderBook.size());
        assertEquals(100.0f, buyOrderBook.getFirst().orderPrice);
        assertEquals(1, buyOrderBook.getFirst().orderPriceList.size());
        assertEquals(0, buyOrderBook.getFirst().orderPriceList.getFirst().orderId);
    }

    @Test
    void testAddSellOrder() throws Exception {
        Order sellOrder = new Order(0, 100.0f, 10, "Sell", LocalDateTime.now());

        OrderBook.Add(sellOrder, "Sell");

        Field sellOrderBookField = OrderBook.class.getDeclaredField("sellOrderBook");
        sellOrderBookField.setAccessible(true);
        LinkedList<OrderPrice> sellOrderBook = (LinkedList<OrderPrice>) sellOrderBookField.get(null);

        assertEquals(1, sellOrderBook.size());
        assertEquals(100.0f, sellOrderBook.getFirst().orderPrice);
        assertEquals(1, sellOrderBook.getFirst().orderPriceList.size());
        assertEquals(0, sellOrderBook.getFirst().orderPriceList.getFirst().orderId);
    }

    @Test
    void testAddOrdersSamePrice() throws Exception {
        Order buyOrder1 = new Order(0, 100.0f, 10, "Buy", LocalDateTime.now());
        Order buyOrder2 = new Order(1, 100.0f, 20, "Buy", LocalDateTime.now());

        OrderBook.Add(buyOrder1, "Buy");
        OrderBook.Add(buyOrder2, "Buy");

        Field buyOrderBookField = OrderBook.class.getDeclaredField("buyOrderBook");
        buyOrderBookField.setAccessible(true);
        LinkedList<OrderPrice> buyOrderBook = (LinkedList<OrderPrice>) buyOrderBookField.get(null);

        assertEquals(1, buyOrderBook.size());
        assertEquals(100.0f, buyOrderBook.getFirst().orderPrice);
        assertEquals(2, buyOrderBook.getFirst().orderPriceList.size());
    }

    @Test
    void testBuyOrderPriceSorting() throws Exception {
        Order buyOrder1 = new Order(0, 90.0f, 10, "Buy", LocalDateTime.now());
        Order buyOrder2 = new Order(1, 100.0f, 20, "Buy", LocalDateTime.now());
        Order buyOrder3 = new Order(2, 95.0f, 30, "Buy", LocalDateTime.now());

        OrderBook.Add(buyOrder1, "Buy");
        OrderBook.Add(buyOrder2, "Buy");
        OrderBook.Add(buyOrder3, "Buy");

        Field buyOrderBookField = OrderBook.class.getDeclaredField("buyOrderBook");
        buyOrderBookField.setAccessible(true);
        LinkedList<OrderPrice> buyOrderBook = (LinkedList<OrderPrice>) buyOrderBookField.get(null);;

        assertEquals(100.0f, buyOrderBook.get(0).orderPrice);
        assertEquals(95.0f, buyOrderBook.get(1).orderPrice);
        assertEquals(90.0f, buyOrderBook.get(2).orderPrice);
    }

    @Test
    void testDeleteOrder() throws Exception {
        Order buyOrder1 = new Order(0, 100.0f, 10, "Buy", LocalDateTime.now());
        Order buyOrder2 = new Order(1, 100.0f, 20, "Buy", LocalDateTime.now());
        OrderBook.Add(buyOrder1, "Buy");
        OrderBook.Add(buyOrder2, "Buy");

        OrderBook.Delete(0, "Buy");

        Field buyOrderBookField = OrderBook.class.getDeclaredField("buyOrderBook");
        buyOrderBookField.setAccessible(true);
        LinkedList<OrderPrice> buyOrderBook = (LinkedList<OrderPrice>) buyOrderBookField.get(null);

        assertEquals(1, buyOrderBook.size());
        assertEquals(1, buyOrderBook.getFirst().orderPriceList.size());
        assertEquals(1, buyOrderBook.getFirst().orderPriceList.getFirst().orderId);
    }

    @Test
    void testModifyOrder() throws Exception {
        Order buyOrder = new Order(0, 100.0f, 10, "Buy", LocalDateTime.now());
        OrderBook.Add(buyOrder, "Buy");

        OrderBook.Modify(0, 15, "Buy", true);

        Field buyOrderBookField = OrderBook.class.getDeclaredField("buyOrderBook");
        buyOrderBookField.setAccessible(true);
        LinkedList<OrderPrice> buyOrderBook = (LinkedList<OrderPrice>) buyOrderBookField.get(null);

        assertEquals(1, buyOrderBook.size());
        assertEquals(1, buyOrderBook.getFirst().orderPriceList.size());
        assertEquals(15, buyOrderBook.getFirst().orderPriceList.getFirst().orderQuantity);
    }

    @Test
    void testCreateNewOrderPrice() {
        Order buyOrder = new Order(0, 100.0f, 10, "Buy", LocalDateTime.now());

        OrderPrice result = OrderBook.CreateNewOrderPrice(buyOrder);

        assertEquals(100.0f, result.orderPrice);
        assertEquals(1, result.orderPriceList.size());
        assertEquals(buyOrder, result.orderPriceList.getFirst());
    }
}