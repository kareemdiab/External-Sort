import java.io.File;
import java.io.IOException;
import student.TestCase;
import java.nio.*;

/**
 * Test class for methods in ByteFile.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.10.14
 *
 */
public class ByteFileTest extends TestCase {

// private ByteFile bFile;
// private ByteFile bFile2;

    /**
     * Sets up test class.
     * 
     * @throws IOException
     */
    public void setUp() throws IOException {
// bFile = new ByteFile("testFile.bin", 2, 2);
//
// bFile2 = new ByteFile("test.bin", 3, 2);

    }


    /**
     * Tests the write random records file.
     * 
     * @throws IOException
     */
    public void testRandomFile() throws IOException {
        ByteFile bFile = new ByteFile("testFile.bin", 2, 2);
        // assertFalse(bFile.isSorted());
        System.out.println(bFile.dump());
    }


    /**
     * Ensures that writeBuffer properly rewrites Buffer back into file.
     * 
     * @throws IOException
     */
    public void testWriteBuffer() throws IOException {
        // System.out.println(bFile.dump());

        // System.out.println(buffer.toString());
        ByteFile bFile = new ByteFile("testFile.bin", 2, 2);
        bFile.sortFile();

        // bFile.writeBuffer(0, buffer.getRecords());
        assertTrue(bFile.isSorted());
        System.out.println(bFile.dump());
        // assertTrue(bFile.isSorted());
        // System.out.println(buffer.toString());

    }


    /**
     * Tests when first and second buffers are written back to.
     * 
     * @throws IOException
     */
    public void testWriteSecondBuffer() throws IOException {
        ByteFile bFile = new ByteFile("testFile.bin", 2, 2);
// bFile.writeRandomRecords();

        bFile.sortFile();
// Buffer buffer = bFile.getPool().getList().get(1);
// buffer.writeBack();

        System.out.println(bFile.dump());
        // assertFalse(bFile.isSorted());
// buffer = bFile.getPool().getList().get(0);
// buffer.writeBack();
        assertTrue(bFile.isSorted());
        System.out.println(bFile.dump());
    }


    /**
     * Tests sort method.
     * 
     * @throws IOException
     */
    public void testSort() throws IOException {
        ByteFile bFile2 = new ByteFile("test.bin", 3, 2);
        bFile2.sortFile();

        System.out.println(bFile2.dump());

    }


    /**
     * Tests getNumBlocks method.
     * 
     * @throws IOException
     */
    public void testGetNumBlocks() throws IOException {
        ByteFile bFile2 = new ByteFile("test.bin", 3, 2);
        assertEquals(bFile2.getNumBlocks(), 2);
    }


    /**
     * Test appendStats method.
     * 
     * @throws IOException
     */
    public void testAppend() throws IOException {
        ByteFile bFile = new ByteFile("testFile.bin", 2, 2);
        bFile.appendStatsFile("fakeStats", "testStats.txt");

    }


    /**
     * See how arrays work with read method.
     * 
     * @throws IOException
     */
    public void testArr() throws IOException {
        byte[] arr = new byte[40];
        ByteFile bFile = new ByteFile("testFile.bin", 4, 4);

        bFile.getFile().seek(1000);
        // bFile.getFile().read(arr);
        Record[] records = Record.toRecArray(arr);
        byte[] arr2 = arr;
        bFile.getFile().seek(10);
        bFile.getFile().read(arr2);
        Record[] records2 = Record.toRecArray(arr2);
// bFile.getFile().seek(10);
// bFile.getFile().read(arr2);

        // byte[] copy = arr;
        System.out.println("contents1: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(records[i]);
        }
        System.out.println("contents2: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(records2[i]);
        }
    }

}
