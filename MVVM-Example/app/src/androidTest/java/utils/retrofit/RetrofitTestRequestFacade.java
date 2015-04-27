package utils.retrofit;

import java.lang.Object;import java.lang.Override;import java.lang.String;import java.util.ArrayList;
import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.client.Header;

public class RetrofitTestRequestFacade implements RequestInterceptor.RequestFacade {

    // Inner classes

    public class Param {
        public String name;
        public String value;

        public Param(String name, String value) {
            super();

            this.name = name;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            return ((Param)o).name.equals(name) && ((Param)o).value.equals(value);
        }
    }

    // Members

    private List<Header> mHeaders = new ArrayList<>();
    private List<Param> mPathParams = new ArrayList<>();
    private List<Param> mQueryParams = new ArrayList<>();

    // Public API

    public List<Header> getHeaders() {
        return mHeaders;
    }

    public List<Param> getPathParams() {
        return mPathParams;
    }

    public List<Param> getQueryParams() {
        return mQueryParams;
    }

    // RequestInterceptor.RequestFacade

    @Override
    public void addHeader(String name, String value) {
        mHeaders.add(new Header(name, value));
    }

    @Override
    public void addPathParam(String name, String value) {
        mPathParams.add(new Param(name, value));
    }

    @Override
    public void addEncodedPathParam(String name, String value) {
        mPathParams.add(new Param(name, value));
    }

    @Override
    public void addQueryParam(String name, String value) {
        mQueryParams.add(new Param(name, value));
    }

    @Override
    public void addEncodedQueryParam(String name, String value) {
        mQueryParams.add(new Param(name, value));
    }
}
