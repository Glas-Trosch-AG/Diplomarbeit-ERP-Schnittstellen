package ch.glastroesch.hades.common;
 
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import static org.springframework.http.HttpMethod.GET;
import org.springframework.http.ResponseEntity;
 
public class RestService {
 
    public static <T extends Object> ResponseEntity<T> call(String url, Class<T> returnType) {
 
        return new TestRestTemplate().exchange(
                createBaseUrl() + url,
                GET,
                new HttpEntity<>(null, new HttpHeaders()),
                returnType);
 
    }
 
    private static String createBaseUrl() {
 
        return "http://localhost:8095";
 
    }
 
}