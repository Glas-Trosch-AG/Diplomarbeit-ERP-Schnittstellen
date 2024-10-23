package ch.glastroesch.hades.business.finance;

import ch.glastroesch.hades.business.common.AbstractEntity;
import static ch.glastroesch.hades.business.common.TestableDate.currentDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "OpenPaymentEntry")
public class OpenPaymentEntry extends AbstractEntity implements Serializable {

    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "readOn")
    private Date readOn;

    @Expose
    @Lob
    @Column(name = "filename")
    private String filename;

    
    @JsonIgnore
    @Lob
    @Column(name = "content")
    private String content;

    @Expose
    @Lob
    @Column(name = "errorMessage")
    private String errorMessage;

    @Expose
    @Column(name = "successful")
    private boolean successful;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
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
        this.successful = false;

    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReadOn() {
        return readOn;
    }

    public void setReadOn(Date readOn) {
        this.readOn = readOn;
    }

    public static UnpaidExportEntryBuilder newOpenPaymentEntry() {
        return new UnpaidExportEntryBuilder();
    }

    public static class UnpaidExportEntryBuilder {

        OpenPaymentEntry entry;

        public UnpaidExportEntryBuilder() {
            entry = new OpenPaymentEntry();
            entry.readOn = currentDate();
            entry.successful = true;
        }

        public UnpaidExportEntryBuilder withId(long id) {
            entry.id = id;
            return this;
        }

        public UnpaidExportEntryBuilder withReadOn(Date readOn) {
            entry.readOn = readOn;
            return this;
        }

        public UnpaidExportEntryBuilder withFilename(String filename) {
            entry.filename = filename;
            return this;
        }

        public UnpaidExportEntryBuilder withErrorMessage(String errorMessage) {
            entry.errorMessage = errorMessage;
            return this;
        }

        public UnpaidExportEntryBuilder withSuccessful(boolean successful) {
            entry.successful = successful;
            return this;
        }

        public OpenPaymentEntry build() {
            return entry;
        }

    }
}
