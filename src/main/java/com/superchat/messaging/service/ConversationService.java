package com.superchat.messaging.service;

import com.superchat.messaging.domainobject.ConversationDO;
import com.superchat.messaging.domainvalue.ContactIdentifierType;
import com.superchat.messaging.domainvalue.Message;
import com.superchat.messaging.exception.ConstraintsViolationException;
import com.superchat.messaging.exception.EntityNotFoundException;

import java.util.List;

public interface ConversationService
{
    ConversationDO find(long contactId) throws EntityNotFoundException;

    List<ConversationDO> find();

    ConversationDO sendMessage(long contactId, Message message) throws EntityNotFoundException;

    ConversationDO sendMessage(ContactIdentifierType contactIdentifierType, String contactIdentifier, Message message) throws EntityNotFoundException,
        ConstraintsViolationException;
}
