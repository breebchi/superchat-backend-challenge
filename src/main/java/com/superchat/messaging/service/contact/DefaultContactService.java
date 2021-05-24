package com.superchat.messaging.service.contact;

import com.superchat.messaging.dataaccessobject.ContactRepository;
import com.superchat.messaging.dataaccessobject.ConversationRepository;
import com.superchat.messaging.domainobject.ContactDO;
import com.superchat.messaging.domainobject.ConversationDO;
import com.superchat.messaging.exception.ConstraintsViolationException;
import com.superchat.messaging.exception.EntityNotFoundException;
import com.superchat.messaging.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some contact specific things.
 * <p/>
 */
@Service
public class DefaultContactService implements ContactService
{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultContactService.class);

    private final ContactRepository contactRepository;
    private final ConversationRepository conversationRepository;


    public DefaultContactService(ContactRepository contactRepository, ConversationRepository conversationRepository)
    {
        this.contactRepository = contactRepository;
        this.conversationRepository = conversationRepository;
    }


    @Transactional(rollbackFor = {ConstraintsViolationException.class})
    @Override public ContactDO create(ContactDO contactDO) throws ConstraintsViolationException
    {
        ContactDO contact;
        try
        {
            conversationRepository.save(contactDO.getConversation().orElse(new ConversationDO()));
            contact = contactRepository.save(contactDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a contact: {}", contactDO);
            throw new ConstraintsViolationException(String.format("ConstraintsViolationException while creating a contact: %s", contactDO.toString()), e);
        }
        return contact;
    }


    @Transactional
    @Override public ContactDO find(long contactId) throws EntityNotFoundException
    {
        return findContactChecked(contactId);
    }


    @Transactional
    @Override public ContactDO find(String contactName) throws EntityNotFoundException
    {
        return findContactChecked(contactName);
    }


    @Transactional
    @Override public List<ContactDO> find()
    {
        return contactRepository.findAll();
    }


    private ContactDO findContactChecked(Long contactId) throws EntityNotFoundException
    {
        return contactRepository.findById(contactId)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find entity with id: %d", contactId)));
    }


    private ContactDO findContactChecked(@NotNull String contactName) throws EntityNotFoundException
    {
        return contactRepository.findByNameIgnoreCase(contactName)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find entity with identifier: %s", contactName)));
    }
}
