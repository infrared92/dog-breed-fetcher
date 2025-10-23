package dogapi;

import java.util.*;


/** new commit
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private int callsMade = 0;
    private final HashMap<String, List<String>> cache;
    private final BreedFetcher fetcher;

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
        this.cache = new HashMap<>();
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        // return statement included so that the starter code can compile and run.

        //If the breed exists in the cache, return the cached list directly.
        if (this.cache.containsKey(breed)) {
            return this.cache.get(breed);
        }
        //Call the underlying fetcher if not cached:
        else {
            try {
                List<String> result = fetcher.getSubBreeds(breed);
                this.cache.put(breed, result);
                callsMade++;
                return result;

            } catch (BreedNotFoundException b) {
                callsMade++;
                throw b;
            }
        }
    }

    public int getCallsMade() {
        return callsMade;
    }
}