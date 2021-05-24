package com.superchat.messaging.controller;

import com.superchat.messaging.controller.mapper.ConversationMapper;
import com.superchat.messaging.controller.mapper.MessageMapper;
import com.superchat.messaging.datatransferobject.ConversationDTO;
import com.superchat.messaging.datatransferobject.MessageDTO;
import com.superchat.messaging.domainvalue.ContactIdentifierType;
import com.superchat.messaging.exception.ConstraintsViolationException;
import com.superchat.messaging.exception.EntityNotFoundException;
import com.superchat.messaging.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * All operations with a conversations will be routed by this controller.
 * <p/>
 */
@RestController
@Validated
@RequestMapping("v1/conversations")
public class ConversationController
{
    private final ConversationService conversationService;


    @Autowired
    public ConversationController(ConversationService conversationService)
    {
        this.conversationService = conversationService;
    }


    @GetMapping("/{conversationId}")
    public ConversationDTO getConversation(@PathVariable long conversationId) throws EntityNotFoundException
    {
        return ConversationMapper.makeConversationDTO(conversationService.find(conversationId));
    }


    @PostMapping("/{contactId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ConversationDTO sendMessage(@PathVariable long contactId, @Valid @RequestBody MessageDTO messageDTO) throws EntityNotFoundException
    {
        return ConversationMapper.makeConversationDTO(conversationService.sendMessage(contactId, MessageMapper.makeMessage(messageDTO)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConversationDTO sendMessage(@RequestParam @NotNull ContactIdentifierType contactIdentifierType, @RequestParam String contactIdentifier,  @Valid @RequestBody MessageDTO messageDTO) throws ConstraintsViolationException, EntityNotFoundException
    {
        return ConversationMapper.makeConversationDTO(conversationService.sendMessage(contactIdentifierType, contactIdentifier, MessageMapper.makeMessage(messageDTO)));
    }


    @GetMapping
    public List<ConversationDTO> findConversations()
    {
        return ConversationMapper.makeConversationDTOList(conversationService.find());
    }
}
