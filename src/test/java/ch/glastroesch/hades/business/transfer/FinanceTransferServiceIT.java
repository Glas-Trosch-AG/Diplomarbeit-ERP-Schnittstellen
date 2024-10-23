package ch.glastroesch.hades.business.transfer;

import ch.glastroesch.Application;
import static ch.glastroesch.hades.business.common.TestableDate.createDate;
import static ch.glastroesch.hades.business.common.TestableDate.setCurrentDate;
import ch.glastroesch.hades.business.mail.MailService;
import static java.util.Calendar.SEPTEMBER;
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
public class FinanceTransferServiceIT {
    
    @Autowired
    FinanceTransferService financeTransferService;
    
    @Test
    public void shouldSendMail() {

        setCurrentDate(createDate(2024, SEPTEMBER, 20, 7, 30, 10));
        
        MailService mailService = mock(MailService.class);
        financeTransferService.mailService = mailService;
        
        financeTransferService.checkForError();

        ArgumentCaptor<String> addressCaptor = forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = forClass(String.class);
        ArgumentCaptor<String> textCaptor = forClass(String.class);

        verify(mailService, times(3)).send(addressCaptor.capture(), subjectCaptor.capture(), textCaptor.capture());

        assertThat(addressCaptor.getAllValues().get(0), is("m.andrijanic@glastroesch.ch"));
        assertThat(subjectCaptor.getAllValues().get(0), is("FinanceTransfer 82"));
        assertThat(textCaptor.getAllValues().get(0), is("no files created"));
        
        assertThat(addressCaptor.getAllValues().get(1), is("m.andrijanic@glastroesch.ch"));
        assertThat(subjectCaptor.getAllValues().get(1), is("FinanceTransfer 82"));
        assertThat(textCaptor.getAllValues().get(1), is("no files were created"));
        
        assertThat(addressCaptor.getAllValues().get(2), is("m.andrijanic@glastroesch.ch"));
        assertThat(subjectCaptor.getAllValues().get(2), is("FinanceTransfer 82"));
        assertThat(textCaptor.getAllValues().get(2), is("no files created"));

    }
    
}
