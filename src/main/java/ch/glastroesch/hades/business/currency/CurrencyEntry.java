package ch.glastroesch.hades.business.currency;

import ch.glastroesch.hades.business.common.AbstractEntity;
import static ch.glastroesch.hades.business.common.TestableDate.currentDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "CurrencyEntry")
public class CurrencyEntry extends AbstractEntity {

    @Expose
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Expose
    @Column(name = "filename")
    private String filename;

    @Column(name = "content")
    @JsonIgnore
    private String content;

    @Expose
    @Column(name = "errorMessage")
    private String errorMessage;

    public CurrencyEntry() {
        // for jpa
    }

    public CurrencyEntry(String filename, String content) {

        this.filename = filename;
        this.content = content;

        this.date = currentDate();

    }

    public Date getDate() {
        return date;
    }

    public String getFilename() {
        return filename;
    }

    public String getContent() {
        return content;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public void addErrorMessage (String errorMessage) {
        if (this.errorMessage == null) {
            this.errorMessage = "";
        }
        this.errorMessage += "/" + errorMessage; 
        
    }
    public static CurrencyEntryBuilder newCurrencyEntry() {
        return new CurrencyEntryBuilder();
    }
 
    public static class CurrencyEntryBuilder {
 
        CurrencyEntry entry;
 
        public CurrencyEntryBuilder() {
            entry = new CurrencyEntry();
        }
 
        public CurrencyEntryBuilder withId(long id) {
            entry.id = id;
            return this;
        }
 
        public CurrencyEntryBuilder withDate(Date date) {
            entry.date = date;
            return this;
        }
 
        public CurrencyEntryBuilder withFilename(String filename) {
            entry.filename = filename;
            return this;
        }
 
        public CurrencyEntryBuilder withErrorMessage(String errorMessage) {
            entry.errorMessage = errorMessage;
            return this;
        }
 
        public CurrencyEntry build() {
            return entry;
        }
 
    }
}
