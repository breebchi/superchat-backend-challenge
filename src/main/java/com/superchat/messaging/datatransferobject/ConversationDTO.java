package com.superchat.messaging.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.superchat.messaging.controller.mapper.MessageMapper;
import com.superchat.messaging.domainvalue.Message;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "contactName", "messages" })
public class ConversationDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "Messages can not be null!")
    private List<MessageDTO> messages = new ArrayList<>();
    @NotNull(message = "Contact name can not be null!")
    private String contactName;


    private ConversationDTO()
    {
    }


    private ConversationDTO(Long id, List<Message> messages, String contactName)
    {
        this.id = id;
        this.messages = MessageMapper.makeMessageDTOList(messages);
        this.contactName = contactName;
    }


    public static ConversationDTOBuilder newBuilder()
    {
        return new ConversationDTOBuilder();
    }


    public String getContactName()
    {
        return contactName;
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public List<MessageDTO> getMessages()
    {
        return messages;
    }


    public static final class ConversationDTOBuilder
    {
        private Long id;
        private List<Message> messages = new ArrayList<>();
        private String contactName;


        private ConversationDTOBuilder()
        {
        }


        public ConversationDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public ConversationDTOBuilder setMessages(List<Message> messages)
        {
            this.messages = messages;
            return this;
        }

        public ConversationDTOBuilder setContactName(String contactName)
        {
            this.contactName = contactName;
            return this;
        }


        public ConversationDTO createConversationDTO()
        {
            return new ConversationDTO(id, messages, contactName);
        }
    }
}
