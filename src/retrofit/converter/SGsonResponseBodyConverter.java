package retrofit.converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Solin on 2018/6/21 15:59
 */
final class SGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final Class baseBeanClazz;

    SGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, Class baseBeanClazz) {
        this.gson = gson;
        this.adapter = adapter;
        this.baseBeanClazz = baseBeanClazz;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String valueString = null;
        try {
            valueString = value.string();
            return adapter.fromJson(valueString);
        } catch (JsonSyntaxException e) {
            try {
                return (T) gson.fromJson(valueString, baseBeanClazz);
            } catch (JsonSyntaxException | ClassCastException e1) {
                throw e;
            }
        } finally {
            value.close();
        }
    }
}
