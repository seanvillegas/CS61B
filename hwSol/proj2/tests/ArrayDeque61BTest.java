import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayDeque61BTest {

    @Test
    public void testStringMethod() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        System.out.println(ad);
    }
    @Test
    public void testEqualDeques61B() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        Deque61B<String> ad2 = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        ad2.addLast("front");
        ad2.addLast("middle");
        ad2.addLast("back");

        assertThat(ad).isEqualTo(ad2);
    }

    @Test
    public void testUnequalDeques61B() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        Deque61B<String> ad2 = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        ad2.addLast("front");
        ad2.addLast("back");
        ad2.addLast("middle");

        assertThat(ad).isNotEqualTo(ad2);
    }

    @Test
    public void wizardIteratorTest() {
        Deque61B ad = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");
        for (Object s : ad) {
            System.out.println(s);
        }
    }

    @Test
    public void addLastTestBasicWithoutToList() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");
        assertThat(ad).containsExactly("front", "middle", "back");
    }


    /**
     * Trigger resize up, then trigger resize down.
     */
    @Test
    public void resizeUptoResizeDown() {
        Deque61B<Object> arrL = new ArrayDeque61B<>();
        for (int i = 1; i <= 9; i++) {
            arrL.addFirst(i);
        }
        assertThat(arrL.toList()).containsExactly(9, 8, 7, 6, 5, 4, 3, 2, 1).inOrder();

        while (!arrL.isEmpty()) {
            arrL.removeFirst();
        }
        arrL.addLast(100);
        arrL.addLast(200);
        assertThat(arrL.toList()).containsExactly(100, 200).inOrder();
    }

    @Test
    public void removeTriggersResizeDown() {
        Deque61B<Integer> arrL = new ArrayDeque61B<>();

        // copyToResize
        for (int i = 1; i <= 16; i++) {
            arrL.addLast(i);
        }

        // resizeDown
        while (arrL.size() > 4) {  // 4/16 = 0.25 threshold
            arrL.removeFirst();
        }
        assertEquals(4, arrL.size());
        // resizeUp
        for (int i = 17; i <= 32; i++) {
            arrL.addLast(i);
        }

        // resizedown from back
        while (arrL.size() > 8) {
            arrL.removeLast();
        }
        assertTrue(arrL.size() <= 8);
    }

    @Test
    public void addFirstAfterRemoveToEmpty() {
        Deque61B arrL = new ArrayDeque61B<>();
        arrL.addFirst("back");
        arrL.addFirst("middle");
        arrL.addFirst("front");
        arrL.removeFirst();
        arrL.removeFirst();
        arrL.removeFirst();
        assertThat(arrL.toList()).containsExactly();
        assertEquals(0, arrL.size());
        arrL.addFirst("McAfee");
        assertThat(arrL.toList()).containsExactly("McAfee");
        assertEquals(1, arrL.size());
    }

    @Test
    public void addLastAfterRemoveToEmpty() {
        Deque61B arrL = new ArrayDeque61B<>();
        arrL.addLast("front");
        arrL.addLast("middle");
        arrL.addLast("back");
        arrL.removeLast();
        arrL.removeLast();
        arrL.removeLast();
        assertThat(arrL.toList()).containsExactly();
        assertEquals(0, arrL.size());
        arrL.addLast("John");
        assertThat(arrL.toList()).containsExactly("John");
        assertEquals(1, arrL.size());
    }
    @Test
    public void addFirstFromEmpty() {
        Deque61B arrL = new ArrayDeque61B<>();
        arrL.addFirst("back");
        arrL.addFirst("middle");
        arrL.addFirst("front");
        assertThat(arrL.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void addLastFromEmpty() {
        Deque61B arrL = new ArrayDeque61B<>();
        arrL.addLast(1);
        arrL.addLast(2);
        arrL.addLast(3);
        arrL.addLast(4);
        assertThat(arrL.toList()).containsExactly(1, 2, 3, 4).inOrder();
    }

    @Test
    public void addFirstNonEmpty() {
        // most redundant tests bru
        Deque61B arrL = new ArrayDeque61B<>();
        arrL.addLast("Kung");
        arrL.addFirst("Ian");
        assertThat(arrL.toList()).containsExactly("Ian", "Kung").inOrder();
    }

    @Test
    public void addLastNonEmpty() {
        // most redundant tests bru
        Deque61B arrL = new ArrayDeque61B<>();
        arrL.addLast("Kung");
        arrL.addFirst("Ian");
        arrL.addLast("expelled");
        assertThat(arrL.toList()).containsExactly("Ian", "Kung", "expelled").inOrder();
    }

    @Test
    public void addFirstTriggerResize() {
        Deque61B arrL = new ArrayDeque61B<>();
        arrL.addFirst(8);
        arrL.addFirst(7);
        arrL.addFirst(6);
        arrL.addFirst(5);
        arrL.addFirst(4);
        arrL.addFirst(3);
        arrL.addFirst(2);
        arrL.addFirst(1);
        // resize method called here
        arrL.addFirst("z");
        assertThat(arrL.toList()).containsExactly("z", 1, 2, 3, 4, 5, 6, 7, 8).inOrder();
    }

    @Test
    public void addLastTriggerResize() {
        Deque61B arrL = new ArrayDeque61B<>();
        arrL.addLast(8);
        arrL.addLast(7);
        arrL.addLast(6);
        arrL.addLast(5);
        arrL.addLast(4);
        arrL.addLast(3);
        arrL.addLast(2);
        arrL.addLast(1);
        // resize method called here
        arrL.addLast("z");
        assertThat(arrL.toList()).containsExactly(8, 7, 6, 5, 4, 3, 2, 1, "z").inOrder();
    }

    @Test
    public<T> void getValid() {
        Deque61B arrL = new ArrayDeque61B<>();
        arrL.addFirst(3);
        arrL.addFirst(2);
        arrL.addFirst(1);
        T element = (T) arrL.get(0);
        assertEquals(1, element);
    }

    /**
     * get should return null when the index is invalid (too large or negative).
     */
    @Test
    public<T> void getOOBLarge() {
        Deque61B arrL = new ArrayDeque61B<>();
        arrL.addFirst(3);
        arrL.addFirst(2);
        arrL.addFirst(1);
        T element = (T) arrL.get(24);
        assertEquals(null, element);
    }

    /**
     * get should return null when the index is invalid (too large or negative).
     */
    @Test
    public<T> void getOOBNeg() {
        Deque61B arrL = new ArrayDeque61B<>();
        arrL.addFirst(3);
        arrL.addFirst(2);
        arrL.addFirst(1);
        T element = (T) arrL.get(-1);
        assertEquals(null, element);
    }

    @Test
    public void isEmptyTrue() {
        Deque61B emptyArr = new ArrayDeque61B<>();
        assertEquals(true, emptyArr.isEmpty());
    }

    @Test
    public void isEmptyFalse() {
        Deque61B usedArr = new ArrayDeque61B<>();
        usedArr.addFirst(3);
        usedArr.addFirst(2);
        usedArr.addFirst(1);
        assertEquals(false, usedArr.isEmpty());

    }

    @Test
    public void toListEmpty() {
        Deque61B emptyArr = new ArrayDeque61B<>();
        assertThat(emptyArr.toList()).containsExactly();
    }

    @Test
    public void toListNonEmpty() {
        Deque61B unEmpty = new ArrayDeque61B<>();
        unEmpty.addFirst(3);
        unEmpty.addFirst(2);
        unEmpty.addFirst(1);
        assertThat(unEmpty.toList()).containsExactly(1, 2, 3);
    }
}
