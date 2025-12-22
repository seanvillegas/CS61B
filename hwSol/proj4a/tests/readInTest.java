import edu.princeton.cs.algs4.In;

public class readInTest {
    public static void main(String[] args) {
        In in = new In("./src/data/word_history_size3.csv");
        int i = 0;

        while(!in.isEmpty()) {
            ++i;
            String nextLine = in.readLine();
            System.out.print("Line " + i + " is: ");
            System.out.println(nextLine);
            System.out.print("After splitting on tab characters, the first word is: ");
            String[] splitLine = nextLine.split("\t");
            System.out.println(splitLine[0]);
            System.out.println("We only care about the above and next data: ");
            System.out.println(splitLine[1]);
            System.out.println(splitLine[2]);

        }

    }
}

