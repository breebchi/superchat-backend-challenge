package com.superchat.messaging.dataaccessobject;

import com.superchat.messaging.domainobject.ContactDO;
import com.superchat.messaging.domainobject.QContactDO;

import java.util.Optional;

/**
 * Database Access Object for contact table.
 * <p/>
 */
public interface ContactRepository extends QuerydslRepository<ContactDO, Long>
{
    QContactDO qContactDO = QContactDO.contactDO;

    Optional<ContactDO> findByNameIgnoreCase(String name);

    Optional<ContactDO> findByEmailIgnoreCase(String email);
}
