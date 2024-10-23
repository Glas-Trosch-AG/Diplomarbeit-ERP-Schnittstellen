package ch.glastroesch.hades.common;
 
import com.google.gson.GsonBuilder;
import java.util.List;
 
public class JsonService {
 
    public static String asString(List list) {
 
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000+00:00'")
                .serializeNulls()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJson(list);
 
    }
 
}

