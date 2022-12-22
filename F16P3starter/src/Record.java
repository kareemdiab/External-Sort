
import java.nio.ByteBuffer;

/**
 * Record class to hold the actual pieces of data and will be used for comparing
 * with sort.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.10.23
 *
 */
public class Record implements Comparable<Record> {

    /**
     * Record's size in bytes.
     */
    public static final int SIZE_IN_BYTES = 4;
    /**
     * Index of key within the four bytes.
     */
    public static final int BYTE_INDEX_KEY = 0;
    /**
     * Index of value within the four bytes.
     */
    public static final int BYTE_INDEX_VALUE = 2;
    /**
     * Maximum value of key.
     */
    public static final int KEY_MAXIMUM = 30000;

    // This tiny ByteBuffer holds both the key and value as bytes
    private ByteBuffer bb;
    private boolean dirty;

    // makes a record and its backing ByteBuffer, useful for testing
    /**
     * Makes a record and its backing buffer.
     * 
     * @param key
     *            Key to make record with.
     * @param val
     *            Value to make record with.
     */
    public Record(short key, short val) {
        bb = ByteBuffer.allocate(SIZE_IN_BYTES);
        bb.putShort(BYTE_INDEX_KEY, key);
        bb.putShort(BYTE_INDEX_VALUE, val);
        dirty = false;
    }


    /**
     * Gets the key of the record.
     * 
     * @return Returns the key.
     */
    public short getKey() {
        return bb.getShort(BYTE_INDEX_KEY);
    }


    /**
     * Gets the value of the record.
     * 
     * @return Value.
     */
    public short getValue() {
        return bb.getShort(BYTE_INDEX_VALUE);
    }


    // makes quick testing even easier
    /**
     * Constructs with key and value.
     * 
     * @param key
     *            Key
     * @param val
     *            Value
     */
    public Record(int key, int val) {
        this((short)key, (short)val);
    }


    // Constructs using a given byte array. Does NOT copy but refers
    /**
     * Constructs using given byte array. Does NOT copy but refers.
     * 
     * @param bytes
     *            Byte array.
     */
    public Record(byte[] bytes) {
        bb = ByteBuffer.wrap(bytes);
    }


    // Makes a whole array of records that are backed by the given byte array
    // Caution: Changing the array will change records and vice versa!
    /**
     * Converts bytes to array of records.
     * 
     * @param binaryData
     *            byte array.
     * @return Returns the new record array.
     */
    public static Record[] toRecArray(byte[] binaryData) {
        int numRecs = binaryData.length / SIZE_IN_BYTES;
        Record[] recs = new Record[numRecs];
        for (int i = 0; i < recs.length; i++) {
            int byteOffset = i * SIZE_IN_BYTES;
            ByteBuffer bb = ByteBuffer.wrap(binaryData, byteOffset,
                SIZE_IN_BYTES);
            recs[i] = new Record(bb.slice());
        }
        return recs;
    }


    // Constructs using a given byte buffer. Does NOT copy but refers
    /**
     * Constructs using a given byte buffer.
     * 
     * @param bb
     *            ByteBuffer to construct with.
     */
    private Record(ByteBuffer bb) {
        this.bb = bb;
    }


    /**
     * Marks record as dirty.
     */
    public void markDirtyRecord() {
        dirty = true;
    }


    /**
     * Checks if record is dirty.
     * 
     * @return True if dirty.
     */
    public boolean isDirty() {
        return dirty;
    }


    // copies the contents of another record. This is a DEEP copy.
    /**
     * Sets record value to another given record.
     * 
     * @param other
     *            Other record.
     */
    public void setTo(Record other) {
        bb.putShort(BYTE_INDEX_KEY, other.getKey());
        bb.putShort(BYTE_INDEX_VALUE, other.getValue());
    }


    /**
     * Returns byte buffer.
     * 
     * @return ByteBuffer.
     */
    public ByteBuffer getBuffer() {
        return bb;
    }


    /**
     * Compares two records.
     * 
     * @param o
     *            Other record to compare to.
     * @return 1 if greater than, 0 if equal, -1 if less than.
     */
    @Override
    public int compareTo(Record o) {
        return Short.compare(this.getKey(), o.getKey());
    }


    // A nice overview of the Record's contents.
    /**
     * ToString method for the record.
     * 
     * @return String representation.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("Record: (");
        sb.append(this.getKey());
        sb.append(", ");
        sb.append(this.getValue());
        sb.append(")");
        return sb.toString();
    }

}
