package ch.glastroesch.hades.business.paus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class PausService {

    @Autowired
    private PausRepository pausRepository;

    public List<PausEntry> getNewestPackages(int tenant) {
        Pageable pageable = PageRequest.of(0, 10);
        return pausRepository.findnewest(tenant, pageable).getContent();

    }

    public byte[] getPackageFile(long id) {
        PausEntry entry = pausRepository.findById(id).orElse(null);
        if (entry != null) {
            return entry.getFile();
        }
        return null;
    }
}
