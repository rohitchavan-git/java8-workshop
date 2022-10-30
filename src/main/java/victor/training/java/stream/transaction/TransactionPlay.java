package victor.training.java.stream.transaction;

import org.junit.jupiter.api.Test;

import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;



public class TransactionPlay {

	private Trader raoul = new Trader("Raoul", "Cambridge");
	private Trader mario = new Trader("Mario","Milan");
	private Trader alan = new Trader("Alan","Cambridge");
	private Trader brian = new Trader("Brian","Cambridge");

	private List<Transaction> transactions = Arrays.asList(
	    new Transaction(brian, 2011, 300),
	    new Transaction(raoul, 2012, 1000),
	    new Transaction(raoul, 2011, 400),
	    new Transaction(mario, 2012, 710),
	    new Transaction(mario, 2012, 700),
	    new Transaction(alan, 2012, 950)
	);
	
	private Transaction[] tx = transactions.toArray(new Transaction[0]);
		
	@Test //1
	public void all_2011_transactions_sorted_by_value() {
		List<Transaction> expected = Arrays.asList(tx[0], tx[2]);
		
		List<Transaction> list =
		transactions.stream()
						.filter( transaction -> transaction.getYear() == 2011)
						.collect(toList());

		assertEquals(expected, list); 									
	}
		
	@Test //2
	public void unique_cities_of_the_traders() {
		List<String> expected = Arrays.asList("Cambridge", "Milan");
		
		List<String> list = transactions.stream()
				.map(transaction -> Optional.ofNullable(transaction.getTrader())
						.map(Trader::getCity))
				.flatMap(Optional::stream)
				.distinct()
				.collect(toList());


		assertEquals(expected, list); 									
	}
	
	@Test //3
	public void traders_from_Cambridge_sorted_by_name() {
		List<Trader> expected = Arrays.asList(alan, brian, raoul);

		List<Trader> list =
		transactions.stream()
						.map(Transaction::getTrader)
				       .filter(trader -> Optional.ofNullable(trader)
							   .map(Trader::getCity)
							   .filter("Cambridge"::equalsIgnoreCase)
							   .isPresent())
						.sorted(comparing(Trader::getName))
						.distinct()
				.toList();
		
		assertEquals(expected, list);
	}
	
	@Test //4
	public void names_of_all_traders_sorted_joined() {
		String expected = "Alan,Brian,Mario,Raoul";
		
		String joined = transactions.stream()
						 .map(Transaction::getTrader)
				         .map(Trader::getName)
				         .sorted(comparing(identity()))
				         .distinct()
				         .collect(Collectors.joining(","));
		
		assertEquals(expected, joined);
	}
			
	@Test //5
	public void are_traders_in_Milan() {
		boolean areTradersInMilan =
		transactions.stream()
						.map(Transaction::getTrader)
								.anyMatch(trader -> trader.getCity().equalsIgnoreCase("Milan"));
		
		assertTrue(areTradersInMilan);
	}
	
	@Test //6 
	public void sum_of_values_of_transactions_from_Cambridge_traders() { 
		int sum = transactions.stream()
						.filter( transaction ->
							 Optional.ofNullable(transaction.getTrader())
									 .map(Trader::getCity)
									 .filter("Cambridge"::equalsIgnoreCase).isPresent())
				.mapToInt(Transaction::getValue).sum();

		assertEquals(2650, sum);
	}
	
	@Test //7
	public void max_transaction_value() {
		int max = transactions.stream()
						.map(Transaction::getValue)
								.max(Integer::compareTo)
						.orElse(0);
		
		assertEquals(1000, max);
	}

	
	@Test
	public void transaction_with_smallest_value() {
		Transaction expected = tx[0];
		Transaction min =
		transactions.stream()
						.min(comparing(Transaction::getValue))
				.orElse(null);

		assertEquals(expected, min);
	}
	@Test
	public void fibonacci_first_10() {
		List<Integer> expected = Arrays.asList(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
		Stream<Integer> fibonacci = null;

		List<Integer> fib10 = fibonacci.limit(10).collect(toList());
		assertEquals(expected, fib10);
	}
	
	@Test
	public void a_transaction_from_2012() {
		Transaction expected = tx[1];
		Transaction tx2012 =  transactions.stream()
				.filter(transaction -> transaction.getYear() == 2012)
				.findFirst().orElse(null);

		assertEquals(expected, tx2012);
	}
	
	@Test
	public void uniqueCharactersOfManyWords() {
		List<String> expected = Arrays.asList("a", "b", "c", "d", "f");
		List<String> wordsStream = Arrays.asList("abcd", "acdf");
		
		List<String> actual =  wordsStream.stream()
				.flatMap(words -> Arrays.stream(words.split("")))
				.distinct()
				.toList();
		assertEquals(expected, actual);
	}
	
	
}
