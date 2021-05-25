package com.superchat.messaging.dataaccessobject;

import com.superchat.messaging.domainobject.ConversationDO;

/**
 * Database Access Object for conversation table.
 * <p/>
 */
public interface ConversationRepository extends QuerydslRepository<ConversationDO, Long>
{
}
