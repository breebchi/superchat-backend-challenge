package com.superchat.messaging.service.contact;

import com.superchat.messaging.dataaccessobject.ContactRepository;
import com.superchat.messaging.dataaccessobject.ConversationRepository;
import com.superchat.messaging.domainobject.ContactDO;
import com.superchat.messaging.domainobject.ConversationDO;
import com.superchat.messaging.domainvalue.Message;
import com.superchat.messaging.exception.ConstraintsViolationException;
import com.superchat.messaging.exception.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DefaultContactServiceTest
{

    @InjectMocks
    DefaultContactService contactService;
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
    public void create() throws ConstraintsViolationException
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("This is text 1"));
        messages.add(new Message("This is text 2"));
        messages.add(new Message("This is text 3"));
        ConversationDO conversation = new ConversationDO(messages);
        ContactDO contact = new ContactDO("Musk", "musk@musk", conversation);
        when(conversationRepository.save(conversation)).thenReturn(conversation);
        when(contactRepository.save(contact)).thenReturn(contact);
        assertEquals(contact, contactService.create(contact));
    }


    @Test
    public void createShouldThrowConstraintsViolationExceptionWhenFieldValueNotValid() throws ConstraintsViolationException
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("This is text 1"));
        messages.add(new Message("This is text 2"));
        messages.add(new Message("This is text 3"));
        ConversationDO conversation = new ConversationDO(messages);
        ContactDO contact = new ContactDO("Musk", null, conversation);
        when(conversationRepository.save(conversation)).thenReturn(conversation);
        when(contactRepository.save(contact)).thenReturn(contact);
        Set<ConstraintViolation<ContactDO>> violations = validator.validate(contactService.create(contact));
        assertEquals(1, violations.size());
    }


    @Test
    public void findById() throws EntityNotFoundException
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("This is text 1"));
        messages.add(new Message("This is text 2"));
        messages.add(new Message("This is text 3"));
        ConversationDO conversation = new ConversationDO(messages);
        ContactDO contact = new ContactDO("Musk", "musk@musk", conversation);
        contact.setId(1L);
        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));
        assertEquals(contact, contactService.find(1L));
    }


    @Test(expected = EntityNotFoundException.class)
    public void findByIdShouldThrowEntityNotFoundExceptionWhenContactIsNotFound() throws EntityNotFoundException
    {
        contactService.find(1L);
    }


    @Test
    public void findByName() throws EntityNotFoundException
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("This is text 1"));
        messages.add(new Message("This is text 2"));
        messages.add(new Message("This is text 3"));
        ConversationDO conversation = new ConversationDO(messages);
        ContactDO contact = new ContactDO("Musk", "musk@musk", conversation);
        when(contactRepository.findByNameIgnoreCase(contact.getName())).thenReturn(Optional.of(contact));
        assertEquals(contact, contactService.find("Musk"));
    }


    @Test(expected = EntityNotFoundException.class)
    public void findByNameShouldThrowEntityNotFoundExceptionWhenContactIsNotFound() throws EntityNotFoundException
    {
        contactService.find("Musk");
    }


    @Test
    public void find()
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("This is text 1"));
        messages.add(new Message("This is text 2"));
        messages.add(new Message("This is text 3"));

        ConversationDO conversation1 = new ConversationDO(messages);
        ConversationDO conversation2 = new ConversationDO(messages);
        ConversationDO conversation3 = new ConversationDO(messages);
        List<ContactDO> contacts = new ArrayList<>();
        contacts.add(new ContactDO("Bezos", "bezos@bezos", conversation1));
        contacts.add(new ContactDO("Musk", "musk@musk", conversation2));
        contacts.add(new ContactDO("Pichai", "pichai@pichai", conversation3));

        when(contactRepository.findAll()).thenReturn(contacts);
        assertEquals(3, contactService.find().size());
        assertEquals(contacts, contactService.find());
    }


    @Test
    public void findShouldReturnEmptyListWhenNoContactWasSpecified()
    {
        when(contactRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(0, contactService.find().size());
    }
}
