package ch.glastroesch.hades.business.paus;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PausRepository extends CrudRepository<PausEntry, Long> {

   

    @Query("SELECT u FROM PausEntry u WHERE u.tenant"
            + " = :tenant ORDER BY u.sentOn DESC")
    Page<PausEntry> findnewest(@Param("tenant") int tenant, Pageable pageable);

}

