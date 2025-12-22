public class StackClient {
    static Stack flipMe = new Stack();

    /** Returns a version of the stack that is flipped. My version iterates through s destructively
     * popping the item and pushing it to top of stack to reverse order. Can be solved non-destructively with
     * .getItem, but I was too lazy to do that
     * */
    public static Stack flipped(Stack s) {
        int listLen = s.size();

        for (int i = 0; i < listLen; i++) {
            int capture = s.pop();
            flipMe.push(capture);
        }
        return flipMe;
    }
}
