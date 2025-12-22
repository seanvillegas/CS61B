import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void testSizeandIsEmpty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("sam");
        lld1.addLast("I");
        assertThat(lld1.size() == 2);
        lld1.addFirst("Am");
        assertThat(lld1.size() == 3);

        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
        assertThat(lld2.isEmpty());
        assertThat(lld2.size() == 0);
    }

    @Test
    public void getTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         // OOB check
         assertThat(lld1.get(20) == null);
         // OOB negative indexing check
         assertThat(lld1.get(-1) == null);

         Deque61B<Object> lldMixed = new LinkedListDeque61B<>();
         lldMixed.addFirst(1);
         lldMixed.addLast("twenty");
         lldMixed.addFirst("hug");
         lldMixed.addLast(2048);
         lldMixed.addLast("IamLast");
         assertEquals("twenty", lldMixed.get(2));
         assertEquals(null, lldMixed.get(-1));
    }

    @Test
    public void getRecursiveTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.getRecursive(20) == null);
        assertThat(lld1.getRecursive(-1) == null);

        Deque61B lldMixed = new LinkedListDeque61B<>();
        lldMixed.addFirst(1);
        lldMixed.addLast("twenty");
        lldMixed.addFirst("hug");
        lldMixed.addLast(2048);

        assertEquals(2048, lldMixed.getRecursive(3));
        assertEquals("hug", lldMixed.getRecursive(0));

    }

    @Test
    public void removeFirster() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();
         lld1.addFirst("Kung");
         lld1.addFirst("Ian");
         lld1.addFirst("Expelled");
         assertThat(lld1.toList()).containsExactly("Expelled", "Ian", "Kung").inOrder();
         lld1.removeFirst();
         assertThat(lld1.toList()).containsExactly("Ian", "Kung").inOrder();
         assertEquals(2, lld1.size());
    }

    @Test
    public void removeLaster() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst("Kung");
        lld1.addFirst("Ian");
        lld1.addFirst("Expelled");
        assertThat(lld1.toList()).containsExactly("Expelled", "Ian", "Kung").inOrder();

        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly("Expelled", "Ian").inOrder();
        assertEquals(2, lld1.size());

    }

    // the annoying tests that they run on my tests
    @Test
    public void toListEmpty() {
         Deque61B<Integer> emptyCheck = new LinkedListDeque61B<>();
         assertThat(emptyCheck.toList()).containsExactly();
    }

    @Test
    public void addFirstAfterRemoveEmpty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst("Kung");
        lld1.addFirst("Ian");
        lld1.addFirst("Expelled");

        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        assertEquals(0, lld1.size());

        lld1.addFirst("Add First Test");
        assertEquals(1, lld1.size());
    }

    @Test
    public void addLastAfterRemoveEmpty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("Kung");
        lld1.addLast("Ian");
        lld1.addLast("Expelled");

        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        assertEquals(0, lld1.size());

        lld1.addLast("Add Last Test");
        assertEquals(1, lld1.size());
    }

    @Test
    public void removeFirstToEmpty() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();
         lld1.addLast("Kung");
         lld1.addLast("Ian");
         lld1.addLast("Expelled");
         lld1.removeFirst();
         lld1.removeFirst();
         lld1.removeFirst();
         assertThat(lld1.isEmpty());
         assertEquals(0, lld1.size());
    }

    @Test
    public void removeLastToEmpty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("Kung");
        lld1.addLast("Ian");
        lld1.addLast("Expelled");
        assertEquals(false, lld1.isEmpty());

        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();

        assertThat(lld1.isEmpty());
        assertEquals(0, lld1.size());
    }

    @Test
    public void sizeAfterRemoveEmpty() {
        Deque61B<String> emptyDeckL = new LinkedListDeque61B<>();
        assertEquals(0, emptyDeckL.size());
        emptyDeckL.removeLast();
        // need to not decrement if the size == 0; this creates -1
        assertEquals(0, emptyDeckL.size());
    }

    @Test
    public void sizeAfterRemoveEmptyFirst() {
        Deque61B<String> emptyDeckF = new LinkedListDeque61B<>();
        assertEquals(0, emptyDeckF.size());
        emptyDeckF.removeFirst();
        assertEquals(0, emptyDeckF.size());
    }
}
