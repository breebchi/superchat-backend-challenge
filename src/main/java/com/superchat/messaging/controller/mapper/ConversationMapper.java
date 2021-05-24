package com.superchat.messaging.controller.mapper;

import com.superchat.messaging.datatransferobject.ConversationDTO;
import com.superchat.messaging.domainobject.ConversationDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ConversationMapper
{
    private ConversationMapper()
    {
    }


    public static ConversationDO makeConversationDO(ConversationDTO conversationDTO)
    {
        return new ConversationDO(MessageMapper.makeMessageDOList(conversationDTO.getMessages()));
    }


    public static ConversationDTO makeConversationDTO(ConversationDO conversationDO)
    {
        return ConversationDTO.newBuilder()
            .setId(conversationDO.getId())
            .setMessages(conversationDO.getMessages())
            .setContactName(conversationDO.getContact().getName())
            .createConversationDTO();

    }


    public static List<ConversationDTO> makeConversationDTOList(Collection<ConversationDO> conversations)
    {
        return conversations.stream()
            .map(ConversationMapper::makeConversationDTO)
            .collect(Collectors.toList());
    }
}
