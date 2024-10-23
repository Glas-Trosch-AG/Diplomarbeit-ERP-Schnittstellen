package ch.glastroesch.hades.business.transfer;

import ch.glastroesch.hades.business.common.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ProtocolEntry")
public class ProtocolEntry extends AbstractEntity {

    @Column(name = "tenant")
    private Integer tenant;

    @Column(name = "read_on")
    private Date readOn;

    @Column(name = "application_message_id")
    private Long applicationMessageId;

    @Column(name = "message_text")
    private String messageText;

    @Lob
    @Column(name = "protocol")
    private byte[] protocol;

    @Column(name = "error_message")
    private String errorMessage;

    public ProtocolEntry() {
        // for jpa
    }

    public Integer getTenant() {
        return tenant;
    }

    public void setTenant(Integer tenant) {
        this.tenant = tenant;
    }

    public Date getReadOn() {
        return readOn;
    }

    public void setReadOn(Date readOn) {
        this.readOn = readOn;
    }

    public Long getApplicationMessageId() {
        return applicationMessageId;
    }

    public void setApplicationMessageId(Long applicationMessageId) {
        this.applicationMessageId = applicationMessageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public byte[] getProtocol() {
        return protocol;
    }

    public void setProtocol(byte[] protocol) {
        this.protocol = protocol;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
