import java.io.File;
import java.io.PrintStream;

// "I have neither given nor received unauthorized assistance on this
// assignment."

/**
 * External HeapsSort starter kit.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.10.18
 */
public class HeapSort {

    /**
     * This is the entry point of the application
     * 
     * @param args
     *            Command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            throw new Exception("Not enough command line arguments!");
        }
        else if (args[0] == null || args[1] == null || args[2] == null) {
            return;
        }
        else {
            int numBuffers = Integer.valueOf(args[1]);
            String stats = args[2];
            ByteFile bFile = new ByteFile(args[0], numBuffers, -1);
            long before = System.currentTimeMillis();
            bFile.sortFile();
            long after = System.currentTimeMillis();
            long time = after - before;
            System.out.println(bFile.dump());
            bFile.appendStatsFile(bFile.buildStats(time), stats);
        }

        // Look at the spec to see what arguments are used for!

    }
}
