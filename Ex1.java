
/**
 * Write a description of class EX1 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 package assignments.ex1;
/**
 * This class represents a simple solution for Ex1.
 * As defined here: https://docs.google.com/document/d/1AJ9wtnL1qdEs4DAKqBlO1bXCM6r6GJ_J/r/edit/edit
 * In this assignment, we will design a number formatting converter and calculator.
 * In general, we will use Strings as numbers over basis of binary till Hexa.
 * [2-16], 10-16 are represented by A,B,..G.
 * The general representation of the numbers is as a String with the following format:
 * <number><b><base> e.g., “135bA” (i.e., “135”, as 10 is the default base), “100111b2”, “12345b6”,”012b5”, “123bG”, “EFbG”.
 * The following are NOT in the format (not a valid number):
 * “b2”, “0b1”, “123b”, “1234b11”, “3b3”, “-3b5”, “3 b4”, “GbG”, "", null,
 * You should implement the following static functions:
 */
public class Ex1 {
        /**
         * Convert the given number (num) to a decimal representation (as int).
         * It the given number is not in a valid format returns -1.
         * @param num a String representing a number in basis [2,16]
         * @return
         */
    public static int number2Int(String num) {
    int ans = -1;
    // add your code here
                               
    if (num == null || num.isEmpty()) { 
                return -1;
            }    
    int base;
    if (num.startsWith("0b")) {
        base = 2;
        num = num.substring(2);
    } else if (num.startsWith("0x")) {
        base = 16;
        num = num.substring(2);
    } else {
        return -1; // אם הבסיס לא מוגדר
    }
     for (int i = 0; i < num.length(); i++) {
        char c = num.charAt(i);
        int value;
        if (c >= '0' && c <= '9') {
            value = c - '0'; // המרה לתו
        } else if (c >= 'a' && c <= 'f') {
            value = c - 'a' + 10; // המרה לתו בעבור בסיס 16
        } else if (c >= 'A' && c <= 'F') {
            value = c - 'A' + 10; // המרה לתו בעבור בסיס 16 (אותיות גדולות)
        } else {
            return -1; // תו לא חוקי
        }

     
        if (value >= base) {
            return -1; // ערך לא חוקי בבסיס הנבחר
        }
        ans = ans * base + value;
    }
    return ans;
}
    
    
        /**
         * This static function checks if the given String (g) is in a valid "number" format.
         * @param a a String representing a number
         * @return true iff the given String is in a number format
         */
public static boolean isNumber(String a) {
            boolean ans = true;
            // add your code here
              if (a == null || a.isEmpty()) {
        return false;
    }

   
    int indexB = a.indexOf('b');
    int length = a.length();

   
    if (indexB <= 0 || indexB == length - 1) {
       
        if (indexB == -1) {
            ans = a.matches("\\d+"); // בודק אם המחרוזת מורכבת רק ממספרים
            return ans;
        }
    }
      String beforeB = a.substring(0, indexB);
    String afterB = a.substring(indexB + 1);

  
    if (!beforeB.matches("\\d+")) {
        ans = false; // לא חוקי אם לפני 'b' יש תווים שאינם מספריים
    }

   
    if (!afterB.matches("\\d+")) {
        ans = false; // לא חוקי אם אחרי 'b' יש תווים שאינם מספריים
    }       
            return ans;
        }

    /**
         * Calculate the number representation (in basis base)
         * of the given natural number (represented as an integer).
         * If num<0 or base is not in [2,16] the function should return "" (the empty String).
         * @param num the natural number (include 0).
         * @param base the basis [2,16]
         * @return a String representing a number (in base) equals to num, or an empty String (in case of wrong input).
         */
        public static String int2Number(int num, int base) {
            String ans = "";
            // add your code here
                 if (num < 0 || base < 2 || base > 16) {
            return ""; // מחזירים מספר ריק
        }

        if (num == 0) {
            return "0"; // אם המספר הוא 0, מחזירים "0"
        }
           while (num > 0) {
            int remainder = num % base; // שארית מחלוקת
            if (remainder < 10) {
                ans = remainder + ans; // הוספת הספרה הרגילה
            } else {
                ans = (char) ('A' + (remainder - 10)) + ans; // הוספת אות באנגלית עבור 10-15
              }
            num /= base; // מחלקים את המספר בבסיס
        }
        return ans;

        }

        /**
         * Checks if the two numbers have the same value.
         * @param n1 first number
         * @param n2 second number
         * @return true iff the two numbers have the same values.
         */
    public static boolean equals(String n1, String n2) {
            boolean ans = true;
              if (n1 == null || n2 == null) {
            return n1 == n2; // אם שניהם null, מחזירים true, אחרת false
        }

    
        if (n1.length() != n2.length()) {
            return false; 
        }    
        for (int i = 0; i < n1.length(); i++) {
            if (n1.charAt(i) != n2.charAt(i)) {
                return false;
            }    
            
        }
        return ans;
        }

    /**
         * This static function search for the array index with the largest number (in value).
         * In case there are more than one maximum - returns the first index.
         * Note: you can assume that the array is not null and is not empty, yet it may contain null or none-valid numbers (with value -1).
         * @param arr an array of numbers
         * @return the index in the array in with the largest number (in value).
         *
         */
        
        public static int maxIndex(String[] arr) {
            int ans = 0;
             int maxValue = Integer.MIN_VALUE; // המקסימום ההתחלתי
                for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && !arr[i].equals("-1")) {
                     boolean isNumber = true; // נניח שזה מספר חוקי
                int length = arr[i].length();
                
           
                for (int j = 0; j < length; j++) {
                    char ch = arr[i].charAt(j);
                    if (!Character.isDigit(ch)) {
                       
                        if (!(ch == '-' && j == 0 && length > 1)) {
                            isNumber = false; // לא מספר חוקי
                        }
                    }
                }
                    if (isNumber) {
                    int currentValue = Integer.parseInt(arr[i]); // המרת המיתר למספר
                    if (currentValue > maxValue) { // אם מצאנו ערך גדול יותר
                        maxValue = currentValue; // מעדכנים את המקסימום
                        ans = i; 
                                            }
                }
            }
        }

        
    

            ////////////////////
            return ans;
        }
}
