public class DoubleUp {
   /**
     * Returns a new string where each character of the given string is repeated twice.
     * Example: doubleUp("hello") -> "hheelllloo"
     */
   public static String doubleUp(String s) {
      String addmeOutside = "";
      for (int idx=0; idx < s.length(); idx++) {
         String addme = "";
         char current = s.charAt(idx);
         String compatible = String.valueOf(current);
         addmeOutside += compatible + compatible;
      }
      return addmeOutside;
   }
   
   public static void main(String[] args) {
      String s = doubleUp("hello");
      System.out.println(s);
      System.out.println(doubleUp("cat"));
   }
}