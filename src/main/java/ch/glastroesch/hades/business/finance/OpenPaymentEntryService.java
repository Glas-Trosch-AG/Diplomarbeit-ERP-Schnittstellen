package ch.glastroesch.hades.business.finance;

import static ch.glastroesch.hades.business.common.TestableDate.currentDate;
import ch.glastroesch.hades.business.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import java.util.Date;
import java.util.List;

@Service
public class OpenPaymentEntryService {

    @Autowired
    OpenPaymentEntryRepository openPaymentEntryRepository;
    
    @Autowired
    MailService mailService;

   
    public List<OpenPaymentEntry> getNewestEntries() {
 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate());
 
        calendar.set(HOUR_OF_DAY, 23);
        calendar.set(MINUTE, 59);
        calendar.set(SECOND, 59);
        Date endDate = calendar.getTime();
 
        calendar.add(Calendar.DATE, -2);
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        Date startDate = calendar.getTime();
 
        return openPaymentEntryRepository.findByReadOnBetween(startDate, endDate);
    }

    public OpenPaymentEntry getFiles(String filename) {
        return openPaymentEntryRepository.findByFilename(filename);
    }

    public void checkForError() {
        OpenPaymentEntry openPaymentEntry = openPaymentEntryRepository.findNewest();
        List<Payment> payments = openPaymentEntryRepository.findErrors(openPaymentEntry.getId());
        String errorMessage = ""; 
        for ( Payment payment : payments) {
            errorMessage += "/" +payment.getErrorMessage();
        }
        if (!errorMessage.isEmpty()) {
            mailService.send("m.andrijanic@glastroesch.ch", "OpenPayments", errorMessage);
        }
    }
    
    
}



