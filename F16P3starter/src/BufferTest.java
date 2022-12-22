import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import student.TestCase;

/**
 * Test class for Buffer.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.10.14
 */
public class BufferTest extends TestCase {
   // private Buffer buffer;
    private ByteFile bFile;

    /**
     * Sets up test class.
     * 
     * @throws IOException
     */
    public void setUp() throws IOException {

        bFile = new ByteFile("testFile.bin", 3, 3);
    }


    /**
     * Ensures loadBytes works correctly for specified block.
     * 
     * @throws IOException
     */
    public void testLoadBytes() throws IOException {

        Buffer buffer = bFile.getPool().acquireBuffer(3);
        // System.out.println(buffer.toString());
    }


    /**
     * Tests that both bytes and records update.
     * 
     * @throws IOException
     */
    public void testBytesAndRecords() throws IOException {
        Buffer buffer = new Buffer(bFile, 0);
        Record test = new Record(6, 7);
// System.out.println(buffer.getRecords()[0].toString());
        buffer.addRecord(0, test);
// System.out.println((byte)buffer.getBytes()[0]);
// System.out.println(buffer.getRecords()[0].toString());

    }


    /**
     * Tests if arrays update correctly.
     * 
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void testArr() throws FileNotFoundException, IOException {
        byte[] bytes = new byte[10];

        bytes[0] = (byte)5;
        //byte[] bytesCopy = bytes;
// System.out.println(bytes[0]);
// System.out.println("copy: " + bytesCopy[0]);
        bFile.getFile().seek(0);
        bFile.getFile().read(bytes);
// System.out.println(bytes[0]);
// System.out.println("copy: " + bytesCopy[0]);

    }


    /**
     * Tests to see how bytebuffer works.
     * 
     * @throws IOException
     */
    public void testBuffers() throws IOException {
        ByteFile byteFile = new ByteFile("testFile.bin", 1, 2);
        byte[] basic = new byte[ByteFile.BYTES_PER_BLOCK];
        ByteBuffer bb = ByteBuffer.wrap(basic);
        byteFile.getFile().read(basic);
        // System.out.println(basic[0]);
        int key = bb.getShort(0);
        int val = bb.getShort(2);
        // Record build = new Record

        //System.out.println(byteFile.testDump(bb.array()));
        System.out.println(key);
        System.out.println(val);
    }

}
