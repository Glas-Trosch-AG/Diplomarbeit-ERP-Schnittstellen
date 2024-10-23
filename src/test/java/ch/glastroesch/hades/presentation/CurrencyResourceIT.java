package ch.glastroesch.hades.presentation;

import ch.glastroesch.Application;
import static ch.glastroesch.hades.business.common.TestableDate.createDate;
import static ch.glastroesch.hades.business.common.TestableDate.setCurrentDate;
import ch.glastroesch.hades.business.currency.CurrencyEntry;
import static ch.glastroesch.hades.business.currency.CurrencyEntry.newCurrencyEntry;
import static ch.glastroesch.hades.common.JsonService.asString;
import static ch.glastroesch.hades.common.RestService.call;
import java.io.IOException;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static java.util.Calendar.SEPTEMBER;
import java.util.List;
import org.apache.commons.io.IOUtils;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.skyscreamer.jsonassert.JSONCompareMode.NON_EXTENSIBLE;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class CurrencyResourceIT {

    @Test
    public void shouldGetNewestEntries() throws JSONException {

        setCurrentDate(createDate(2024, SEPTEMBER, 19, 7, 30, 10));

        ResponseEntity<String> response = call("/currency/entries", String.class);

        List<CurrencyEntry> entries = asList(
                newCurrencyEntry()
                        .withId(77L)
                        .withDate(createDate(2024, SEPTEMBER, 19, 8, 17, 17))
                        .withFilename("a182z240918")
                        .withErrorMessage(null)
                        .build(),
                newCurrencyEntry()
                        .withId(76L)
                        .withDate(createDate(2024, SEPTEMBER, 18, 8, 25, 33))
                        .withFilename("a182z240918")
                        .withErrorMessage("""
                                          ORA-20111: CurrencyCode.FND_RECORD_NOT_EXIST: The Currency Code does not exist.
                                          ORA-06512: in "IFSAPP.ERROR_SYS", Zeile 140
                                          ORA-06512: in "IFSAPP.ERROR_SYS", Zeile 366
                                          ORA-06512: in "IFSAPP.CURRENCY_CODE_API", Zeile 832
                                          ORA-06512: in "IFSAPP.CURRENCY_CODE_API", Zeile 836
                                          ORA-06512: in "IFSAPP.CURRENCY_CODE_API", Zeile 303
                                          ORA-06512: in "IFSAPP.CURRENCY_CODE_API", Zeile 308
                                          ORA-06512: in "IFSAPP.CURRENCY_RATE_API", Zeile 3340
                                          ORA-06512: in "IFSAPP.CURRENCY_RATE_API", Zeile 3371
                                          ORA-06512: in "IFSAPP.CURRENCY_RATE_API", Zeile 3381
                                          ORA-06512: in "IFSAPP.CURRENCY_RATE_API", Zeile 3398
                                          ORA-06512: in "IFSAPP.CURRENCY_RATE_API", Zeile 3429
                                          ORA-06512: in "IFSAPP.CURRENCY_RATE_API", Zeile 3436
                                          ORA-06512: in "IFSAPP.CURRENCY_RATE_API", Zeile 654
                                          ORA-06512: in "IFSAPP.CURRENCY_RATE_API", Zeile 662
                                          ORA-06512: in Zeile 1""")
                        .build(),
                newCurrencyEntry()
                        .withId(75L)
                        .withDate(createDate(2024, SEPTEMBER, 17, 8, 22, 29))
                        .withFilename("a180z240916")
                        .withErrorMessage(null)
                        .build()
        );

        assertEquals(asString(entries), response.getBody(), NON_EXTENSIBLE);

    }

    @Test
    public void shouldGetFile() throws IOException {

        ResponseEntity<byte[]> response = call("/currency/entries/files/a182z240918", byte[].class);

        byte[] content = getContentOf("a182z240918.xml");

        assertThat(response.getHeaders().getContentType(), is(MediaType.APPLICATION_XML));
        assertThat(new String(response.getBody(), UTF_8), is(new String(content, UTF_8)));

    }

    private byte[] getContentOf(String filename) throws IOException {

        return IOUtils.toByteArray(this.getClass()
                .getResourceAsStream("/ch/glastroesch/hades/presentation/" + filename));

    }

}
