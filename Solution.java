import java.util.Scanner;

public class Solution {

/**
  * Stores the frequency of the tickets with an id 
  * that contains the same unique digits.
  */
  private static int[] frequency = new int[1024];

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int numberOfLotteryTickets = scanner.nextInt();

    while (numberOfLotteryTickets-- > 0) {
      String ticket_id = scanner.next();

      String binary = extractUniqueDigits_createBinaryForUniqueDigits(ticket_id);
      populate_frequencyArray(binary);
    }
    scanner.close();

    long result = calculate_total_winningPairsOfTickets();
    System.out.println(result);
  }

  /**
  * Checks for unique digits (0 to 9) contained in the ticket id. 
  * On this basis, a new binary id is created for this ticket. 
  *
  * The new binary id, consisting of 10 bits, has a bit value of '1'
  * at an index with a digit that is contained in the ticket id. 
  * Otherwise, the value at the index is '0'.
  *
  * Example:
  *                                  index: 0123456789
  * ticket id: 1114488880 => new binary id: 1100100010                 
  * ticket id: 0123456789 => new binary id: 1111111111
  *
  * @return An integer value of the new binary id.
  */
  private static String extractUniqueDigits_createBinaryForUniqueDigits(String ticket_id) {
    
    StringBuilder binary = new StringBuilder();
    for (int i = 0; i <= 9; i++) {
      String digit = Integer.toString(i);
      if (ticket_id.contains(digit)) {
        binary.append("1");
      } else {
        binary.append("0");
      }
    }
    return binary.toString();
  }

  private static void populate_frequencyArray(String binary) {
    int uniqueCombination = Integer.parseInt(binary, 2);
    frequency[uniqueCombination]++;
  }
 
  /**
  * Calucaltes the total number of winning pairs of lottery tickets.
  *
  * Since the new binary id has a maximum value of 1023,
  * if the bitwise 'OR' between two integers of the new binary id
  * (represented by the array indexes) equals 1023, then 
  * it is a winning pair of tickets.
  *
  * The number of winning pairs of tickets with an id containing
  * all digits from 0 to 9 (tickets at an array index of 1023), has to be 
  * calculated on the basis of the formula for the total possible ways 
  * of selecting 'k' items from a collection of 'n' items, 
  * where the order of selection does not matter: n!/(k!*(n-k)!) 
  *
  * return A long integer, representing total number of winning pairs.
  */
  private static long calculate_total_winningPairsOfTickets() {
  
    long result = 0;
    for (int i = 1; i < frequency.length; i++) {
      if (frequency[i] == 0) {
        continue;
      }

      for (int j = i + 1; j < frequency.length; j++) {
        if ((i | j) == 1023) {
          result = result + (long) frequency[i] * frequency[j];
        }
      }
    }

    int freq_lastIndex = frequency[frequency.length - 1];
    result = result + ((long) freq_lastIndex * (freq_lastIndex - 1)) / 2;

    return result;
  }
}
