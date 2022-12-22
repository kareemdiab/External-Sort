import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Random;
import student.TestableRandom;

/**
 * This class will represent the buffers within the buffer pool.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.10.13
 *
 */
public class Buffer {

    private ByteFile file;
    private byte[] bytes;
    private int block;
    private Record[] records;
    private boolean dirty;

    private ByteBuffer bb;

    /**
     * Buffer constructor. Loads bytes into byte array.
     * 
     * @param bFile
     *            Name of ByteFile.
     * @param b
     *            Specified block for buffer
     * @throws IOException
     *             Any input/output errors.
     */
    public Buffer(ByteFile bFile, int b) throws IOException {
        dirty = false;
        file = bFile;
        this.block = b;

        bytes = new byte[ByteFile.BYTES_PER_BLOCK];
        

        loadBytes();

        bb = ByteBuffer.wrap(bytes);

    }


//    /**
//     * Second Constructor for buffer using given byte array and bytebuffer.
//     * 
//     * @param bufferArr
//     *            bytes to construct with.
//     * 
//     */
//    public Buffer(byte[] bufferArr) {
//        bytes = bufferArr;
//        bb = ByteBuffer.wrap(bufferArr);
//    }


    /**
     * Finds specified block for buffer and loads in bytes to the array.
     * 
     * @param block
     * @throws IOException
     */
    public void loadBytes() throws IOException {

        file.getFile().seek(ByteFile.BYTES_PER_BLOCK * block);
        file.getFile().read(bytes, 0, ByteFile.BYTES_PER_BLOCK);

        // records = Record.toRecArray(bytes);

    }


    /**
     * Returns array of bytes.
     * 
     * @return byte array.
     */
    public byte[] getBytes() {
        return bytes;
    }


    /**
     * Returns array of records.
     * 
     * @return Records
     */
    public Record[] getRecords() {
        return records;
    }




    /**
     * Tells block that buffer is in.
     * 
     * @return Returns block number.
     */
    public int getBlock() {
        return this.block;
    }


    /**
     * Adds record to byte array.
     * 
     * @param index
     *            Index to insert record
     * @param record
     *            Record to add.
     */
    public void addRecord(int index, Record record) {
        int n = index - (block * ByteFile.RECORDS_PER_BLOCK);
// bytes[n] = (byte)record.getKey();

        // records[n] = record;

        short key = (short)record.getKey();
        short val = (short)record.getValue();

        bb.putShort(n * 4, key);
        bb.putShort((n * 4) + 2, val);
        // records[n] = record;

        markDirty();
        // pool.moveToFront(this);

    }


    /**
     * Tells if buffer is dirty or not.
     * 
     * @return True if dirty.
     */
    public boolean isDirty() {
        return dirty;
    }


    /**
     * Gets record from specified index.
     * 
     * @param index
     *            Index to retrieve record from
     * @return Returns record.
     */
    public Record getRecord(int index) {
        index = index - (block * ByteFile.RECORDS_PER_BLOCK);

        int n = index * 4;
        int key = bb.getShort(n);
        int value = bb.getShort(n + 2);
        Record record = new Record(key, value);
        // System.out.println(records[index]);

        // pool.moveToFront(this);
        // markDirty();

        return record;
        // return records[index];

    }


    /**
     * Sets dirty bit.
     */
    public void markDirty() {
        this.dirty = true;
    }


    /**
     * Writes back into file if dirty.
     * 
     * @throws IOException
     */
    public void writeBack() throws IOException {
        if (this.isDirty()) {
            // bb.clear();
            // file.writeBuffer(block, records);
            file.writeBytes(block, bb.array());
        }

        // System.arrayco

    }
}
