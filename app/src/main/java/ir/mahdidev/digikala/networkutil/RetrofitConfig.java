package ir.mahdidev.digikala.networkutil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import ir.mahdidev.digikala.util.Const;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private static Retrofit retrofit;
    private static Retrofit mapRetrofit;
    public static Retrofit getRetrofit(){
        if (retrofit==null){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor(Const.RetrofitConst.CONSUMER_KEY ,
                            Const.RetrofitConst.CONSUMER_SECRET))
                    .addInterceptor(logging)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build();



            retrofit = new Retrofit.Builder().baseUrl(Const.RetrofitConst.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getMapRetrofit(){
        if (mapRetrofit==null){
            mapRetrofit = new Retrofit.Builder().baseUrl(Const.RetrofitConst.BASE_URL_GEOCODING_MAP_IR)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mapRetrofit;
    }



    private static class BasicAuthInterceptor implements Interceptor {

        private String credentials;
        private String consumer_key;
        private String consumer_secret;

        public BasicAuthInterceptor(String consumer_key, String consumer_secret) {
            this.credentials = Credentials.basic(consumer_key, consumer_secret);
            this.consumer_key = consumer_key ;
            this.consumer_secret = consumer_secret;
        }



        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().
                    addQueryParameter("consumer_key",Const.RetrofitConst.CONSUMER_KEY)
                    .addQueryParameter("consumer_secret" , Const.RetrofitConst.CONSUMER_SECRET)
                    .build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        }


    }
}
