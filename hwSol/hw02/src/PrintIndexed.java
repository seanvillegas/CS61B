public class PrintIndexed {
   /**
     * Prints each character of a given string followed by the reverse of its index.
     * Example: printIndexed("hello") -> h4e3l2l1o0
     */
   public static void printIndexed(String s) {
      int wordLen = s.length();
      int idxOneLess = wordLen - 1;

      for (int idx=0; idx < wordLen; idx++) {
         char iter = s.charAt(idx);
         String compatible = String.valueOf(iter);
         int num = idxOneLess - idx;
         System.out.print(compatible + num);
      }
      System.out.println();
   }

   public static void main(String[] args) {
      printIndexed("hello");
      printIndexed("cat"); // should print c2a1t0
   }
}