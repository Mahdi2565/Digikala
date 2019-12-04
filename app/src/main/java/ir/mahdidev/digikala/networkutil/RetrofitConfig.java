package ir.mahdidev.digikala.networkutil;

import java.io.IOException;

import ir.mahdidev.digikala.util.Const;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    public static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if (retrofit==null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor(Const.RetrofitConst.CONSUMER_KEY ,
                            Const.RetrofitConst.CONSUMER_SECRET))
                    .build();

            retrofit = new Retrofit.Builder().baseUrl(Const.RetrofitConst.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static class BasicAuthInterceptor implements Interceptor {

        private String credentials;

        public BasicAuthInterceptor(String consumer_key, String consumer_secret) {
            this.credentials = Credentials.basic(consumer_key, consumer_secret);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }

    }
}
