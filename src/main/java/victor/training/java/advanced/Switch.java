package victor.training.java.advanced;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

 enum MovieType {
   REGULAR(MovieService::priceForRegular),
   NEW_RELEASE(MovieService::priceForNewRelease),
   CHILDREN(MovieService::priceForChildren);

   public final BiFunction<MovieService,Integer,Integer> moviePriceCalAlgo;


   MovieType(BiFunction<MovieService, Integer, Integer> moviePriceCalAlgo) {
      this.moviePriceCalAlgo = moviePriceCalAlgo;
   }
}


@Service
public class Switch {
   @Value("${children.price}")
   private int childrenPrice = 5; // pretend Spring is ON

   // @see tests
   public int computePrice(MovieType type, int days) {
       return new MovieService().calculatePriceByType(type, days);
   }

   public void auditDelayReturn(MovieType movieType, int delayDays) {
       switch (movieType) {
           case REGULAR ->
                   System.out.println("Regular delayed by " + delayDays);
           case NEW_RELEASE ->
                   System.out.println("CRITICAL: new release return delayed by " + delayDays);
       }
   }
}


class MySpecialCaseNotFoundException extends RuntimeException {
}