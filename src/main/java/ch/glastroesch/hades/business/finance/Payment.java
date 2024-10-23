package ch.glastroesch.hades.business.finance;

import ch.glastroesch.hades.business.common.AbstractEntity;
import static ch.glastroesch.hades.business.common.TestableDate.currentDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "Payment")
public class Payment extends AbstractEntity {

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Column(name = "company")
    private String company;

    @Column(name = "invoiceNumber")
    private String invoiceNumber;

    @Column(name = "customerNumber")
    private String customerNumber;

    @Column(name = "accountingYear")
    private Integer accountingYear;

    @Column(name = "orderNo")
    private String orderNo;

    @Column(name = "downPaymentNumber")
    private String downPaymentNumber;

    @Column(name = "amount")
    private double amount;

    @Column(name = "mixedPaymentId")
    private long mixedPaymentId;

    @Column(name = "errorMessage")
    private String errorMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unpaidEntryId", referencedColumnName = "id")
    private OpenPaymentEntry entry;

    public Payment() { 
        // fro jpa
    }

    
    
    public Payment(PaymentType type) {
        this.type = type;
    }

    public PaymentType getType() {
        return type;
    }

    public String getCompany() {
        return company;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setEntry(OpenPaymentEntry entry) {
        this.entry = entry;
    }

    public long getMixedPaymentId() {
        return mixedPaymentId;
    }

    public void setMixedPaymentId(long mixedPaymentId) {
        this.mixedPaymentId = mixedPaymentId;
    }

    public Integer getAccountingYear() {
        return accountingYear;
    }

    public static PaymentBuilder newPayment(PaymentType type) {

        return new PaymentBuilder(type);

    }

    public static class PaymentBuilder {

        Payment payment;

        public PaymentBuilder(PaymentType type) {
            payment = new Payment(type);
        }

        public PaymentBuilder withCompany(String company) {
            payment.company = company;
            return this;
        }

        public PaymentBuilder withInvoiceNumber(String invoiceNumber) {
            payment.invoiceNumber = invoiceNumber;
            return this;
        }

        public PaymentBuilder withCustomerNumber(String customerNumber) {
            payment.customerNumber = customerNumber;
            return this;
        }

        public PaymentBuilder withAmount(double amount) {
            payment.amount = amount;
            return this;
        }

        public PaymentBuilder withErrorMessage(String errorMessage) {
            payment.errorMessage = errorMessage;
            return this;
        }

        public PaymentBuilder withMixedPaymentId(long mixedPaymentId) {
            payment.mixedPaymentId = mixedPaymentId;
            return this;
        }

        public PaymentBuilder withAccountingYear(Integer accountingYear) {
            payment.accountingYear = accountingYear;
            return this;
        }

        public PaymentBuilder withOrderNo(String orderNo) {
            payment.orderNo = orderNo;
            return this;
        }

        public PaymentBuilder withDownPaymentNumber(String downPaymentNumber) {
            payment.downPaymentNumber = downPaymentNumber;
            return this;
        }

        public Payment build() {
            return payment;
        }

    }

}
