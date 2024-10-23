package ch.glastroesch.hades.business.paus;

import ch.glastroesch.hades.business.common.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.xml.bind.annotation.XmlTransient;
import java.util.Date;

@Entity
@Table(name = "PausEntry")
public class PausEntry extends AbstractEntity {

    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sentOn")
    private Date sentOn;

    @Expose
    @Column(name = "tenant")
    private int tenant;

    @Expose
    @Column(name = "packageNumber")
    private String packageNumber;

    @JsonIgnore
    @Lob
    @Column(name = "file")
    private byte[] file;

    @Expose
    @Column(name = "errorMessage")
    private String errorMessage;

    @Expose
    @Column(name = "exception")
    private String exception;

    @Expose
    @Column(name = "processed")
    private boolean processed;

    @Expose
    @Column(name = "retries")
    private int retries;

    @Expose
    @Column(name = "successful")
    private boolean successful;

    @Expose
    @Column(name = "\"ignore\"")
    private boolean ignore;

    public PausEntry() {
        // for jpa
    }

    public Date getSentOn() {
        return sentOn;
    }

    public void setSentOn(Date sentOn) {
        this.sentOn = sentOn;
    }

    public int getTenant() {
        return tenant;
    }

    public void setTenant(int tenant) {
        this.tenant = tenant;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public static PausEntryBuilder newPausEntry() {
        return new PausEntryBuilder();
    }

    public static class PausEntryBuilder {

        PausEntry entry;

        public PausEntryBuilder() {
            entry = new PausEntry();
        }

        public PausEntryBuilder withId(long id) {
            entry.id = id;
            return this;
        }

        public PausEntryBuilder withSentOn(Date sentOn) {
            entry.sentOn = sentOn;
            return this;
        }

        public PausEntryBuilder withTenant(int tenant) {
            entry.tenant = tenant;
            return this;
        }

        public PausEntryBuilder withPackageNumber(String packageNumber) {
            entry.packageNumber = packageNumber;
            return this;
        }

        public PausEntryBuilder withFile(byte[] file) {
            entry.file = file;
            return this;
        }

        public PausEntryBuilder withErrorMessage(String errorMessage) {
            entry.errorMessage = errorMessage;
            return this;
        }

        public PausEntryBuilder withException(String exception) {
            entry.exception = exception;
            return this;
        }

        public PausEntryBuilder withProcessed(boolean processed) {
            entry.processed = processed;
            return this;
        }

        public PausEntryBuilder withRetries(int retries) {
            entry.retries = retries;
            return this;
        }

        public PausEntryBuilder withSuccessful(boolean successful) {
            entry.successful = successful;
            return this;
        }

        public PausEntryBuilder withIgnore(boolean ignore) {
            entry.ignore = ignore;
            return this;
        }

        public PausEntry build() {
            return entry;
        }

    }

}
