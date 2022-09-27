package folders.client;

import folders.folder.Dirent;
import folders.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Stream;

import static folders.common.Constants.EndPoint.*;
import static folders.common.Constants.SERVER_BASE_URL;

/**
 * This class is a proxy to the FolderApplication micro-service.
 */
@SuppressWarnings("FieldCanBeLocal")
@Component
public final class FolderProxy {
    /**
     * A Java utility class should have a private constructor.
     */
    private FolderProxy() {}

    /**
     * A URI to the input "works" to process, which is a large
     * recursive folder containing thousands of subfolders and files.
     */
    private final String mCreateURI =
        FOLDERS + ROOT_DIR + CREATE_FOLDER;

    /**
     * A URI to count the entries in the root folder.
     */
    private final String mCountURI =
        FOLDERS + ROOT_DIR + COUNT_DOCUMENTS;

    /**
     * A URI to count the number of times a word appears in the root folder.
     */
    private final String mSearchURI =
        FOLDERS + ROOT_DIR + SEARCH;

    /**
     * A URI to return all documents that include a word match in the root folder.
     */
    private final String mGetDocumentsURI =
        FOLDERS + ROOT_DIR + GET_DOCUMENTS;

    /**
     * This auto-wired field connects the {@link
     * FolderProxy} to the {@link RestTemplate}
     * that performs HTTP requests synchronously.
     */
    @Autowired
    private RestTemplate mRestTemplate;

    /**
     * Synchronously and remotely create an in-memory folder
     * containing all the works.
     *
     * @param concurrent Flag indicating whether to run the test
     *                   concurrently or not
     * @return A {@link Dirent} that holds a folder containing all the
     *         works
     */
    public
    Dirent createRemoteFolder(boolean concurrent) {
        // Return a mono to the folder initialized remotely.
        return WebUtils
            .makeGetRequest(mRestTemplate,
                            // Add the uri to the baseUrl.
                            UriComponentsBuilder
                            .fromPath(mCreateURI)
                            .queryParam("concurrent", concurrent)
                            .build()
                            .toString(),
                            Dirent.class);
    }

    /**
     * Asynchronously and remotely count the number of entries in the folder.
     *
     * @param concurrent Flag indicating whether to run the test
     *                   concurrently or not
     * @return A {@link Long} that counts the number of
     *         entries in the folder
     */
    public Long countEntries(boolean concurrent) {
        // Return a mono to the folder initialized remotely.
        return WebUtils
            .makeGetRequest(mRestTemplate,
                            // Add the uri to the baseUrl.
                            UriComponentsBuilder
                            .fromPath(mCountURI)
                            .queryParam("concurrent", concurrent)
                            .build()
                            .toString(),
                            Long.class);
    }

    /**
     * Asynchronously and remotely count the number of times that
     * {@code word} appears in the folder.
     *
     * @param word The word to search for
     * @param concurrent True if the search should be done
     *                   concurrently else false
     * @return A {@link Long} that counts the number of
     *         entries in the folder
     */
    public Long searchWord(String word,
                                  boolean concurrent) {
        // Return a mono to the folder initialized remotely.
        return WebUtils
            .makeGetRequest(mRestTemplate,
                            UriComponentsBuilder
                            .fromPath(mSearchURI)
                            .queryParam("word", word)
                            .queryParam("concurrent", concurrent)
                            .build()
                            .toString(),
                            Long.class);
    }

    /**
     * Returns a {@link Stream} of documents that match the {@code word}
     *
     * @param word The word to search for
     * @param concurrent True if the search should run concurrently, else false
     * @return A {@link List<Dirent>} containing documents that match the {@code word}
     */
    public List<Dirent> getDocuments(String word,
                                     boolean concurrent) {
        // Return a Stream to the folder initialized remotely.
        return WebUtils
            .makeGetRequestList(mRestTemplate,
                                UriComponentsBuilder
                                .fromPath(mGetDocumentsURI)
                                .queryParam("word", word)
                                .queryParam("concurrent", concurrent)
                                .build()
                                .toString(),
                                Dirent[].class);
    }
}
