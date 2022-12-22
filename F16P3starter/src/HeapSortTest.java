
import org.junit.Test;

import student.TestCase;

/**
 * Test class for heapsort.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.10.24
 *
 */
public class HeapSortTest extends TestCase {

    /**
     * An artificial test to get initial coverage for the
     * main method. Delete or modify this test.
     * 
     * @throws Exception
     */
    @Test
    public void testMain() throws Exception {
        HeapSort dum = new HeapSort();
        assertNotNull(dum);
        HeapSort.main(new String[3]);
        assertEquals(systemOut().getHistory(), ""); // check that nothing was
                                                    // printed out
    }

}
