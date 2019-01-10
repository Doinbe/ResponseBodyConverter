package retrofit.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Solin on 2018/6/21 15:46
 */
public final class SGsonConverterFactory extends Converter.Factory {

    public static SGsonConverterFactory create(Class baseClazz) {
        return create(new Gson(), baseClazz);
    }

    public static SGsonConverterFactory create(Gson gson, Class baseClazz) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new SGsonConverterFactory(gson, baseClazz);
    }

    private final Gson gson;
    private final Class baseClazz;

    private SGsonConverterFactory(Gson gson, Class baseClazz) {
        this.gson = gson;
        this.baseClazz = baseClazz;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new SGsonResponseBodyConverter<>(gson, adapter, baseClazz);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new SGsonRequestBodyConverter<>(gson, adapter);
    }
}
