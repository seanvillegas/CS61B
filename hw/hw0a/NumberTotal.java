/**
 * Exercise sourced from Practice-It by the University of Washington.
 * Original problems available at: https://practiceit.cs.washington.edu/
 *
 * @author Erik Kizior
 */

// TODO: What is the output of the following program?
public class NumberTotal {
    public static void main(String[] args) {
        int total = 25;
        for (int number = 1; number <= (total / 2); number++) {
            total = total - number;
            System.out.println(total + " " + number);
        }
    }
}

/* Before running the code, type your answer below.

TODO: Write output here

// Where I went Wrong;
    - We check the condition first, then we execute the suite, printing the updated total after a successful check of our condition
    * 25 1
    * 24 2
    * 10 3
    * 7 4
    * 3 5

Then, click the green play button to check your work.

ANSWER
        24 1
        22 2
        19 3
        15 4
        10 5

*/
