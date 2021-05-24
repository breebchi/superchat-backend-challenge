package com.superchat.messaging.controller;

import com.superchat.messaging.controller.mapper.ContactMapper;
import com.superchat.messaging.datatransferobject.ContactDTO;
import com.superchat.messaging.domainobject.ContactDO;
import com.superchat.messaging.exception.ConstraintsViolationException;
import com.superchat.messaging.exception.EntityNotFoundException;
import com.superchat.messaging.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * All operations with a contact will be routed by this controller.
 * <p/>
 */
@RestController
@Validated
@RequestMapping("v1/contacts")
public class ContactController
{
    private final ContactService contactService;


    @Autowired
    public ContactController(ContactService contactService)
    {
        this.contactService = contactService;
    }


    @GetMapping("/{contactId}")
    public ContactDTO getContact(@PathVariable long contactId) throws EntityNotFoundException
    {
        return ContactMapper.makeContactDTO(contactService.find(contactId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO createContact(@Valid @RequestBody ContactDTO contactDTO) throws ConstraintsViolationException
    {
        ContactDO contactDO = ContactMapper.makeContactDO(contactDTO);
        return ContactMapper.makeContactDTO(contactService.create(contactDO));
    }


    @GetMapping("/contact-name")
    public ContactDTO findContact(@RequestParam @NotNull String contactName) throws EntityNotFoundException
    {
        return ContactMapper.makeContactDTO(contactService.find(contactName));
    }


    @GetMapping
    public List<ContactDTO> findContacts()
    {
        return ContactMapper.makeContactDTOList(contactService.find());
    }
}
