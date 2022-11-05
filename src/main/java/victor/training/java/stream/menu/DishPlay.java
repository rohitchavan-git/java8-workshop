package victor.training.java.stream.menu;

import victor.training.java.stream.menu.Dish.Type;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class DishPlay {
   public static final List<Dish> menu = Arrays.asList(
       new Dish("pork", false, 800, Type.MEAT),
       new Dish("beef", false, 700, Type.MEAT),
       new Dish("chicken", false, 400, Type.MEAT),
       new Dish("french fries", true, 530, Type.OTHER),
       new Dish("rice", true, 350, Type.OTHER),
       new Dish("season fruit", true, 120, Type.OTHER),
       new Dish("pizza", true, 550, Type.OTHER),
       new Dish("prawns", false, 300, Type.FISH),
       new Dish("salmon", false, 450, Type.FISH));

   public static void main(String[] args) {
      // The above code sample + problems are from Java 8 in Action book (Manning)

      // TODO select the low-calories (<400) items

      List<Dish> lowCalories = menu.stream()
              .filter(dish -> dish.getCalories() < 400)
              .collect(Collectors.toList());
      System.out.println(lowCalories);

      // TODO find out three high-calorie dish names

      List<Dish> top3HighCalorieItem = menu.stream()
              .sorted(comparing(Dish::getCalories).reversed())
              .limit(3)
              .toList();

      System.out.println(top3HighCalorieItem);


      // TODO find out the 2nd and 3rd most caloric items


      List<Dish> top2and3CaloricItems = menu.stream()
              .sorted(comparing(Dish::getCalories).reversed())
              .skip(1)
              .limit(2)
              .collect(toList());

      System.out.println(top2and3CaloricItems);

      // TODO find vegetarian dishes

      List<Dish> vegetarianDishes = menu.stream()
              .filter(Dish::isVegetarian)
              .toList();

      // TODO find 2 dishes with meat

      List<Dish> twoMeatDishes = menu.stream()
              .filter(dish -> Type.MEAT.equals(dish.getType()))
              .limit(2)
              .toList();

      // TODO Map<Type, Map<Object, List<Dish>>> dishesByTypeAndCaloricLevel

      Map<Type, Map<Integer, List<Dish>>> dishesByTypeAndCaloricLevel = menu.stream()
              .collect(groupingBy(Dish::getType,
                      groupingBy(Dish::getCalories)));


      // TODO Map<Dish.Type, Long> typesCount

      Map<Type, Long> typesCount = menu.stream()
              .collect(groupingBy(Dish::getType, counting()));


      // TODO Map<Dish.Type, Set<Dish>> groupedAsSets

      Map<Type, Set<Dish>> groupedAsSets = menu.stream()
              .collect(groupingBy(Dish::getType, toSet()));

      // TODO Map<Dish.Type, Integer> totalCaloriesByType

      Map<Type, List<Dish>> collect = menu.stream()
              .collect(groupingBy(Dish::getType));

      Function<Map.Entry<Type, List<Dish>>, Integer> getSumOfCalories =
              mapVal1 -> mapVal1.getValue()
              .stream()
              .mapToInt(Dish::getCalories)
              .sum();
      Map<Type, Integer> totalCaloriesByType = collect.entrySet()
              .stream()
              .collect(toMap(Map.Entry::getKey, getSumOfCalories));



      // TODO Map<Dish.Type, Optional<Dish>> mostCaloricByType


      Map<Type, Optional<Dish>> mostCaloricByType = collect.entrySet()
              .stream()
              .collect(toMap(Map.Entry::getKey,
                      typeListEntry -> typeListEntry.getValue()
                              .stream()
                              .max(comparing(Dish::getCalories))));


   }

}
