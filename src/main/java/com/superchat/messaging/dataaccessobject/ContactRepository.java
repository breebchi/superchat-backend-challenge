package com.superchat.messaging.dataaccessobject;

import com.superchat.messaging.domainobject.ContactDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Database Access Object for contact table.
 * <p/>
 */
@Repository
public interface ContactRepository extends JpaRepository<ContactDO, Long>
{
    Optional<ContactDO> findByNameIgnoreCase(String name);

    Optional<ContactDO> findByEmailIgnoreCase(String email);

    @Query("SELECT p FROM ContactDO p WHERE searchContactNames(:text) = true")
    List<ContactDO> searchContactNames(@Param("text") String text, String local);
}
