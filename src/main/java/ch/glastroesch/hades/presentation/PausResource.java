package ch.glastroesch.hades.presentation;

import ch.glastroesch.hades.business.paus.PausEntry;
import ch.glastroesch.hades.business.paus.PausService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "PAUS", description = "endpoints for paus")
@RestController
@RequestMapping("paus")
public class PausResource {

    @Autowired
    PausService pausService;

    @GetMapping("/companies/{company}/entries")
    public List<PausEntry> getNewestPackages(@PathVariable("company") int company) {

        return pausService.getNewestPackages(company);

    }

    @GetMapping(value = "/entries/{id}", produces = {"application/xml"})
    public byte[] getPackageFile(@PathVariable("id") long id) {

        return pausService.getPackageFile(id);

    }
}
