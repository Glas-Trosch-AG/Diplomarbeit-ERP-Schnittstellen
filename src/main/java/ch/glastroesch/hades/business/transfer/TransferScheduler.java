
package ch.glastroesch.hades.business.transfer;

import ch.glastroesch.hades.business.finance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class TransferScheduler {
    
    @Autowired 
    DocuwareTransferService docuwareTransferService;
    
    @Autowired 
    FinanceTransferService financeTransferService;
    
    @Scheduled (cron = "0 0 20 * * ?")
    public void check () {
        docuwareTransferService.checkForError();
        financeTransferService.checkForError();
        
    }
    
}
