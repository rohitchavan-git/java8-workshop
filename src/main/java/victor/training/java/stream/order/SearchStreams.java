package victor.training.java.stream.order;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import victor.training.java.stream.order.entity.Customer;
import victor.training.java.stream.order.entity.Order;
import victor.training.java.stream.order.entity.OrderLine;

public class SearchStreams {
	/**
	 * - shorten/clean it up
	 */
	public List<Order> p1_getActiveOrders(Customer customer) {	
		return customer.getOrders().stream()
				.filter(order -> order.getStatus() == Order.Status.ACTIVE)
				.collect(toList());
	}
	
	/**
	 * @return the Order in the list with the given id, or null if not found
	 * - findAny or findFirst ?
	 */
	public Order p2_getOrderById(List<Order> orders, long orderId) {

		return orders.stream()
				.filter(order -> order.getId().equals(orderId))
				.findFirst()
				.orElse(null);
	}
	
	/**
	 * @return true if customer has at least one order with status ACTIVE
	 */
	public boolean p3_hasActiveOrders(Customer customer) {

		return customer.getOrders()
				.stream()
				.map(Order::getStatus)
				.anyMatch(Order.Status.ACTIVE::equals);

	}

	/**
	 * An Order can be returned if it doesn't contain 
	 * any OrderLine with isSpecialOffer()==true
	 */
	public boolean p4_canBeReturned(Order order) {

		return order.getOrderLines()
				.stream()
				.anyMatch(Predicate.not(OrderLine::isSpecialOffer));

		// order.getOrderLines().stream()
	}
	
	/**
	 * Return the Order with maximum getTotalPrice.
	 * i.e. the most expensive Order, or null if no Orders
	 * - Challenge: return an Optional<creationDate>
	 */
	public Order p5_getMaxPriceOrder(Customer customer) {

		return customer.getOrders()
				.stream()
				.max(comparing(Order::getTotalPrice))
				.orElse(null);
	}
	
	/**
	 * last 3 Orders sorted descending by creationDate
	 */
	public List<Order> p6_getLatestOrders(Customer customer) {

		return customer.getOrders()
				.stream()
				.sorted(comparing(Order::getCreationDate).reversed())
				.limit(3)
				.toList();

	}
	
	
}
