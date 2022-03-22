package meesho;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;

class User{
	private String userID;
	private String userNae;
}

enum OrderType{
	BUY, SELL
}
class Stock{
	
	private String StockID;
	private String StockName;
	public Stock(String StockName) {
		this.StockName = StockName;
	}

}
class StockComparator implements Comparator<Order>{

	private OrderType orderType;
	
	public StockComparator(OrderType orderType){
		// TODO Auto-generated constructor stub
		this.orderType = orderType;
	}

	@Override
	public int compare(Order o1, Order o2) {
		// TODO Auto-generated method stub
		
		int order1Time = OrderIntegerTime(o1.getOrderTime());
		int order2Time = OrderIntegerTime(o2.getOrderTime());
		
		// buy , 95, 96 , 97
		// sell 90
		if(orderType == OrderType.BUY){
			
			//System.out.println( o1.getOrderID() +" price : "+o1.getAveragePrice() + ",  "+ o2.getOrderID() + " price: " + o2.getAveragePrice());
			
			if(o1.getAveragePrice() < o2.getAveragePrice()){
				return 1;
			}else if(Double.compare(o1.getAveragePrice(), o2.getAveragePrice()) == 0){
                
				//System.out.println( o1.getOrderID() +"order1Time : "+order1Time + ",  "+ o2.getOrderID() + " order2Time: " + order2Time);
				if(order1Time > order2Time){
					return 1;
				}else if(order1Time == order2Time){
					return 0;
				}
			}
			
		}
		
		if(orderType == OrderType.SELL){  // ,ow
			
			if(o1.getAveragePrice() > o2.getAveragePrice()){
				return 1;
			}else if(Double.compare(o1.getAveragePrice(), o2.getAveragePrice()) == 0){
				
				if(order1Time > order2Time){
					return 1;
				}else if(order1Time == order2Time){
					return 0;
				}
			}
			
		}
		
		return -1;
	}

	private int OrderIntegerTime(String orderTime) {
		// TODO Auto-generated method stub
		int h1 = orderTime.charAt(0) - '0';
		int h2 = orderTime.charAt(1) - '0';
		int m1 = orderTime.charAt(3) - '0';
		int m2 = orderTime.charAt(4) - '0';
		return (h1*10 + h2) * 60 + (m1*10+m2);
	}
	
}

class StockTransaction{
	
	private String buyOrderId;
	private double price;
	private int quantity;
	private String sellOrderId;
	
	public StockTransaction(String buyOrderId, double price, int quantity,
			String sellOrderId) {
		super();
		this.buyOrderId = buyOrderId;
		this.price = price;
		this.quantity = quantity;
		this.sellOrderId = sellOrderId;
	}

	
	
	@Override
	public String toString() {
		return "StockTransaction buyOrderId#" + buyOrderId + " price="
				+ price + " quantity=" + quantity + " sellOrderId#"
				+ sellOrderId;
	}
	
}
class Order{
	private String OrderID;
	private String OrderTime;
	private Stock stock;
	private Enum OrderType;
	
	private Double AveragePrice;   // take big decimal in real system
	private int quantity;
	
	public String getOrderID() {
		return OrderID;
	}

