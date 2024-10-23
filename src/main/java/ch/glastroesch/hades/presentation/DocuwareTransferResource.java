package ch.glastroesch.hades.presentation;

import ch.glastroesch.hades.business.transfer.DocuwareFile;
import ch.glastroesch.hades.business.transfer.DocuwareTransfer;
import ch.glastroesch.hades.business.transfer.DocuwareTransferService;
import ch.glastroesch.hades.business.transfer.FinanceTransfer;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "DocuwareTransfer", description = "endpoints for DocuwareTransfer")
@RestController
@RequestMapping("transfer/docuware")
public class DocuwareTransferResource {

    @Autowired
    DocuwareTransferService docuwareTransferService;

    @Autowired
    HttpServletResponse response;

    @GetMapping("/entries")
    public List<DocuwareTransfer> getNewestEntries() {
        return docuwareTransferService.getNewestEntries();
    }

    @GetMapping("/entries/{id}")
    public List<DocuwareFile> getFiles(@PathVariable Long id) {
        return docuwareTransferService.getFiles(id);
    }

    @GetMapping(value = "/files/csv/{id}", produces = {"text/csv"})
    public byte[] getDocuwareFileCsv(@PathVariable Long id) {
        DocuwareFile file = docuwareTransferService.getDocuwareFile(id);
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        return file.getContent();

    }

    @GetMapping(value = "/files/pdf/{id}", produces = {"application/pdf"})
    public byte[] getDocuwareFilePdf(@PathVariable Long id) {
        DocuwareFile file = docuwareTransferService.getDocuwareFile(id);
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        return file.getContent();

    }

    @PostMapping("/check")
    public void check() {
        docuwareTransferService.checkForError();
    }

}
