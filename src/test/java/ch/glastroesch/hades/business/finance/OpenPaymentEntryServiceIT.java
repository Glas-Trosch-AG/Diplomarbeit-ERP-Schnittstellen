package ch.glastroesch.hades.business.finance;

import ch.glastroesch.Application;
import ch.glastroesch.hades.business.mail.MailService;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.ArgumentCaptor.forClass;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class OpenPaymentEntryServiceIT {

    @Autowired
    OpenPaymentEntryService openPaymentEntryService;

    @Test
    public void shouldSendMail() {

        MailService mailService = mock(MailService.class);
        openPaymentEntryService.mailService = mailService;
        
        openPaymentEntryService.checkForError();

        ArgumentCaptor<String> addressCaptor = forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = forClass(String.class);
        ArgumentCaptor<String> textCaptor = forClass(String.class);

        verify(mailService).send(addressCaptor.capture(), subjectCaptor.capture(), textCaptor.capture());

        assertThat(addressCaptor.getValue(), is("m.andrijanic@glastroesch.ch"));
        assertThat(subjectCaptor.getValue(), is("OpenPayments"));
        assertThat(textCaptor.getValue(), is("""
                                             /ORA-20110: no customer found for 020947
                                             ORA-06512: in "IFSAPP.GT_COMMON", Zeile 221
                                             ORA-06512: in "IFSAPP.GT_PAYMENT", Zeile 119
                                             ORA-06512: in Zeile 1
                                             /ORA-20110: no customer found for 020388
                                             ORA-06512: in "IFSAPP.GT_COMMON", Zeile 221
                                             ORA-06512: in "IFSAPP.GT_PAYMENT", Zeile 119
                                             ORA-06512: in Zeile 1
                                             """));

    }

}
