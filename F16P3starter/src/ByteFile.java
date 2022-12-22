
/**
 * Basic handling of binary data files.
 * Uses a single byte array as a buffer for disc operations
 * Assumes that Records are composed of a short key, and
 * a short value.
 * 
 * @author CS Staff
 * @version 2022 Oct 10
 */
import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Random;
import java.io.FileWriter;
import student.TestableRandom;

/**
 * ByteFile to read from file and create corresponding buffer pool.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.10.18
 *
 */
public class ByteFile {
    private long time;
    private File theFile;
    private int numBlocks;
    private long seed;
    private BufferPool pool;
    private String name;
    private int diskWrites;
    private static RandomAccessFile raf;
    private byte[] basic;

    /**
     * The number of records per block.
     */
    public static final int RECORDS_PER_BLOCK = 1024;
    /**
     * The number of bytes per block.
     */
    public static final int BYTES_PER_BLOCK = RECORDS_PER_BLOCK
        * Record.SIZE_IN_BYTES; // 4096

    /**
     * ByteFile constructor.
     * 
     * @param fileName
     *            Name of file to read from.
     * @param buffers
     *            Number of buffers to use.
     * @param blocksForRandom
     *            Number of blocks to use for random file in testing.
     * @throws IOException
     */
    public ByteFile(String fileName, int buffers, int blocksForRandom)
        throws IOException {
        time = 0;
        diskWrites = -2;
        theFile = new File(fileName);
        basic = new byte[BYTES_PER_BLOCK];

        this.name = fileName;
        this.seed = 1;
        if (blocksForRandom != -1) {
            this.writeRandomRecords(blocksForRandom);
        }
        raf = new RandomAccessFile(theFile, "rws");

        this.numBlocks = getNumBlocks();
        // this.numBlocks = getNumBlocks();
        pool = new BufferPool(this, numBlocks, buffers);
    }


    /**
     * Builds string to append to stats file.
     * 
     * @param timeToSort
     *            Time taken to sort file.
     * @return String representation of sort statistics.
     */
    public String buildStats(long timeToSort) {
        String result = "\n------  STATS ------\n";
        result += "File name: sample/" + name + "\n";
        result += "Cache Hits: " + pool.getCacheHits() + "\n";
        result += "Cache Misses: " + pool.getCacheMisses() + "\n";
        result += "Disk Reads: " + pool.getDiskReads() + "\n";
        result += "Disk Writes: " + diskWrites + "\n";
        result += "Time to sort: " + timeToSort;
        return result;

    }


//    /**
//     * ToString method file byteFile.
//     * 
//     * @return String representation.
//     */
//    public String toString() {
//
//        try {
//            return secondDump();
//        }
//        catch (IOException e) {
//
//            e.printStackTrace();
//        }
//        return "";
//
//    }

//
//    /**
//     * Print contents of file.
//     * 
//     * @return string result.
//     * @throws IOException
//     */
//    public String secondDump() throws IOException {
//        String result = "File: ";
//
//        byte[] basicBuffer = new byte[BYTES_PER_BLOCK];
//        RandomAccessFile reader = new RandomAccessFile(theFile, "r");
//        try {
//
//            for (int block = 0; block < numBlocks; block++) {
//                result += "BLOCK: " + block + "\n";
//                reader.read(basicBuffer);
//
//                Record[] recsInBlock = Record.toRecArray(basicBuffer);
//
//                for (int i = 0; i < 1024; i++) {
//
//                    result += recsInBlock[i].toString() + "\n";
//                }
//
//            }
//
//        }
//        finally
//
//        {
//            reader.close();
//        }
//        return result;
//    }


//    /**
//     * Dump method purely for testing.
//     * 
//     * @param bytes
//     *            The byte array to convert to string.
//     * @return Returns string representation of bytes.
//     */
//    public String testDump(byte[] bytes) {
//        String result = "File: ";
//
//        for (int block = 0; block < numBlocks; block++) {
//            result += "BLOCK: " + block + "\n";
//
//            Record[] recsInBlock = Record.toRecArray(bytes);
//
//            for (int i = 0; i < 10; i++) {
//
//                result += recsInBlock[i].toString() + "\n";
//            }
//
//        }
//
//        return result;
//    }


