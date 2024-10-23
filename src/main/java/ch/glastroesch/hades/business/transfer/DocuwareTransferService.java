package ch.glastroesch.hades.business.transfer;

import static ch.glastroesch.hades.business.common.TestableDate.currentDate;
import ch.glastroesch.hades.business.mail.MailService;
import java.util.Calendar;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocuwareTransferService {

    @Autowired
    DocuwareTransferRepository docuwareTransferRepository;

    @Autowired
    MailService mailService;

    public List<DocuwareTransfer> getNewestEntries() {
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

        return docuwareTransferRepository.findByDateBetween(startDate, endDate);
    }

    public List<DocuwareFile> getFiles(Long id) {
        return docuwareTransferRepository.findFiles(id);
    }

    public DocuwareFile getDocuwareFile(Long id) {
        return docuwareTransferRepository.findFileById(id);

    }

    public void checkForError() {
        DocuwareTransfer docuwareTransfer = docuwareTransferRepository.findNewest();
        if (docuwareTransfer.getFiles().isEmpty()) {
            mailService.send("m.andrijanic@glastroesch.ch", "DocuwareTransfer", "no files created");
        }
        if (docuwareTransfer.getErrorMessage() != null) {
            mailService.send("m.andrijanic@glastroesch.ch", "DocuwareTransfer", docuwareTransfer.getErrorMessage());
    }

    }
    

}
