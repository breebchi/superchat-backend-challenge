package com.superchat.messaging.domainvalue;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.ZonedDateTime;

@Embeddable
public class Message
{
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    private String text;


    public Message()
    {
    }


    public Message(String text)
    {
        this.text = text;
    }


    public ZonedDateTime getDateCreated()
    {
        return dateCreated;
    }


    public String getText()
    {
        return text;
    }


    public void setText(String text)
    {
        this.text = text;
    }
}
