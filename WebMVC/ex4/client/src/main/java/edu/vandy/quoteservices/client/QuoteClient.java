package edu.vandy.quoteservices.client;

import edu.vandy.quoteservices.common.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This client uses Spring WebMVC features to perform synchronous
 * remote method invocations on the {@code ZippyController} and {@code
 * HandeyController} web services to request random quotes.
 *
 * The {@code @Component} annotation allows Spring to automatically
 * detect custom beans, i.e., Spring will scan the application for
 * classes annotated with {@code @Component}, instantiate them, and
 * inject the specified dependencies into them without having to write
 * any explicit code.
 */
@Component
public class QuoteClient {
    /**
     * This auto-wired field connects the {@link QuoteClient} to the
     * {@link QuoteProxy} that performs HTTP requests synchronously.
     */
    @Autowired
    private QuoteProxy mQuoteProxy; 

    /**
     * Spring WebMVC maps HTTP GET requests sent to the {@code
     * GET_QUOTES} endpoint to this method.
     *
     * @return An {@link List} of {@link Quote} objects
     */
    public List<Quote> getQuotes(String service,
                                 List<Integer> quoteIds) {
        return mQuoteProxy
            // Forward to the proxy.
            .getQuotes(service, quoteIds);
    }

    /**
     * Spring WebMVC maps HTTP GET requests sent to the {@code
     * GET_ALL_QUOTES} endpoint to this method.
     *
     * @return An {@link List} of {@link Quote} objects
     */
    public List<Quote> getAllQuotes(String service) {
        return mQuoteProxy
            // Forward to the proxy.
            .getAllQuotes(service);
    }
}
