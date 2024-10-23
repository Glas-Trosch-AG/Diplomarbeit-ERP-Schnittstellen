package ch.glastroesch.hades.presentation;

import ch.glastroesch.hades.business.currency.CurrencyEntry;
import ch.glastroesch.hades.business.currency.CurrencyEntryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Currency", description = "endpoints for currency")
@RestController
@RequestMapping("currency")
public class CurrencyResource {

    @Autowired
    CurrencyEntryService currencyEntryService;

    @GetMapping("/entries")
    public List<CurrencyEntry> getNewestEntries() {

        List<CurrencyEntry> entries = currencyEntryService.getNewestEntries();
        
        System.out.println("id = " + entries.get(1).getId());
        System.out.println("first date = " + entries.get(1).getDate());
        
        return entries;

    }

    @GetMapping(value = "/entries/files/{filename}", produces = {"application/xml"})
    public byte[] getPackageFile(@PathVariable("filename") String filename) {

        return currencyEntryService.getPackageFile(filename);

    }

    @PostMapping("/update")
    public void refreshCurrencies() {
        currencyEntryService.refreshCurrencies();
    }

}
