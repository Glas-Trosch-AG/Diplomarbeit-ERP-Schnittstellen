package ch.glastroesch.hades.presentation;

import ch.glastroesch.Application;
import static ch.glastroesch.hades.business.common.TestableDate.createDate;
import static ch.glastroesch.hades.business.common.TestableDate.setCurrentDate;
import ch.glastroesch.hades.business.finance.OpenPaymentEntry;
import static ch.glastroesch.hades.business.finance.OpenPaymentEntry.newOpenPaymentEntry;
import static ch.glastroesch.hades.common.JsonService.asString;
import static ch.glastroesch.hades.common.RestService.call;
import java.io.IOException;
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
public class OpenPaymentEntryResourceIT {

    @Test
    public void shouldGetNewestEntries() throws JSONException {

        setCurrentDate(createDate(2024, SEPTEMBER, 19, 7, 30, 10));

        ResponseEntity<String> response = call("/openpayment/entries", String.class);

        List<OpenPaymentEntry> entries = asList(
                newOpenPaymentEntry()
                        .withId(214)
                        .withReadOn(createDate(2024, SEPTEMBER, 19, 11, 52, 53))
                        .withFilename("FICOINFOB_20240918114503.csv")
                        .withErrorMessage(null)
                        .withSuccessful(true)
                        .build(),
                newOpenPaymentEntry()
                        .withId(213)
                        .withReadOn(createDate(2024, SEPTEMBER, 18, 9, 16, 30))
                        .withFilename("FICOINFOB_20240918090827.csv")
                        .withErrorMessage(null)
                        .withSuccessful(true)
                        .build(),
                newOpenPaymentEntry()
                        .withId(212)
                        .withReadOn(createDate(2024, SEPTEMBER, 17, 8, 16, 9))
                        .withFilename("FICOINFOB_20240918080745.csv")
                        .withErrorMessage(null)
                        .withSuccessful(true)
                        .build()
        );

        assertEquals(asString(entries), response.getBody(), NON_EXTENSIBLE);

    }

    @Test
    public void shouldGetFile() throws IOException {

        ResponseEntity<byte[]> response = call("/openpayment/files/FICOINFOB_20240918114503.csv", byte[].class);

        byte[] content = getContentOf("FICOINFOB_20240918114503.csv");

        assertThat(response.getHeaders().getContentType(), is(new MediaType("text", "csv")));
        assertThat(response.getBody(), is(content));

    }

    private byte[] getContentOf(String filename) throws IOException {

        return IOUtils.toByteArray(this.getClass()
                .getResourceAsStream("/ch/glastroesch/hades/presentation/" + filename));

    }

}
