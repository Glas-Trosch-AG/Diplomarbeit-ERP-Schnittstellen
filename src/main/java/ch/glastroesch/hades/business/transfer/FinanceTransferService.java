package ch.glastroesch.hades.business.transfer;

import static ch.glastroesch.hades.business.common.TestableDate.currentDate;
import ch.glastroesch.hades.business.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.util.Calendar;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import java.util.Date;
import java.util.List;

@Service
public class FinanceTransferService {

    @Autowired
    FinanceTransferRepository financeTransferRepository;

    @Autowired
    MailService mailService;

    public List<FinanceTransfer> getNewestEntries() {
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

        return financeTransferRepository.findByDateBetween(startDate, endDate);
    }

    public List<FinanceFile> getFiles(Long id) {
        return financeTransferRepository.findFiles(id);
    }

    public FinanceFile getFinanceFile(String filename) {
        return financeTransferRepository.findByFilename(filename);

    }

    public void checkForError() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate());

        calendar.add(Calendar.DATE, -1);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date startDate = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        Date endDate = calendar.getTime();
        List<FinanceTransfer> financeTransfers = financeTransferRepository.findNewest(startDate, endDate);

        for (FinanceTransfer financeTransfer : financeTransfers) {
            if (financeTransfer.getFiles().isEmpty()) {

                mailService.send("m.andrijanic@glastroesch.ch", "FinanceTransfer " + financeTransfer.getTenant(), "no files created");
            }
            if (financeTransfer.getErrorMessage() != null) {
                mailService.send("m.andrijanic@glastroesch.ch", "FinanceTransfer " + financeTransfer.getTenant(), financeTransfer.getErrorMessage());
            }
        }

    }

}
