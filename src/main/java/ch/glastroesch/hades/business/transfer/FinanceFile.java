package ch.glastroesch.hades.business.transfer;

import ch.glastroesch.hades.business.common.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "FinanceFile")
public class FinanceFile extends AbstractEntity {

    @Expose
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @Lob
    @Column(name = "content")
    private byte[] content;

    @Expose
    @Column(name = "errorMessage")
    private String errorMessage;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "transferId")
    private FinanceTransfer transfer;

    public FinanceFile() {
        //for jpa
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public FinanceTransfer getTransfer() {
        return transfer;
    }

    public void setTransfer(FinanceTransfer transfer) {
        this.transfer = transfer;
    }

    public static FinanceFileBuilder newFinanceFile() {
        return new FinanceFileBuilder();
    }

    public static class FinanceFileBuilder {

        FinanceFile file;

        public FinanceFileBuilder() {
            file = new FinanceFile();
        }

        public FinanceFileBuilder withId(long id) {
            file.id = id;
            return this;
        }

        public FinanceFileBuilder withName(String name) {
            file.name = name;
            return this;
        }

        public FinanceFileBuilder withErrorMessage(String errorMessage) {
            file.errorMessage = errorMessage;
            return this;
        }

        public FinanceFile build() {
            return file;
        }
    }

}
