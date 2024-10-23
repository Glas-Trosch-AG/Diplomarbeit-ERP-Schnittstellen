package ch.glastroesch.hades.business.currency;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CurrencyEntryRepository extends CrudRepository<CurrencyEntry, String> {

    @Query("select u from CurrencyEntry u "
            + "where u.date >= :startDate "
            + "and u.date <= :endDate "
            + "order by u.date desc")
    List<CurrencyEntry> findByDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("Select u from CurrencyEntry u "
            + "where u.filename = :filename "
            + "order by u.date desc")
    List<CurrencyEntry> findByFilename(@Param("filename") String filename,Pageable pageable);

}
