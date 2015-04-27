package utils.retrofit;

import java.io.IOException;
import java.lang.Override;import java.util.ArrayList;

import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;

public class RetrofitTestClient implements Client {

    // Members

    private Request mTapedRequest;
    private Response mTapedResponse;

    // Constructors

    public RetrofitTestClient() {
        super();

        mTapedResponse = new Response("Mocked request", 200, "Mocked request", new ArrayList<Header>(), null);
    }

    public RetrofitTestClient(Response response) {
        super();

        mTapedResponse = response;
    }

    // Public API

    public Request getTapedRequest() {
        return mTapedRequest;
    }

    public Response getTapedResponse() {
        return mTapedResponse;
    }

    // Client

    @Override
    public Response execute(Request request) throws IOException {
        mTapedRequest = request;

        return mTapedResponse;
    }

}
