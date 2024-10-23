package ch.glastroesch.hades.presentation.archive;

import ch.glastroesch.hades.business.record.Record;
import ch.glastroesch.hades.business.record.RecordRepository;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/records")
public class RecordServiceResource {
    
    @Autowired
    RecordRepository recordRepository;

    @PostMapping("/new")
    public void newRecord() throws SQLException {

        Record record = new Record("currency_rate");
        record.add("COMPANY", "0057");
        record.add("CURRENCY_TYPE", "1");
        record.add("REF_CURRENCY_CODE", "PLN");
        record.add("CURRENCY_CODE", "USD");
        record.add("CURRENCY_RATE", "77");
        record.add("VALID_FROM", "2023-11-28-00.00.00");
        record.add("CONV_FACTOR", "1");

        recordRepository.save(record);

    }

}
