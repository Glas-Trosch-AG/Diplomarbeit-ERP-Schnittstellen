package ch.glastroesch.hades.business.finance;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OpenPaymentEntryRepository extends CrudRepository<OpenPaymentEntry, String> {

    @Query("select u from OpenPaymentEntry u "
            + "where u.readOn between :startDate AND :endDate "
            + "order by u.readOn DESC")
    List<OpenPaymentEntry> findByReadOnBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("select u from OpenPaymentEntry u "
            + "where u.filename = :filename ")
    OpenPaymentEntry findByFilename(@Param(value = "filename") String filename);

    @Query("select u from OpenPaymentEntry u "
            + "order by u.id desc "
            + "limit 1")
    OpenPaymentEntry findNewest();

    
    @Query("select u from Payment u "
            + "where u.entry.id = :id "
            + "and u.errorMessage is not null "
            + "order by u.id ")
    List<Payment> findErrors(@Param(value = "id") Long id);
    
    
    
    
}
