
package ch.glastroesch.hades.business.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class OpenPaymentScheduler {
    
    @Autowired 
    OpenPaymentEntryService openPaymentEntryService;
    
    @Scheduled (cron = "0 30 1 * * ?")
    public void check () {
        openPaymentEntryService.checkForError();
        
    }
    
}
