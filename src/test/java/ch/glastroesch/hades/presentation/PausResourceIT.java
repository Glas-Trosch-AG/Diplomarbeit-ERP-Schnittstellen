package ch.glastroesch.hades.presentation;

import ch.glastroesch.Application;
import static ch.glastroesch.hades.business.common.TestableDate.createDate;
import ch.glastroesch.hades.business.paus.PausEntry;
import static ch.glastroesch.hades.business.paus.PausEntry.newPausEntry;
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
public class PausResourceIT {

    @Test
    public void shouldGetNewestPackages() throws JSONException {

        ResponseEntity<String> response = call("/paus/companies/82/entries", String.class);

        List<PausEntry> entries = asList(
                newPausEntry()
                        .withId(414563)
                        .withSentOn(createDate(2024, SEPTEMBER, 25, 10, 48, 30))
                        .withTenant(82)
                        .withPackageNumber("240925433782201")
                        .withErrorMessage(null)
                        .withException("ORA-20110: ShopMaterialAlloc.NOISSUE3: Insufficient material. Issuing could not be done for the part F4C01.\n\n")
                        .withProcessed(true)
                        .withRetries(0)
                        .withSuccessful(false)
                        .withIgnore(false)
                        .build(),
                newPausEntry()
                        .withId(414547)
                        .withSentOn(createDate(2024, SEPTEMBER, 25, 10, 25, 29))
                        .withTenant(82)
                        .withPackageNumber("240925102572201")
                        .withErrorMessage("Nicht genügend Material")
                        .withException("ORA-20110: ShopMaterialAlloc.NOISSUE3: Insufficient material. Issuing could not be done for the part F4C01.\n\n")
                        .withProcessed(false)
                        .withRetries(5)
                        .withSuccessful(false)
                        .withIgnore(false)
                        .build(),
                newPausEntry()
                        .withId(414543)
                        .withSentOn(createDate(2024, SEPTEMBER, 25, 10, 22, 22))
                        .withTenant(82)
                        .withPackageNumber("240925093282100")
                        .withErrorMessage(null)
                        .withException(null)
                        .withProcessed(false)
                        .withRetries(0)
                        .withSuccessful(true)
                        .withIgnore(false)
                        .build(),
                newPausEntry()
                        .withId(414538)
                        .withSentOn(createDate(2024, SEPTEMBER, 25, 10, 10, 19))
                        .withTenant(82)
                        .withPackageNumber("240925091082100")
                        .withErrorMessage(null)
                        .withException(null)
                        .withProcessed(true)
                        .withRetries(2)
                        .withSuccessful(false)
                        .withIgnore(false)
                        .build(),
                newPausEntry()
                        .withId(414537)
                        .withSentOn(createDate(2024, SEPTEMBER, 25, 10, 9, 32))
                        .withTenant(82)
                        .withPackageNumber("240925433682201")
                        .withErrorMessage(null)
                        .withException(null)
                        .withProcessed(true)
                        .withRetries(0)
                        .withSuccessful(true)
                        .withIgnore(false)
                        .build(),
                newPausEntry()
                        .withId(414515)
                        .withSentOn(createDate(2024, SEPTEMBER, 25, 9, 33, 48))
                        .withTenant(82)
                        .withPackageNumber("240925433582201")
                        .withErrorMessage(null)
                        .withException(null)
                        .withProcessed(true)
                        .withRetries(0)
                        .withSuccessful(true)
                        .withIgnore(false)
                        .build(),
                newPausEntry()
                        .withId(414511)
                        .withSentOn(createDate(2024, SEPTEMBER, 25, 9, 28, 16))
                        .withTenant(82)
                        .withPackageNumber("240925082682100")
                        .withErrorMessage(null)
                        .withException(null)
                        .withProcessed(true)
                        .withRetries(0)
                        .withSuccessful(true)
                        .withIgnore(false)
                        .build(),
                newPausEntry()
                        .withId(414497)
                        .withSentOn(createDate(2024, SEPTEMBER, 25, 9, 8, 13))
                        .withTenant(82)
                        .withPackageNumber("240925081082100")
                        .withErrorMessage(null)
                        .withException(null)
                        .withProcessed(true)
                        .withRetries(0)
                        .withSuccessful(true)
                        .withIgnore(false)
                        .build(),
                newPausEntry()
                        .withId(414488)
                        .withSentOn(createDate(2024, SEPTEMBER, 25, 8, 56, 45))
                        .withTenant(82)
                        .withPackageNumber("240925432782201")
                        .withErrorMessage(null)
                        .withException(null)
                        .withProcessed(true)
                        .withRetries(0)
                        .withSuccessful(true)
                        .withIgnore(false)
                        .build(),
                newPausEntry()
                        .withId(414481)
                        .withSentOn(createDate(2024, SEPTEMBER, 25, 8, 53, 32))
                        .withTenant(82)
                        .withPackageNumber("240925433482201")
                        .withErrorMessage(null)
                        .withException(null)
                        .withProcessed(true)
                        .withRetries(0)
                        .withSuccessful(true)
                        .withIgnore(false)
                        .build());

        assertEquals(asString(entries), response.getBody(), NON_EXTENSIBLE);

    }

    @Test
    public void shouldGetFile() throws IOException {

        ResponseEntity<byte[]> response = call("/paus/entries/414563", byte[].class);

        byte[] content = getContentOf("240925433782201.xml");

        assertThat(response.getHeaders().getContentType(), is(MediaType.APPLICATION_XML));
        assertThat(response.getBody(), is(content));

    }

    private byte[] getContentOf(String filename) throws IOException {

        return IOUtils.toByteArray(this.getClass()
                .getResourceAsStream("/ch/glastroesch/hades/presentation/" + filename));

    }

}
