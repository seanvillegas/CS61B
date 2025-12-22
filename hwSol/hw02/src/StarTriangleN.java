public class StarTriangleN {
   /**
     * Prints a right-aligned triangle of stars ('*') with N lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle(int N) {
      for (int row = 1; row <= N; row++) {

         for (int space = 1; space <= N - row; space++) {
            System.out.print(" ");
         }

         for (int star = 1; star <= row; star++) {
            System.out.print("*");
         }
         System.out.println();
      }
   }
   
   public static void main(String[] args) {
      starTriangle(7);
   }
}