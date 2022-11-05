package victor.training.java.advanced;

import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.model.OrderLine;
import victor.training.java.advanced.model.Product;
import victor.training.java.advanced.repo.ProductRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

public class StreamWreck {
   private ProductRepo productRepo;

   public List<Product> getFrequentOrderedProducts(List<Order> orders) {

      Map<Product, Integer> productBySumming = orders.stream()
              .filter(o -> o.getCreationDate().isAfter(LocalDate.now().minusYears(1)))
              .flatMap(o -> o.getOrderLines().stream())
              .collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));

      List<Product> hiddenProduct = productRepo.findByHiddenTrue();

      return productBySumming.entrySet()
                     .stream()
                     .filter(e -> e.getValue() >= 10)
                     .map(Entry::getKey)
                     .filter(not(Product::isDeleted))
                     .filter(not(hiddenProduct::contains))
                     .collect(toList());
   }
}


