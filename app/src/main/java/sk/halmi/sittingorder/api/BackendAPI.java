package sk.halmi.sittingorder.api;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by FS0149 on 27.6.2016.
 * https://futurestud.io/blog/retrofit-getting-started-and-android-client
 */
public class BackendAPI {

	public static final String API_BASE_URL = "http://54.169.86.172:8000/";

	private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
	static {
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
	}

	private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(interceptor);

	private static Retrofit.Builder builder = new Retrofit.Builder()
			.baseUrl(API_BASE_URL)
			.addConverterFactory(GsonConverterFactory.create());

	public static <S> S createService(Class<S> serviceClass) {
//		Retrofit retrofit = builder.client(httpClient.build()).build();
		// concatenate username and password with colon for authentication
		String credentials = "trainee06:school1234";
		// create Base64 encoded string
		final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

		httpClient.addInterceptor(new Interceptor() {
			@Override
			public Response intercept(Interceptor.Chain chain) throws IOException {
				Request original = chain.request();

				Request.Builder requestBuilder = original.newBuilder()
						.header("Authorization", basic)
						.header("Accept", "application/json")
						.method(original.method(), original.body());

				Request request = requestBuilder.build();
				return chain.proceed(request);
			}
		});

		OkHttpClient client = httpClient.build();
		Retrofit retrofit = builder.client(client).build();

		return retrofit.create(serviceClass);
	}
}
