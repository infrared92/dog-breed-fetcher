package dogapi;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private static final String API_URL = "https://dog.ceo/api";
    private static final String STATUS = "status";
        /**
         * Fetch the list of sub breeds for the given breed from the dog.ceo API.
         * @param breed the breed to fetch sub breeds for
         * @return list of sub breeds for the given breed
         */
        @Override
        public List<String> getSubBreeds (String breed) throws BreedNotFoundException {
            // return statement included so that the starter code can compile and run.
            // use the API to list all sub-breeds from https://dog.ceo/api/breed/hound/list
            // and parse JSON array to arraylist

            final OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            final Request request = new Request.Builder()
                    .url(String.format("%s/breed/%s/list", API_URL, breed))
                    .build();


            try {
                final Response response = client.newCall(request).execute();
                final JSONObject responseBody = new JSONObject(response.body().string());

                if (responseBody.get(STATUS).equals("success")) {
                    final JSONArray breeds = responseBody.getJSONArray("message");
                    List<String> subBreeds = new ArrayList<>();
                    for (int i = 0; i < breeds.length(); i++) {
                        subBreeds.add(breeds.getString(i));
                    }
                    return subBreeds;
                }
            }
            catch (IOException e){
                throw new BreedNotFoundException(breed);

            }
            throw new BreedNotFoundException(breed);
        }
        }

