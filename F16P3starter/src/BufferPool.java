
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/**
 * This class will represent the buffer pool for the heap.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.10.12
 *
 */
public class BufferPool {
    private int cacheMisses;
    private int cacheHits;
    private MaxLinkedList<Buffer> pool;
    private MaxHeap<Record> heap;
    private int buffers;
    private int diskReads;
  
    //private byte[] bytes;

    private ByteFile byteFile;

    /**
     * BufferPool constructor.
     * 
     * @param bFile
     *            File reader.
     * @param blockNum
     *            Number of blocks in file.
     * @param bufferAmnt
     *            Number of buffers in buffer pool.
     * @throws IOException
     */
    public BufferPool(ByteFile bFile, int blockNum, int bufferAmnt)
        throws IOException {
        byteFile = bFile;
        this.pool = new MaxLinkedList<Buffer>(bufferAmnt);
      
        this.buffers = bufferAmnt;

        //this.bytes = new byte[ByteFile.BYTES_PER_BLOCK];
        this.cacheMisses = 0;
        this.cacheHits = 0;
        this.diskReads = 0;
        
     
        heap = new MaxHeap<Record>(blockNum, this);

    }


    /**
     * Inserts buffer into specified MaxLinkedList.
     * 
     * @param buff
     *            Buffer to be inserted.
     * @throws IOException
     */
    public void insert(Buffer buff) throws IOException {
        pool.insert(buff);
    }


    /**
     * Returns linked list holding input buffers.
     * 
     * @return MaxLinkedList.
     */
    public MaxLinkedList<Buffer> getList() {
        return this.pool;
    }


    /**
     * Return a pointer to the buffer's data array (without reading from disk)
     * 
     * @param block
     *            Block to get data from.
     * @return Returns byte array of data.
     */
    public Buffer getDataPointer(int block) {
        return pool.get(block);
    }

// /**
// * Get data pointer from disk when it is not in buffer pool.
// *
// * @return Returns pointer to data.
// * @throws IOException
// */
// public Buffer getDataFromDisk(int index) throws IOException {
// cacheMisses++;
// diskReads++;
// int block = getBlock(index);
// byteFile.getFile().seek(ByteFile.BYTES_PER_BLOCK * block);
// byteFile.getFile().read(bytes, 0, ByteFile.BYTES_PER_BLOCK);
//
// Buffer buff = new Buffer(bytes);
// insert(buff);
//
// return buff;
// }


    /**
     * Get data pointer from disk when it is not in buffer pool.
     * 
     * @param index Index to get buffer for.
     * @return Returns pointer to data.
     * @throws IOException
     */
    public Buffer getDataFromDisk(int index) throws IOException {
        cacheMisses++;
        diskReads++;
        int block = getBlock(index);

        Buffer buff = new Buffer(byteFile, block);
        insert(buff);

        return buff;
    }


    /**
     * Return the number of cacheMisses after heap building and sorting.
     * 
     * @return Returns number of cache misses.
     */
    public int getCacheMisses() {
        return this.cacheMisses;
    }


    /**
     * Return the number of cacheHits after heap building and sorting.
     * 
     * @return Returns the number of cache hits.
     */
    public int getCacheHits() {
        return this.cacheHits;
    }


    /**
     * Returns the number of disk reads.
     * 
     * @return Return number of disk reads.
     */
    public int getDiskReads() {
        return this.diskReads;
    }
    



    /**
     * Returns block for specified index.
     * 
     * @param index
     *            Index for block.
     * @return Returns block number.
     */
    public int getBlock(int index) {
        return index / ByteFile.RECORDS_PER_BLOCK;
    }


    /**
     * Acquires buffer, preferably from buffer pool but will read from disk if
     * necessary.
     * 
     * @param index
     *            Index for buffer.
     * @return Returns buffer.
     * @throws IOException
     */
    public Buffer acquireBuffer(int index) throws IOException {

        int block = getBlock(index);
        Buffer buf = getDataPointer(block);
        if (buf == null) {
            buf = getDataFromDisk(index);
        }
        else {
            pool.moveToFront(buf);
            cacheHits++;
        }
        return buf;
    }


    /**
     * Instructs MaxHeap to sort file.
     * 
     * @throws IOException
     */
    public void sort() throws IOException {
        heap.heapSort();
    }


    /**
     * Returns max heap.
     * 
     * @return Returns heap.
     */
    public MaxHeap<Record> getHeap() {
        return heap;
    }


    /**
     * Flushes out all the remaining buffers in the max linked list back into
     * file.
     * 
     * @return Returns the number of disk writes during flush.
     * @throws IOException
     */
    public int flush() throws IOException {
        int diskWrites = 0;
        for (int i = 0; i < buffers; i++) {
            diskWrites++;
            pool.removeLastNode();
        }
        return diskWrites;
    }



}
