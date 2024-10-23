package ch.glastroesch.hades.business.currency;

import static ch.glastroesch.hades.business.common.TestableDate.currentDate;
import ch.glastroesch.hades.business.mail.MailService;
import ch.glastroesch.hades.business.record.RecordRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ch.glastroesch.hades.business.record.Record;
import java.util.Calendar;
import static java.util.Calendar.DATE;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class CurrencyEntryService {

    private static final Logger LOGGER = Logger.getLogger(CurrencyEntryService.class.getName());
    private static final String CURRENCY_URL = "https://static.nbp.pl/dane/kursy/xml/dir.txt";
    private static final String CURRENCY_FILE_URL = "http://static.nbp.pl/dane/kursy/xml";
    private static final String FILE_EXTENSION = "xml";
    private static final String COMPANY = "0057";
    private static final String REF_CURRENCY_CODE = "PLN";
    private static final String CURRENCY_TYPE = "1";

    @Autowired
    private CurrencyEntryRepository currencyEntryRepository;

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    MailService mailService;

    public List<CurrencyEntry> getAllCurrency() {
        return (List<CurrencyEntry>) currencyEntryRepository.findAll();
    }

    public void refreshCurrencies() {

        LOGGER.log(Level.INFO, "refresh currencies");

        String newestFile = findNewestFile();
        if (newestFile == null) {
            mailService.send("m.andrijanic@glastroesch.ch", "Currency Update (File not found)", "Newest file was not found on " + CURRENCY_URL);
            CurrencyEntry entry = new CurrencyEntry();
            entry.setErrorMessage("Newest file was not found on ");

            currencyEntryRepository.save(entry);
            throw new IllegalStateException("no matching file found at " + CURRENCY_URL);
        }

        LOGGER.log(Level.INFO, "newestFile: " + newestFile);

        RestTemplate restDocument = new RestTemplate();
        String content = restDocument.getForObject(CURRENCY_FILE_URL + "/" + newestFile + "." + FILE_EXTENSION, String.class);

        CurrencyEntry entry = new CurrencyEntry(newestFile, content);

        try {
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(content.getBytes()));

            NodeList nodeList = document.getElementsByTagName("pozycja");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                String currencyCode = element.getElementsByTagName("kod_waluty").item(0).getTextContent();
                String currencyRate = element.getElementsByTagName("kurs_sredni").item(0).getTextContent()
                        .replace(",", ".");
                String convFactor = element.getElementsByTagName("przelicznik").item(0).getTextContent();

                Record record = new Record("currency_rate");
                record.add("COMPANY", COMPANY);
                record.add("CURRENCY_CODE", currencyCode);
                record.add("VALID_FROM", currentDate());
                try {
                    if (!recordRepository.exists(record)) {
                        LOGGER.log(Level.INFO, "updating currency for " + currencyCode);
                        createEntry(currencyCode, currencyRate, currentDate(), convFactor);
                    }
                } catch (SQLException e) {

                    entry.addErrorMessage(e.getMessage());
                }

            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            entry.addErrorMessage(e.getMessage());

        }
        if (entry.getErrorMessage() != null) {
            mailService.send("m.andrijanic@glastroesch.ch", "Currency Update (IFS)", entry.getErrorMessage());
        }
        currencyEntryRepository.save(entry);

    }

    private void createEntry(String currencyCode, String currencyRate, Date validFrom, String convFactor) throws SQLException {

        Record record = new Record("currency_rate");

        record.add("COMPANY", COMPANY);
        record.add("CURRENCY_TYPE", CURRENCY_TYPE);
        record.add("REF_CURRENCY_CODE", REF_CURRENCY_CODE);
        record.add("CONV_FACTOR", convFactor);

        record.add("CURRENCY_CODE", currencyCode);
        record.add("CURRENCY_RATE", currencyRate);
        record.add("VALID_FROM", validFrom);

        recordRepository.save(record);

    }

    public List<CurrencyEntry> getNewestEntries() {

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

        return currencyEntryRepository.findByDateBetween(startDate, endDate);

    }

    public byte[] getPackageFile(String filename) {
        List<CurrencyEntry> entries = currencyEntryRepository.findByFilename(filename, PageRequest.of(0, 1));

        if (!entries.isEmpty()) {
            return entries.get(0).getContent().getBytes();
        }
        return null;

    }

    private String findNewestFile() {

        RestTemplate restFiles = new RestTemplate();
        String fileList = null;
        try {

            fileList = restFiles.getForObject(CURRENCY_URL, String.class);
        } catch (final HttpClientErrorException e) {

            LOGGER.log(Level.SEVERE, "update currencies: " + e.getStatusCode(), e);
            LOGGER.log(Level.SEVERE, "update currencies: " + e.getResponseBodyAsString(), e);
            return null;
        }

        String lastFile = null;
        String[] files = fileList.split("\r\n");
        for (int i = files.length - 1; i >= 0; i--) {
            String file = files[i];
            if (file.startsWith("a")) {
                lastFile = file;
                break;
            }
        }

        return lastFile;

    }

}
