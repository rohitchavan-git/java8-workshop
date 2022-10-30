package victor.training.java.advanced;

import victor.training.java.advanced.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SplitLoopToStream {

   // see tests
   public void bossLevel(List<User> users) {

      List<String> allUsernames = users.stream()
              .map(User::getUsername)
              .collect(Collectors.toList());
      int missingLanguageCount = (int) users.stream()
              .filter(user -> user.getLanguage() == null)
              .count();
      users.stream()
              .filter(user -> !user.getActive())
              .forEach(user -> insertInactiveAlert(user.getId()));
      int totalTickets = users.stream()
              .mapToInt(User::getTicketsRaised)
              .sum();
      double noLanguagePercent = 100d * missingLanguageCount / users.size();
      System.out.printf("No language set  = %.2f%%\n", noLanguagePercent);
      System.out.printf("Number of tickets = %d\n", totalTickets);
      writeToFile(allUsernames);
   }

   private void writeToFile(List<String> usernames) {
      System.out.println("Write to file: " + usernames);
      // omitted
   }

   private void insertInactiveAlert(Long userId) {
      System.out.println("Alert inactive user: " + userId);
      // omitted
   }
}

