package ch.glastroesch.hades.presentation;

import ch.glastroesch.hades.business.finance.OpenPaymentEntry;
import ch.glastroesch.hades.business.finance.OpenPaymentEntryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "OpenPaymentEntry", description = "endpoints for OpenPaymentEntry")
@RestController
@RequestMapping("openpayment")
public class OpenPaymentEntryResource {

    @Autowired
    private OpenPaymentEntryService openPaymentEntryService;

    
    @Autowired
    HttpServletResponse response;
   
    @GetMapping("/entries")
    public List<OpenPaymentEntry> getNewestEntries() {
        return openPaymentEntryService.getNewestEntries();
    }

   
    @GetMapping(value = "/files/{filename}", produces = {"text/csv"})
    public byte[] getFile(@PathVariable String filename) {
        OpenPaymentEntry entry = openPaymentEntryService.getFiles(filename);
        response.setHeader("Content-Disposition", "attachment;filename=" + entry.getFilename());
        return entry.getContent().getBytes();
    }
    
    
    @PostMapping("/check") 
    public void check() {
    openPaymentEntryService.checkForError();
    
    
    }
    
    
    
    
}