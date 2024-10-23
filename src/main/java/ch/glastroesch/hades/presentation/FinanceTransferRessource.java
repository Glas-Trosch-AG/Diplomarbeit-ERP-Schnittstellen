package ch.glastroesch.hades.presentation;

import ch.glastroesch.hades.business.transfer.FinanceFile;
import ch.glastroesch.hades.business.transfer.FinanceTransfer;
import ch.glastroesch.hades.business.transfer.FinanceTransferService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "FinanceTransfer", description = "endpoints for FinanceTransfer")
@RestController
@RequestMapping("transfer/finance")
public class FinanceTransferRessource {

    @Autowired
    FinanceTransferService financeTransferService;

    @Autowired
    HttpServletResponse response;

    @GetMapping("/entries")
    public List<FinanceTransfer> getNewestEntries() {
        return financeTransferService.getNewestEntries();
    }

    @GetMapping("/entries/{id}")
    public List<FinanceFile> getFiles(@PathVariable Long id) {
        return financeTransferService.getFiles(id);
    }

    @GetMapping(value = "/files/csv/{filename}", produces = {"text/csv"})
    public byte[] getFinanceFileCsv(@PathVariable String filename) {
        FinanceFile file = financeTransferService.getFinanceFile(filename);
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        return file.getContent();

    }

    @GetMapping(value = "/files/pdf/{filename}", produces = {"application/pdf"})
    public byte[] getFinanceFilePdf(@PathVariable String filename) {
        FinanceFile file = financeTransferService.getFinanceFile(filename);
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        return file.getContent();

    }

    @PostMapping("/check")
    public void check() {
        financeTransferService.checkForError();
    }
}
