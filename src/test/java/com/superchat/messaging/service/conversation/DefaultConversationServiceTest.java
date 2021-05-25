package com.superchat.messaging.service.conversation;

import com.superchat.messaging.dataaccessobject.ContactRepository;
import com.superchat.messaging.dataaccessobject.ConversationRepository;
import com.superchat.messaging.domainobject.ContactDO;
import com.superchat.messaging.domainobject.ConversationDO;
import com.superchat.messaging.domainvalue.ContactIdentifierType;
import com.superchat.messaging.domainvalue.Message;
import com.superchat.messaging.exception.ConstraintsViolationException;
import com.superchat.messaging.exception.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.superchat.messaging.dataaccessobject.ContactRepository.qContactDO;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DefaultConversationServiceTest
{

    @InjectMocks
    DefaultConversationService conversationService;
    @Mock
    ContactRepository contactRepository;
    @Mock
    ConversationRepository conversationRepository;
    private Validator validator;


    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void findById() throws EntityNotFoundException
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("This is text 1"));
        messages.add(new Message("This is text 2"));
        messages.add(new Message("This is text 3"));
        ConversationDO conversation = new ConversationDO(messages);
        conversation.setId(1L);
        when(conversationRepository.findById(conversation.getId())).thenReturn(Optional.of(conversation));
        assertEquals(conversation, conversationService.find(1L));
    }


    @Test
    public void findAll()
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("This is text 1"));
        messages.add(new Message("This is text 2"));
        messages.add(new Message("This is text 3"));

        List<ConversationDO> conversations = new ArrayList<>();
        conversations.add(new ConversationDO(messages));
        conversations.add(new ConversationDO(messages));
        conversations.add(new ConversationDO(messages));

        when(conversationRepository.findAll()).thenReturn(conversations);
    }


    @Test(expected = EntityNotFoundException.class)
    public void sendMessage() throws EntityNotFoundException
    {
        Message message = new Message("This is text 1");
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(new Message("This is text 2"));
        messages.add(new Message("This is text 3"));
        ConversationDO conversation = new ConversationDO(messages);
        ContactDO contact = new ContactDO("Musk", "musk@musk", conversation);
        contact.setId(1L);
        conversation.setContact(contact);
        conversation.addMessage(message);
        when(contactRepository.save(contact)).thenReturn(contact);
        assertEquals(contact, conversationService.sendMessage(contact.getId(), message));
    }


    @Test(expected = EntityNotFoundException.class)
    public void sendMessageShouldThrowConstraintsViolationExceptionWhenContactDoesNotExist() throws EntityNotFoundException
    {
        Message message = new Message("This is text 1");
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(new Message("This is text 2"));
        messages.add(new Message("This is text 3"));
        ConversationDO conversation = new ConversationDO(messages);
        ContactDO contact = new ContactDO("Musk", "musk@musk", conversation);
        contact.setId(1L);
        conversation.setContact(contact);
        conversation.addMessage(message);
        conversationService.sendMessage(contact.getId(), message);
    }


    @Test
    public void sendMessageByIdentifier() throws EntityNotFoundException, ConstraintsViolationException
    {
        Message message = new Message("This is text 1");
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(new Message("This is text 2"));
        messages.add(new Message("This is text 3"));
        ConversationDO conversation = new ConversationDO(messages);
        ContactDO contact = new ContactDO("Musk", "musk@musk", conversation);
        contact.setId(1L);
        conversation.setContact(contact);
        conversation.addMessage(message);

        when(contactRepository.save(contact)).thenReturn(contact);
        when(contactRepository.findByNameIgnoreCase(contact.getName())).thenReturn(Optional.of(contact));
        when(contactRepository.findByEmailIgnoreCase(contact.getEmail())).thenReturn(Optional.of(contact));
        when(contactRepository.findAll(qContactDO.name.lower().in(message.getText().toLowerCase()))).thenReturn(Collections.singletonList(contact));

        assertEquals(conversation, conversationService.sendMessage(ContactIdentifierType.NAME, contact.getName(), message));
        assertEquals(conversation, conversationService.sendMessage(ContactIdentifierType.EMAIL, contact.getEmail(), message));
        assertEquals(conversation, conversationService.sendMessage(ContactIdentifierType.GUESS, message.getText(), message));
    }
}