    /**
     * Appends to stats file.
     * 
     * @param stats
     *            String of stats to append to file.
     * @param file
     *            File to append to.
     * @throws IOException
     */
    public void appendStatsFile(String stats, String file) throws IOException {
        FileWriter appender = new FileWriter(file, true);
        appender.append(stats);
        appender.close();
    }


    /**
     * Tells cache misses after sorting.
     * 
     * @return Return cache misses.
     */
    public int getCacheMisses() {
        return pool.getCacheMisses();
    }


    /**
     * Gets buffer pool.
     * 
     * @return Return buffer pool.
     */
    public BufferPool getPool() {
        return pool;
    }


    /**
     * Tells number of blocks in file.
     * 
     * @return Returns number of blocks.
     * @throws IOException
     */
    public int getNumBlocks() throws IOException {

        int blocks = 0;
        byte[] basicBuffer = new byte[BYTES_PER_BLOCK];

        RandomAccessFile reader = new RandomAccessFile(theFile, "r");
        try {
            while (reader.read(basicBuffer) != -1) {
                blocks++;
            }

        }
        finally {
            reader.close();
        }

        return blocks;
    }


    /**
     * Returns file with records.
     * 
     * @return theFile.
     * @throws FileNotFoundException
     */
    public RandomAccessFile getFile() throws FileNotFoundException {
        return raf;
    }


    /**
     * Checks if file is sorted or not.
     * 
     * @return true if sorted.
     * @throws IOException
     */
    public boolean isSorted() throws IOException {

        byte[] basicBuffer = new byte[BYTES_PER_BLOCK];

        RandomAccessFile reader = new RandomAccessFile(theFile, "r");
        try {
            short currKey = Short.MIN_VALUE;

            for (int block = 0; block < numBlocks; block++) {
                reader.read(basicBuffer);

                // ^^^ the slow operation! Buffer helps here.

                Record[] recsInBlock = Record.toRecArray(basicBuffer);
                for (int rec = 0; rec < RECORDS_PER_BLOCK; rec++) {

                    short nextKey = recsInBlock[rec].getKey();
                    if (currKey > nextKey) {
                        reader.close();
                        return false;

                    }
                    else {

                        currKey = nextKey;
                    }
                }
            }
        }
        finally {
            reader.close();
        }
        return true;
    }


    /**
     * Method that prints out contents of binary file to help with testing.
     * 
     * @return Returns string representation of file.
     * @throws IOException
     */
    public String dump() throws IOException {

        String dump = "";

        byte[] basicBuffer = new byte[BYTES_PER_BLOCK];
        RandomAccessFile reader = new RandomAccessFile(theFile, "r");
        try {

            for (int block = 0; block < numBlocks; block++) {
                reader.read(basicBuffer);

                Record[] recsInBlock = Record.toRecArray(basicBuffer);
                if (block % 8 == 0 && block != 0) {
                    dump += "\n";
                }

                dump += getSpacing(recsInBlock[0].getKey()) + recsInBlock[0]
                    .getKey() + " " + getSpacing(recsInBlock[0].getValue())
                    + recsInBlock[0].getValue();
                dump += "   ";

            }

        }
        finally

        {
            reader.close();
        }

        return dump;

    }


    /**
     * Returns the required amount of spacing for keys and values in output.
     * 
     * @param keyVal
     *            The key to find spacing for.
     * @return String spacing.
     */
    public String getSpacing(short keyVal) {
        String space = "";

        int i = 1;
        int spacing = 0;
        while (i - 1 <= keyVal) {
            i *= 10;
            spacing++;
        }
        spacing = 5 - spacing;
        i = 0;
        while (i < spacing) {
            space += " ";
            i++;
        }
        return space;
    }


