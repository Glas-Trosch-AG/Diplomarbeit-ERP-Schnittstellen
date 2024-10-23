package ch.glastroesch.hades.business.transfer;

import ch.glastroesch.hades.business.common.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FinanceTransfer")
public class FinanceTransfer extends AbstractEntity {

    @Expose
    @Enumerated(EnumType.STRING)
    private FinanceTransferType type;

    @Expose
    @Column(name = "startedOn")
    private Date startedOn;

    @Expose
    @Column(name = "finishedOn")
    private Date finishedOn;

    @Expose
    @Column(name = "tenant")
    private Integer tenant;

    @Expose
    @Column(name = "errorMessage")
    private String errorMessage;

    @JsonIgnore
    @OneToMany(mappedBy = "transfer", cascade = CascadeType.ALL)
    private List<FinanceFile> files;

    public FinanceTransfer() {
        //for jpa

    }

    public FinanceTransferType getType() {
        return type;
    }

    public void setType(FinanceTransferType type) {
        this.type = type;
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

    public Integer getTenant() {
        return tenant;
    }

    public void setTenant(Integer tenant) {
        this.tenant = tenant;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<FinanceFile> getFiles() {
        return files;
    }

    public void setFiles(List<FinanceFile> files) {
        this.files = files;
    }

    public static FinanceTransferBuilder newFinanceTransfer() {
        return new FinanceTransferBuilder();
    }

    public static class FinanceTransferBuilder {

        FinanceTransfer transfer;

        public FinanceTransferBuilder() {
            transfer = new FinanceTransfer();
        }

        public FinanceTransferBuilder withId(long id) {
            transfer.id = id;
            return this;
        }

        public FinanceTransferBuilder withType(FinanceTransferType type) {
            transfer.type = type;
            return this;
        }

        public FinanceTransferBuilder withStartedOn(Date startedOn) {
            transfer.startedOn = startedOn;
            return this;
        }

        public FinanceTransferBuilder withFinishedOn(Date finishedOn) {
            transfer.finishedOn = finishedOn;
            return this;
        }

        public FinanceTransferBuilder withTenant(int tenant) {
            transfer.tenant = tenant;
            return this;
        }

        public FinanceTransferBuilder withErrorMessage(String errorMessage) {
            transfer.errorMessage = errorMessage;
            return this;
        }

        public FinanceTransfer build() {
            return transfer;
        }

    }

}
