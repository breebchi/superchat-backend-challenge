package com.superchat.messaging.service.conversation;

import com.superchat.messaging.dataaccessobject.ContactRepository;
import com.superchat.messaging.dataaccessobject.ConversationRepository;
import com.superchat.messaging.domainobject.ContactDO;
import com.superchat.messaging.domainobject.ConversationDO;
import com.superchat.messaging.domainvalue.ContactIdentifierType;
import com.superchat.messaging.domainvalue.Message;
import com.superchat.messaging.exception.ConstraintsViolationException;
import com.superchat.messaging.exception.EntityNotFoundException;
import com.superchat.messaging.service.ConversationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some conversation specific things.
 * <p/>
 */
@Service
public class DefaultConversationService implements ConversationService
{

    private final ConversationRepository conversationRepository;
    private final ContactRepository contactRepository;

    private final String LANGUAGE = Locale.ENGLISH.getLanguage();


    public DefaultConversationService(ConversationRepository conversationRepository, ContactRepository contactRepository)
    {
        this.conversationRepository = conversationRepository;
        this.contactRepository = contactRepository;
    }


    @Transactional
    @Override public ConversationDO find(long conversationId) throws EntityNotFoundException
    {
        return findConversationChecked(conversationId);
    }


    @Transactional
    @Override public List<ConversationDO> find()
    {
        return conversationRepository.findAll();
    }


    @Transactional(rollbackFor = {EntityNotFoundException.class})
    @Override public ConversationDO sendMessage(long contactId, Message message) throws EntityNotFoundException
    {
        ContactDO contact = contactRepository.findById(contactId)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find entity with id: %d", contactId)));
        ConversationDO conversationDO = contact.getConversation().orElse(conversationRepository.save(new ConversationDO()));
        conversationDO.setContact(contact);
        conversationDO.addMessage(message);
        return conversationDO;
    }


    @Transactional(rollbackFor = {EntityNotFoundException.class})
    @Override public ConversationDO sendMessage(ContactIdentifierType contactIdentifierType, String contactIdentifier, Message message)
        throws EntityNotFoundException, ConstraintsViolationException
    {
        ContactDO contact = contactIdentifierType == ContactIdentifierType.GUESS ? guessContactByText(message.getText())
            : findContactCheckedByIdentifier(contactIdentifierType, contactIdentifier);
        ConversationDO conversationDO = contact.getConversation().orElse(conversationRepository.save(new ConversationDO()));
        conversationDO.setContact(contact);
        conversationDO.addMessage(message);
        return conversationDO;
    }


    private ConversationDO findConversationChecked(Long conversationId) throws EntityNotFoundException
    {
        return conversationRepository.findById(conversationId)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find entity with id: %d", conversationId)));
    }


    private ContactDO findContactCheckedByIdentifier(ContactIdentifierType contactIdentifierType, String contactIdentifier)
        throws EntityNotFoundException
    {
        switch (contactIdentifierType)
        {
            case NAME:
                return contactRepository.findByNameIgnoreCase(contactIdentifier)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find entity with identifier: %s", contactIdentifier)));
            case EMAIL:
                return contactRepository.findByEmailIgnoreCase(contactIdentifier)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find entity with identifier: %s", contactIdentifier)));
            default:
                throw new IllegalStateException(String.format("Unexpected value: %s", contactIdentifierType));
        }
    }


    private ContactDO guessContactByText(String text) throws ConstraintsViolationException
    {
        List<ContactDO> contactList = contactRepository.searchContactNames(text, LANGUAGE);
        if (contactList.size() != 1)
        {
            throw new ConstraintsViolationException(String.format("Too many or no possible contact matches %s : ", contactList), null);
        }
        return contactList.get(0);
    }
}
