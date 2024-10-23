package ch.glastroesch.hades.business.transfer;

import ch.glastroesch.Application;
import ch.glastroesch.hades.business.mail.MailService;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class DocuwareTransferServiceIT {

    @Autowired
    DocuwareTransferService docuwareTransferService;

    @Test
    public void shouldSendMail() {

        MailService mailService = mock(MailService.class);
        docuwareTransferService.mailService = mailService;

        docuwareTransferService.checkForError();

        ArgumentCaptor<String> addressCaptor = forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = forClass(String.class);
        ArgumentCaptor<String> textCaptor = forClass(String.class);

        verify(mailService).send(addressCaptor.capture(), subjectCaptor.capture(), textCaptor.capture());

        assertThat(addressCaptor.getValue(), is("m.andrijanic@glastroesch.ch"));
        assertThat(subjectCaptor.getValue(), is("DocuwareTransfer"));
        assertThat(textCaptor.getValue(), is("folder not ready"));

    }

}
