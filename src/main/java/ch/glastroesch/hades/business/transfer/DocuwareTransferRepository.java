package ch.glastroesch.hades.business.transfer;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocuwareTransferRepository extends JpaRepository<DocuwareTransfer, Long> {

    @Query("select u from DocuwareTransfer u "
            + "where u.startedOn >= :startDate "
            + "and u.startedOn <= :endDate "
            + "order by u.startedOn DESC")
    List<DocuwareTransfer> findByDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("select u from DocuwareFile u "
            + "where u.id = :id ")
    DocuwareFile findFileById(@Param("id") Long id);

    @Query("select u from DocuwareFile u "
            + "where u.transfer.id = :id ")
    List<DocuwareFile> findFiles(@Param("id") Long id);

   @Query("select u from DocuwareTransfer u "
            + "left join fetch u.files "
            + "order by u.id desc "
            + "limit 1")
    DocuwareTransfer findNewest();

}
