package folders;

import folders.client.FolderClient;
import folders.server.FolderApplication;
import folders.utils.Options;
import folders.utils.RunTimer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This example shows the use of a Spring WebFlux micro-service to
 * apply Java sequential and parallel to process entries in a
 * recursively-structured directory folder sequentially, concurrently,
 * and in parallel in a client/server environment.  This example also
 * encodes/decodes complex objects that use inheritance relationships
 * and transmits them between processes.
 */
@SpringBootConfiguration
@SpringBootTest(
        classes = FolderApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FolderApplicationTests {
    @Autowired
    private FolderClient mFolderClient;

    /**
     * Emulate the "command-line" arguments for the tests.
     */
    private final String[] mArgv = new String[]{
            "-d",
            "true", 
            "-c",
            "true",
            "-s",
            "true" 
    };

    /**
     * Run all the tests and print the results.
     */
    @Test
    public void runTests() {
        System.out.println("Starting WebMVCFolders test");

        Options.getInstance().parseArgs(mArgv);

        if (Options.getInstance().sequential()) {
            // Run the tests sequentially.
            mFolderClient.runTests(false);
            mFolderClient.runRemoteTests(false);
        }

        if (Options.getInstance().concurrent()) {
            // Run the tests concurrently.
            mFolderClient.runTests(true);
            mFolderClient.runRemoteTests(false);
        }

        // Print results sorted by decreasing order of efficiency.
        System.out.println(RunTimer.getTimingResults());

        System.out.println("Ending WebMVCFolders test");
    }
}