    /**
     * Creates file of randomly generated records.
     * 
     * @param blocks
     *            Number of blocks to generate.
     * @throws IOException
     */
    public void writeRandomRecords(int blocks) throws IOException {
        Random rng = new TestableRandom();
        if (seed != -1) {
            rng.setSeed(seed);
        }

        byte[] basicBuffer = new byte[BYTES_PER_BLOCK];
        ByteBuffer bb = ByteBuffer.wrap(basicBuffer);

        theFile.delete();
        // Deletes whatever content was there! This is important for if
        // you try using a file that already has lots of data, and you
        // don't reach the end of it. That old data would still be there
        // otherwise!

        RandomAccessFile writer = new RandomAccessFile(theFile, "rw");
        try {
            for (int block = 0; block < blocks; block++) {
                for (int rec = 0; rec < RECORDS_PER_BLOCK; rec++) {

                    short key = (short)rng.nextInt(Record.KEY_MAXIMUM);
                    short val = (short)rng.nextInt(Short.MAX_VALUE);
                    // puts the data in the basicBuffer...
                    bb.putShort(key);
                    bb.putShort(val);
                }
                writer.write(bb.array());
                // ^^^ the slow, costly operation!!! Good thing we use buffer
                bb.clear(); // resets the position of the buffer in array
            }
        }
        finally {
            writer.close();
        }
    }

//
// /**
// * Writes dirty buffers back into file.
// *
// * @param block
// * to be re-written.
// * @throws IOException
// */
// public void writeBuffer(int block, Record[] records) throws IOException {
// // RandomAccessFile accessor = new RandomAccessFile(theFile, "rws");
// // byte[] basicBuffer = new byte[BYTES_PER_BLOCK];
// long before = System.currentTimeMillis();
// long startingPosition = block * 4096;
//
// raf.seek(startingPosition);
//
// ByteBuffer bb = ByteBuffer.wrap(basic);
//
// for (int rec = 0; rec < RECORDS_PER_BLOCK; rec++) {
//
// short key = (short)records[rec].getKey();
// short val = (short)records[rec].getValue();
// // puts the data in the basicBuffer...
// bb.putShort(key);
// bb.putShort(val);
//
// }
// bb.clear(); // resets the position of the buffer in array
// raf.write(bb.array());
//
// diskWrites++;
// long after = System.currentTimeMillis();
// time += (after - before);
// }


    /**
     * Writes bytes bytes back into file.
     * 
     * @param block
     *            Part of file to write back into.
     * @param bytes
     *            Bytes to write back.
     * @throws IOException
     */
    public void writeBytes(int block, byte[] bytes) throws IOException {
        long startingPosition = block * BYTES_PER_BLOCK;
        // long startingPosition = block * RECORDS_PER_BLOCK;
        // ByteBuffer bb = ByteBuffer.wrap(bytes);

        raf.seek(startingPosition);
        raf.write(bytes);
        // bb.clear();

        // raf.write(bytes);

        diskWrites++;

    }

// public void writeBuffer(int block, byte[] bytes) throws IOException {
// // RandomAccessFile accessor = new RandomAccessFile(theFile, "rws");
// // byte[] basicBuffer = new byte[BYTES_PER_BLOCK];
//
// long startingPosition = block * 4096;
//
// raf.seek(startingPosition);
//
// //ByteBuffer bb = ByteBuffer.wrap(bytes);
//
// // resets the position of the buffer in array
// //bb.clear();
// raf.write(bytes);
//
//
// diskWrites++;
// }

// try {
// for (int rec = 0; rec < RECORDS_PER_BLOCK; rec++) {
// raf.writeByte(records[rec].getKey());
// raf.writeByte(records[rec].getValue());
//
// }
// }


    /**
     * Sorts the file with the buffer pool.
     * 
     * @throws IOException
     */
    public void sortFile() throws IOException {
        
        pool.sort();
        pool.flush();

        raf.close();

    }

}
