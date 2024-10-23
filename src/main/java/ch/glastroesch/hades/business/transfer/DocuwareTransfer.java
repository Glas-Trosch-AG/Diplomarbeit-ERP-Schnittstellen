
package ch.glastroesch.hades.business.transfer;

import ch.glastroesch.hades.business.common.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "DocuwareTransfer")
public class DocuwareTransfer extends AbstractEntity{

    @Expose
    @Column(name = "startedOn")
    private Date startedOn;
    
    @Expose
    @Column(name = "finishedOn")
    private Date finishedOn;

    @Expose
    @Column(name = "errorMessage")
    private String errorMessage;

    @JsonIgnore
    @OneToMany(mappedBy = "transfer", cascade = CascadeType.ALL)
    private List<DocuwareFile> files;
    
    public DocuwareTransfer() {
        // for jpa
    }

    public Date getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(Date startedOn) {
        this.startedOn = startedOn;
    }

    public Date getFinishedOn() {
        return finishedOn;
    }

    public void setFinishedOn(Date finishedOn) {
        this.finishedOn = finishedOn;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<DocuwareFile> getFiles() {
        return files;
    }

    public void setFiles(List<DocuwareFile> files) {
        this.files = files;
    }
    public static DocuwareTransferBuilder newDocuwareTransfer() {
        return new DocuwareTransferBuilder();
    }
 
    public static class DocuwareTransferBuilder {
 
        DocuwareTransfer transfer;
 
        public DocuwareTransferBuilder() {
            transfer = new DocuwareTransfer();
        }
 
        public DocuwareTransferBuilder withId(long id) {
            transfer.id = id;
            return this;
        }
 
        public DocuwareTransferBuilder withStartedOn(Date startedOn) {
            transfer.startedOn = startedOn;
            return this;
        }
 
        public DocuwareTransferBuilder withFinishedOn(Date finishedOn) {
            transfer.finishedOn = finishedOn;
            return this;
        }
 
        public DocuwareTransferBuilder withErrorMessage(String errorMessage) {
            transfer.errorMessage = errorMessage;
            return this;
        }
 
        public DocuwareTransfer build() {
            return transfer;
        }
 
    }
}

