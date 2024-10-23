package ch.glastroesch.hades.business.currency;

import ch.glastroesch.Application;
import ch.glastroesch.hades.business.mail.MailService;
import ch.glastroesch.hades.business.record.RecordRepository;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ch.glastroesch.hades.business.record.Record;
import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class CurrencyEntryServiceIT {

    @Autowired
    CurrencyEntryService currencyEntryService;

    @Test
    public void shouldSendMail() throws SQLException {

        RecordRepository recordRepository = mock(RecordRepository.class);
        currencyEntryService.recordRepository = recordRepository;
        MailService mailService = mock(MailService.class);
        currencyEntryService.mailService = mailService;

        String errorMessage = """
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
            ORA-06512: in Zeile 1""";

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) throws SQLException {

                Record record = (Record) invocation.getArguments()[0];
                if (record.getValue("CURRENCY_CODE").equals("USD")) {
                    throw new SQLException(errorMessage);
                }
                return null;
            }
        }).when(recordRepository).save(any(Record.class));

        currencyEntryService.refreshCurrencies();

        ArgumentCaptor<String> addressCaptor = forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = forClass(String.class);
        ArgumentCaptor<String> textCaptor = forClass(String.class);

        verify(mailService).send(addressCaptor.capture(), subjectCaptor.capture(), textCaptor.capture());

        assertThat(addressCaptor.getValue(), is("m.andrijanic@glastroesch.ch"));
        assertThat(subjectCaptor.getValue(), is("Currency Update (IFS)"));
        assertThat(textCaptor.getValue(), is("/" + errorMessage));

    }

}
