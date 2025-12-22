public class StarTriangle5 {
   /**
     * Prints a right-aligned triangle of stars ('*') with 5 lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle5() {
       for (int row = 1; row <= 5; row++) {

           for (int space = 1; space <= 5 - row; space++) {
               System.out.print(" ");
           }

           for (int star = 1; star <= row; star++) {
               System.out.print("*");
           }
           System.out.println();
       }
   }
   
   public static void main(String[] args) {
      starTriangle5();
   }
}