	public void setOrderID(String orderID) {
		OrderID = orderID;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Enum getOrderType() {
		return OrderType;
	}

	public void setOrderType(Enum orderType) {
		OrderType = orderType;
	}

	public Double getAveragePrice() {
		return AveragePrice;
	}

	public void setAveragePrice(Double averagePrice) {
		AveragePrice = averagePrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	public String getOrderTime() {
		return OrderTime;
	}

	public void setOrderTime(String orderTime) {
		OrderTime = orderTime;
	}


	
	public Order(String orderID, String orderTime, Stock stock, Enum orderType,
			Double averagePrice, int quantity) {
		super();
		this.OrderID = orderID;
		this.OrderTime = orderTime;
		this.stock = stock;
		this.OrderType = orderType;
		this.AveragePrice = averagePrice;
		this.quantity = quantity;
	}
	
	
	
}

class OrderBook{

	private Stock stock;
	
	static Queue<Order> buyOrders = new PriorityQueue<>(new StockComparator(OrderType.BUY));  // max heap
	static Queue<Order> sellOrders = new PriorityQueue<>(new StockComparator(OrderType.SELL));   // min heap
	
	public OrderBook(Stock stock){
		this.stock = stock;
	}
	public static List<StockTransaction> addOrder(Order order) {
		// TODO Auto-generated method stub
		
		if(order.getOrderType() == OrderType.BUY){
			buyOrders.add(order);
		}else if(order.getOrderType() == OrderType.SELL){
			sellOrders.add(order);
		}
	   
		return match();
	}
	private static List<StockTransaction> match() {
		// TODO Auto-generated method stub
		
	     List<StockTransaction> transcationList = new ArrayList<>();
		
		while(buyOrders.peek() != null && sellOrders.peek()!= null 
				&& buyOrders.peek().getAveragePrice() >= sellOrders.peek().getAveragePrice()){
			
			
			StockTransaction stockTransaction = checkTrancation(buyOrders.peek(),sellOrders.peek());
			
			transcationList.add(stockTransaction);
			
		}
		
		return transcationList;
	}
	private static StockTransaction checkTrancation(Order buyOrder, Order sellOrder) {
		// TODO Auto-generated method stub
		
		int buyOrderQuanity = buyOrder.getQuantity();
		
		int sellOrderQuanity = sellOrder.getQuantity();
		
		int quanitySold = Math.min(buyOrderQuanity, sellOrderQuanity);
		
		if(buyOrderQuanity > sellOrderQuanity){
			
			Order newOrderAFterSelling = buyOrders.peek();
			
			newOrderAFterSelling.setQuantity(buyOrderQuanity  - quanitySold);
			
			//System.out.println("BuyOrder : " + (buyOrderQuanity  - quanitySold) );
			buyOrders.poll();
			sellOrders.poll();
			
			buyOrders.add(newOrderAFterSelling);  // partial
			
		}else if(buyOrderQuanity == sellOrderQuanity){
			buyOrders.poll();
			sellOrders.poll();   // complete 
		}else{
			
			Order newOrderAFterSelling = sellOrders.peek();
			
			newOrderAFterSelling.setQuantity(sellOrderQuanity  - quanitySold);
			//System.out.println("SellOrder : " + (sellOrderQuanity  - quanitySold) );
			buyOrders.poll();
			sellOrders.poll();
			
			sellOrders.add(newOrderAFterSelling);
			
			
		}
		
		StockTransaction stockTransaction = new StockTransaction(buyOrder.getOrderID(),sellOrder.getAveragePrice(),quanitySold,sellOrder.getOrderID());
		
		return stockTransaction;
	}
	
}
class StockSystem{
	
	// buy and sell orderes matching ??
	//  take two priority queue and match it if,  trade happen when Buy_price >= Sell_price
	
	Map<String,List<Order>> UserOrderBook;

	public void intialze() {
		// TODO Auto-generated method stub
		UserOrderBook = new ConcurrentHashMap<>();
	}

	public void placeOrder(Order order) {
		// TODO Auto-generated method stub
		
	}
	
}
/**
 * Sorted by timestamp of order placed
 * @author Yash
 *
 */
class BuyOrderSet{
	
	private SortedSet<Order> orderSet;
	
}
public class Driver {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
        
	
		Order order1 = new Order("1","09:45",new Stock("Meesho"),OrderType.SELL,240.12,100);
		
		Order order2 = new Order("2","09:46",new Stock("Meesho"),OrderType.SELL,237.45,90);
		Order order3 = new Order("3","09:47",new Stock("Meesho"),OrderType.BUY,238.10,110);
		Order order4 = new Order("4","09:48",new Stock("Meesho"),OrderType.BUY,237.8,10);
		Order order5 = new Order("5","09:49",new Stock("Meesho"),OrderType.BUY,237.8,40);
		
		Order order6 = new Order("6","09:50",new Stock("Meesho"),OrderType.SELL,236.0,50);
		
	    OrderBook.addOrder(order1);
		OrderBook.addOrder(order2);
		
		List<StockTransaction> executedTranscation = OrderBook.addOrder(order3);
		
		for(StockTransaction stockTransaction : executedTranscation){
			System.out.println("After Adding order 3 ->"  + stockTransaction.toString());
		}
		List<StockTransaction> executedTranscation1 = OrderBook.addOrder(order4);
		
		for(StockTransaction stockTransaction : executedTranscation1){
			System.out.println("After Adding order 4 ->"  + stockTransaction.toString());
		}
		List<StockTransaction> executedTranscation2 = OrderBook.addOrder(order5);
		
		for(StockTransaction stockTransaction : executedTranscation2){
			System.out.println("After Adding order 5 ->"  + stockTransaction.toString());
		}
		List<StockTransaction> executedTranscation3 =OrderBook.addOrder(order6);
		
		for(StockTransaction stockTransaction : executedTranscation3){
			System.out.println("After Adding order 6 ->"  +  stockTransaction.toString());
		}
		
		//OrderBook.match();
		//OrderBook.match();
		
		
		// Issue: not added time comparison in Comparator
	}

}
