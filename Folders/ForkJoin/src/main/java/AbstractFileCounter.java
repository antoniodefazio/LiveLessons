import java.io.File;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Abstract super class for the various {@code FileCounter*} subclasses.
 */
public abstract class AbstractFileCounter
       extends RecursiveTask<Long> {
    /**
     * The current file that's being analyzed.
     */
    protected final File mFile;

    /**
     * Keeps track of the total number of documents encountered.
     */
    protected final AtomicLong mDocumentCount;

    /**
     * Keeps track of the total number of folders encountered.
     */
    protected final AtomicLong mFolderCount;

    /**
     * Constructor initializes the fields.
     */
    AbstractFileCounter(File file) {
        mFile = file;
        mDocumentCount = new AtomicLong(0);
        mFolderCount = new AtomicLong(0);
    }

    /**
     * Constructor initializes the fields.
     */
    AbstractFileCounter(File file,
                        AtomicLong documentCount,
                        AtomicLong folderCount) {
        mFile = file;
        mDocumentCount = documentCount;
        mFolderCount = folderCount;
    }

    /**
     * @return The number of documents counted during the recursive
     * traversal
     */
    public long documentCount() {
        return mDocumentCount.get();
    }

    /**
     * @return The number of folders counted during the recursive
     * traversal
     */
    public long folderCount() {
        return mFolderCount.get();
    }

    protected long handleDocument(File document) {
        // Increment the count of documents.
        mDocumentCount.incrementAndGet();

        // Return the length of the document.
        return document.length();
    }

    protected long handleFolder(File folder,
                                Supplier<Long> factory) {
        // Increment the count of folders.
        mFolderCount.incrementAndGet();

        // Recursively count the number of files in a (sub)folder.
        return factory.get();
    };
}

