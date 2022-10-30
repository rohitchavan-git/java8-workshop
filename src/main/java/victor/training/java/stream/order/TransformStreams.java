package victor.training.java.stream.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.weaver.ast.Or;
import victor.training.java.stream.order.dto.OrderDto;
import victor.training.java.stream.order.entity.Customer;
import victor.training.java.stream.order.entity.Order;
import victor.training.java.stream.order.entity.OrderLine;
import victor.training.java.stream.order.entity.Product;
import victor.training.java.stream.order.entity.Order.PaymentMethod;

import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class TransformStreams {

	/**
	 * Transform all entities to DTOs.
	 * use .map
	 */
	public List<OrderDto> p01_toDtos(List<Order> orders) {

		return orders.stream()
				.map( order -> new OrderDto(order.getTotalPrice(),
						order.getCreationDate()))
				.collect(toList());
		
	}

	/**
	 * Note: Order.getPaymentMethod()
	 */
	public Set<PaymentMethod> p02_getUsedPaymentMethods(Customer customer) {

		return Optional.ofNullable(customer)
				.map(Customer::getOrders)
				.stream()
				.flatMap(List::stream)
				.map(Order::getPaymentMethod)
				.collect(toSet());
	}
	
	/**
	 * When did the customer created orders ?
	 * Note: Order.getCreationDate()
	 */
	public List<LocalDate> p03_getOrderDatesAscending(Customer customer) {

		Stream<Order> orderStream = Optional.ofNullable(customer)
				.map(Customer::getOrders)
				.stream()
				.flatMap(List::stream);

		List<LocalDate> localDates = orderStream
				.map(Order::getCreationDate)
				.sorted(comparing(identity()))
				.distinct()
				.toList();
		return localDates;
	}
	
	
	/**
	 * @return a map order.id -> order
	 */
	public Map<Long, Order> p04_mapOrdersById(Customer customer) {

		return customer.getOrders().stream()
				.collect(toMap(Order::getId, identity()));
	}
	
	/** 
	 * Orders grouped by Order.paymentMethod
	 */
	public Map<PaymentMethod, List<Order>> p05_getProductsByPaymentMethod(Customer customer) {

		Map<PaymentMethod, List<Order>> paymentMethodListMap = customer.getOrders()
				.stream()
				.collect(groupingBy(Order::getPaymentMethod,
						toList()));

		return paymentMethodListMap;
	}
	
	// -------------- MOVIE BREAK :p --------------------
	
	/** 
	 * A hard one !
	 * Get total number of products bought by a customer, across all her orders.
	 * Customer --->* Order --->* OrderLines(.count .product)
	 * The sum of all counts for the same product.
	 * i.e. SELECT PROD_ID, SUM(COUNT) FROM PROD GROUPING BY PROD_ID
	 */
	public Map<Product, Integer> p06_getProductCount(Customer customer) {


		Map<Product, List<OrderLine>> collect = customer.getOrders().stream()
				.map(Order::getOrderLines)
				.flatMap(Collection::stream)
				.collect(groupingBy(OrderLine::getProduct));

		Function<Map.Entry<Product, List<OrderLine>>, Integer> sumOfProductItem = value1 -> value1.getValue()
				.stream().mapToInt(OrderLine::getCount)
				.sum();

		return collect.entrySet()
				.stream()
				.collect(toMap(Map.Entry::getKey, sumOfProductItem));
		
	}
	
	/**
	 * All the unique products bought by the customer, 
	 * sorted by Product.name.
	 */
	public List<Product> p07_getAllOrderedProducts(Customer customer) {

		Stream<OrderLine> orderLineStream = customer.getOrders()
				.stream()
				.map(Order::getOrderLines)
				.flatMap(List::stream);

		List<Product> products = orderLineStream
				.map(OrderLine::getProduct)
				.sorted(comparing(Product::getName))
				.distinct()
				.toList();
		return products;
	}
	
	
	/**
	 * The names of all the products bought by Customer,
	 * sorted and then concatenated by ",".
	 * Example: "Armchair,Chair,Table".
	 * Hint: Reuse the previous function.
	 */
	public String p08_getProductsJoined(Customer customer) {

		Stream<OrderLine> orderLineStream = customer.getOrders().stream()
				.map(Order::getOrderLines)
				.flatMap(List::stream);
		return orderLineStream
				.map(OrderLine::getProduct)
				.map(Product::getName)
				.sorted(comparing(identity()))
				.distinct()
				.collect(joining(","));
	}
	
	/**
	 * Sum of all Order.getTotalPrice(), truncated to Long.
	 */
	public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
		// TODO +, longValue(), reduce()

		return customer.getOrders()
				.stream()
				.map(order -> order.getTotalPrice().longValue())
				.reduce(0L, Long::sum);
	}
}
