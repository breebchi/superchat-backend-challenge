package com.superchat.messaging.dataaccessobject;

import com.superchat.messaging.domainobject.ConversationDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Database Access Object for conversation table.
 * <p/>
 */
@Repository
public interface ConversationRepository extends JpaRepository<ConversationDO, Long>
{
}
