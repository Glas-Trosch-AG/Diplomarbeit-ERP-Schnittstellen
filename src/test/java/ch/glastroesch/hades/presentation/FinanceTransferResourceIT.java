package ch.glastroesch.hades.presentation;

import ch.glastroesch.Application;
import static ch.glastroesch.hades.business.common.TestableDate.createDate;
import static ch.glastroesch.hades.business.common.TestableDate.setCurrentDate;
import ch.glastroesch.hades.business.transfer.FinanceFile;
import static ch.glastroesch.hades.business.transfer.FinanceFile.newFinanceFile;
import ch.glastroesch.hades.business.transfer.FinanceTransfer;
import static ch.glastroesch.hades.business.transfer.FinanceTransfer.newFinanceTransfer;
import static ch.glastroesch.hades.business.transfer.FinanceTransferType.CREATE;
import static ch.glastroesch.hades.business.transfer.FinanceTransferType.MOVE;
import static ch.glastroesch.hades.business.transfer.FinanceTransferType.RUN;
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
public class FinanceTransferResourceIT {

    @Test
    public void shouldGetNewestEntries() throws JSONException {

        setCurrentDate(createDate(2024, SEPTEMBER, 19, 7, 30, 10));

        ResponseEntity<String> response = call("/transfer/finance/entries", String.class);

        List<FinanceTransfer> transfers = asList(
                newFinanceTransfer()
                        .withId(1372)
                        .withType(MOVE)
                        .withStartedOn(createDate(2024, SEPTEMBER, 19, 19, 48, 59))
                        .withFinishedOn(createDate(2024, SEPTEMBER, 19, 19, 49, 59))
                        .withTenant(82)
                        .withErrorMessage("no files were created")
                        .build(),
                newFinanceTransfer()
                        .withId(1367)
                        .withType(CREATE)
                        .withStartedOn(createDate(2024, SEPTEMBER, 19, 19, 46, 59))
                        .withFinishedOn(createDate(2024, SEPTEMBER, 19, 19, 47, 59))
                        .withTenant(82)
                        .withErrorMessage(null)
                        .build(),
                newFinanceTransfer()
                        .withId(1362)
                        .withType(RUN)
                        .withStartedOn(createDate(2024, SEPTEMBER, 19, 19, 44, 59))
                        .withFinishedOn(createDate(2024, SEPTEMBER, 19, 19, 45, 59))
                        .withTenant(82)
                        .withErrorMessage(null)
                        .build(),
                newFinanceTransfer()
                        .withId(1373)
                        .withType(MOVE)
                        .withStartedOn(createDate(2024, SEPTEMBER, 18, 19, 48, 59))
                        .withFinishedOn(createDate(2024, SEPTEMBER, 18, 19, 49, 59))
                        .withTenant(82)
                        .withErrorMessage(null)
                        .build(),
                newFinanceTransfer()
                        .withId(1368)
                        .withType(CREATE)
                        .withStartedOn(createDate(2024, SEPTEMBER, 18, 19, 46, 59))
                        .withFinishedOn(createDate(2024, SEPTEMBER, 18, 19, 47, 59))
                        .withTenant(82)
                        .withErrorMessage(null)
                        .build(),
                newFinanceTransfer()
                        .withId(1363)
                        .withType(RUN)
                        .withStartedOn(createDate(2024, SEPTEMBER, 18, 19, 44, 59))
                        .withFinishedOn(createDate(2024, SEPTEMBER, 18, 19, 45, 59))
                        .withTenant(82)
                        .withErrorMessage(null)
                        .build(),
                newFinanceTransfer()
                        .withId(1374)
                        .withType(MOVE)
                        .withStartedOn(createDate(2024, SEPTEMBER, 17, 19, 48, 59))
                        .withFinishedOn(createDate(2024, SEPTEMBER, 17, 19, 49, 59))
                        .withTenant(82)
                        .withErrorMessage("no files were created")
                        .build(),
                newFinanceTransfer()
                        .withId(1369)
                        .withType(CREATE)
                        .withStartedOn(createDate(2024, SEPTEMBER, 17, 19, 46, 59))
                        .withFinishedOn(createDate(2024, SEPTEMBER, 17, 19, 47, 59))
                        .withTenant(82)
                        .withErrorMessage(null)
                        .build(),
                newFinanceTransfer()
                        .withId(1364)
                        .withType(RUN)
                        .withStartedOn(createDate(2024, SEPTEMBER, 17, 19, 44, 59))
                        .withFinishedOn(createDate(2024, SEPTEMBER, 17, 19, 45, 59))
                        .withTenant(82)
                        .withErrorMessage(null)
                        .build()
        );

        assertEquals(asString(transfers), response.getBody(), NON_EXTENSIBLE);

    }

    @Test
    public void shouldGetFiles() throws JSONException {

        setCurrentDate(createDate(2024, SEPTEMBER, 19, 7, 30, 10));

        ResponseEntity<String> response = call("/transfer/finance/entries/1367", String.class);

        List<FinanceFile> files = asList(
                newFinanceFile()
                        .withId(1909)
                        .withName("Protokoll_0057_20240903_1619.pdf")
                        .withErrorMessage(null)
                        .build(),
                newFinanceFile()
                        .withId(1908)
                        .withName("KUNDEN_0057_20240903161927.csv")
                        .withErrorMessage(null)
                        .build()
        );

        assertEquals(asString(files), response.getBody(), NON_EXTENSIBLE);
    }

    @Test
    public void shouldGetCsvFile() throws IOException {

        ResponseEntity<byte[]> response = call("/transfer/finance/files/csv/KUNDEN_0057_20240903161927.csv", byte[].class);

        byte[] content = getContentOf("KUNDEN_0057_20240903161927.csv");

        assertThat(response.getHeaders().getContentType(), is(new MediaType("text", "csv")));
        assertThat(response.getBody(), is(content));

    }

    @Test
    public void shouldGetPdfFile() throws IOException {

        ResponseEntity<byte[]> response = call("/transfer/finance/files/pdf/Protokoll_0057_20240903_1619.pdf", byte[].class);

        byte[] content = getContentOf("Protokoll_0057_20240903_1619.pdf");

        assertThat(response.getHeaders().getContentType(), is(MediaType.APPLICATION_PDF));
        assertThat(response.getBody(), is(content));

    }

    private byte[] getContentOf(String filename) throws IOException {

        return IOUtils.toByteArray(this.getClass()
                .getResourceAsStream("/ch/glastroesch/hades/presentation/" + filename));

    }

}
