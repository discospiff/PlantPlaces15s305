package nw15s305.plantplaces.com.dao;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by jonesb on 4/24/2015.
 */
public class NetworkDAO {

    /**
     * Execute the given URI, and return the data from that URI.
     * @param uri the universal resource indicator for a set of data.
     * @return the set of data provided by the uri
     */
    public String request(String uri) throws IOException {
        // Use the GET method, which submits the search terms in the URL.
        HttpGet httpGet = new HttpGet(uri);
        // how to handle response data.
        ResponseHandler<String> responseHander = new BasicResponseHandler();

        // marry the request and the response.
        HttpClient httpClient = new DefaultHttpClient();
        String returnString = null;
            returnString = httpClient.execute(httpGet, responseHander);
        return returnString;
    }
}
