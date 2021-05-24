package com.superchat.messaging.datatransferobject;

import javax.validation.constraints.NotNull;

public class MessageDTO
{
    @NotNull(message = "Text can not be null!")
    private String text;


    public void setText(String text)
    {
        this.text = text;
    }


    public String getText()
    {
        return text;
    }


    private MessageDTO(String text)
    {
        this.text = text;
    }


    public MessageDTO()
    {
    }


    public static MessageDTO.MessageDTOBuilder newBuilder()
    {
        return new MessageDTO.MessageDTOBuilder();
    }

    public static final class MessageDTOBuilder
    {
        private String text;


        private MessageDTOBuilder()
        {
        }



        public MessageDTOBuilder setText(String text)
        {
            this.text = text;
            return this;
        }


        public MessageDTO createMessageDTO()
        {
            return new MessageDTO(text);
        }
    }
}
