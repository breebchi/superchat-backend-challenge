package com.superchat.messaging.controller.mapper;

import com.superchat.messaging.datatransferobject.MessageDTO;
import com.superchat.messaging.domainvalue.Message;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MessageMapper
{
    private MessageMapper()
    {
    }


    public static Message makeMessage(MessageDTO messageDTO)
    {
        return new Message(messageDTO.getText());
    }


    public static MessageDTO makeMessageDTO(Message messageDO)
    {
        return MessageDTO.newBuilder()
            .setText(messageDO.getText())
            .createMessageDTO();

    }


    public static List<MessageDTO> makeMessageDTOList(Collection<Message> messages)
    {
        return messages.stream()
            .map(MessageMapper::makeMessageDTO)
            .collect(Collectors.toList());
    }


    public static List<Message> makeMessageDOList(Collection<MessageDTO> messages)
    {
        return messages.stream()
            .map(MessageMapper::makeMessage)
            .collect(Collectors.toList());
    }
}
