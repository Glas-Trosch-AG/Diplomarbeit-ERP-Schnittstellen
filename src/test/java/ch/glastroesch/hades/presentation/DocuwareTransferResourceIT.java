package ch.glastroesch.hades.presentation;

import ch.glastroesch.Application;
import static ch.glastroesch.hades.business.common.TestableDate.createDate;
import static ch.glastroesch.hades.business.common.TestableDate.setCurrentDate;
import ch.glastroesch.hades.business.transfer.DocuwareFile;
import static ch.glastroesch.hades.business.transfer.DocuwareFile.newDocuwareFile;
import ch.glastroesch.hades.business.transfer.DocuwareTransfer;
import static ch.glastroesch.hades.business.transfer.DocuwareTransfer.newDocuwareTransfer;
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
public class DocuwareTransferResourceIT {

    @Test
    public void shouldGetNewestEntries() throws JSONException {

        setCurrentDate(createDate(2024, SEPTEMBER, 19, 7, 30, 10));

        ResponseEntity<String> response = call("/transfer/docuware/entries", String.class);

        List<DocuwareTransfer> transfers = asList(
                newDocuwareTransfer()
                        .withId(70L)
                        .withStartedOn(createDate(2024, SEPTEMBER, 19, 19, 44, 59))
                        .withFinishedOn(createDate(2024, SEPTEMBER, 19, 19, 50, 59))
                        .withErrorMessage("folder not ready")
                        .build(),
                newDocuwareTransfer()
                        .withId(69L)
                        .withStartedOn(createDate(2024, SEPTEMBER, 18, 19, 44, 59))
                        .withFinishedOn(createDate(2024, SEPTEMBER, 18, 19, 50, 54))
                        .withErrorMessage(null)
                        .build(),
                newDocuwareTransfer()
                        .withId(68L)
                        .withStartedOn(createDate(2024, SEPTEMBER, 17, 19, 53, 0))
                        .withFinishedOn(createDate(2024, SEPTEMBER, 17, 19, 57, 0))
                        .withErrorMessage(null)
                        .build()
        );

        assertEquals(asString(transfers), response.getBody(), NON_EXTENSIBLE);

    }

    @Test
    public void shouldGetFiles() throws JSONException {

        setCurrentDate(createDate(2024, SEPTEMBER, 19, 7, 30, 10));

        ResponseEntity<String> response = call("/transfer/docuware/entries/70", String.class);

        List<DocuwareFile> files = asList(
                newDocuwareFile()
                        .withId(9862)
                        .withName("de00571103674RE.CSV")
                        .withErrorMessage(null)
                        .build(),
                newDocuwareFile()
                        .withId(9878)
                        .withName("de00571103674RE.PDF")
                        .withErrorMessage(null)
                        .build()
        );

        assertEquals(asString(files), response.getBody(), NON_EXTENSIBLE);

    }

    @Test
    public void shouldGetCsvFile() throws IOException {

        ResponseEntity<byte[]> response = call("/transfer/docuware/files/csv/9862", byte[].class);

        byte[] content = getContentOf("de00571103674RE.CSV");

        assertThat(response.getHeaders().getContentType(), is(new MediaType("text", "csv")));
        assertThat(response.getBody(), is(content));

    }

    @Test
    public void shouldGetPdfFile() throws IOException {

        ResponseEntity<byte[]> response = call("/transfer/docuware/files/pdf/9878", byte[].class);

        byte[] content = getContentOf("de00571103674RE.PDF");

        assertThat(response.getHeaders().getContentType(), is(MediaType.APPLICATION_PDF));
        assertThat(response.getBody(), is(content));

    }

    private byte[] getContentOf(String filename) throws IOException {

        return IOUtils.toByteArray(this.getClass()
                .getResourceAsStream("/ch/glastroesch/hades/presentation/" + filename));

    }

}
