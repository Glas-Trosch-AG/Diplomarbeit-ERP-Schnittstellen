package ch.glastroesch.hades.business.transfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.List;

@Repository
public interface FinanceTransferRepository extends JpaRepository<FinanceTransfer, Long> {

    @Query("SELECT u FROM FinanceTransfer u "
            + "WHERE u.startedOn >= :startDate "
            + "AND u.startedOn <= :endDate "
            + "ORDER BY u.startedOn DESC")
    List<FinanceTransfer> findByDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT u FROM FinanceFile u "
            + "WHERE u.name = :filename ")
    FinanceFile findByFilename(@Param("filename") String filename);

    @Query("SELECT u FROM FinanceFile u "
            + "WHERE u.transfer.id = :id ")
    List<FinanceFile> findFiles(@Param("id") Long id);

    @Query("select u from FinanceTransfer u "
            + "left join fetch u.files "
            + "where u.startedOn >= :startDate "
            + "and u.startedOn <= :endDate "
            + "order by u.id desc ")
    List<FinanceTransfer> findNewest(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
