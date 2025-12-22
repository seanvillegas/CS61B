package adventure;

import common.IntList;
import edu.princeton.cs.algs4.In;

import java.util.Map;
import java.util.TreeMap;

public class PalindromeStage implements AdventureStage {

    private final In in;
    private final Map<String, AdventureStage> responses;

    public PalindromeStage(In in) {
        this.in = in;
        AdventureStage nextStage = new FillerStage(
                "Mm, tasty. Briefly, you wonder if free will is an illusion. You hear some people talking " +
                        "about a machine in Soda and decide to check it out.",
                Map.of("go", new MachineStage(in))
        );
        this.responses = new TreeMap<>(Map.of(
                "va cafe", nextStage,
                "hearst food court", nextStage
        ));
    }

    @Override
    public void playStage() {
        System.out.println("""
                The Woz is a cool name, much better than "Soda 430". Soda 606 is a neat, symmetric conference room,
                like its number. We could rename The Woz to be palindromic, but it sounds so cool! Why not change the
                room number instead?
                (Give a palindromic room number.)
                """);
        while (true) {
            String input = in.readLine();
            // if (input == null) return;                 // extra check added
            while (!AdventureUtils.isInt(input)) {
                // here we enter the infinite loop, because the input expects an int but gets a string
                System.out.println("Please enter a valid integer.");
                // if (input == null) return;             //  extra check added
                input = this.in.readLine();
            }

            IntList numLst = digitsToIntList(input);
            IntList reversedLst = reverseList(numLst);

            if (numLst.equals(reversedLst)) {
                System.out.println("Wow, nice room number!");
                break;
            }

            System.out.println("That's not a palindrome! Try again.");
        }
    }

    @Override
    public String nextStagePrompt() {
        return "Hmm, you're getting hungry. Where do you want to go?";
    }

    @Override
    public Map<String, AdventureStage> getResponses() {
        return responses;
    }

    /** Returns a new IntList with the contents of the original IntList in reverse order.*/
    private static IntList reverseList(IntList l) {
        IntList reversed = null;
        // 4th bug in reversed, we should increment L tell it is null, and grab its first in that object too instead of
            // stopping right at L.rest (before tail)
        while (l != null) {
            reversed = new IntList(l.first, reversed);
            l = l.rest;
        }
        return reversed;
    }

    /**
     * Given an input string of digits, converts it into an IntList of single-digit ints.
     * For example, the string "606" is converted to 6 -> 0 -> 6.
     */
    private static IntList digitsToIntList(String s) {
        // 3rd Bug is out of bounds. You need to decrement i, and then use index length instead of normal length
        int actualLen = s.length() - 1;
        int[] a = new int[s.length()];
        for (int i = actualLen; i >= 0; i--) {
            a[actualLen - i] = Character.getNumericValue(s.charAt(i));
        }
        return IntList.of(a);
    }


}
