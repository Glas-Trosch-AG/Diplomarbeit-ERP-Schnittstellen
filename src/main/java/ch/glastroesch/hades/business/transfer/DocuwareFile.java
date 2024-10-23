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
@Table(name = "DocuwareFile")
public class DocuwareFile extends AbstractEntity {

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
    private DocuwareTransfer transfer;

    public DocuwareFile() {
        // for jpa
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

    public DocuwareTransfer getTransfer() {
        return transfer;
    }

    public void setTransfer(DocuwareTransfer transfer) {
        this.transfer = transfer;
    }

  public static DocuwareFileBuilder newDocuwareFile() {
        return new DocuwareFileBuilder();
    }
 
    public static class DocuwareFileBuilder {
 
        DocuwareFile file;
 
        public DocuwareFileBuilder() {
            file = new DocuwareFile();
        }
 
        public DocuwareFileBuilder withId(long id) {
            file.id = id;
            return this;
        }
 
        public DocuwareFileBuilder withName(String name) {
            file.name = name;
            return this;
        }
 
        public DocuwareFileBuilder withErrorMessage(String errorMessage) {
            file.errorMessage = errorMessage;
            return this;
        }
 
        public DocuwareFile build() {
            return file;
        }
 
    }

}
