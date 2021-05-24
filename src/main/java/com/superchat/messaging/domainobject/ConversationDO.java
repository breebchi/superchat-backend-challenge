package com.superchat.messaging.domainobject;

import com.superchat.messaging.domainvalue.Message;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "conversation"
)
public class ConversationDO
{
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final ZonedDateTime dateCreated = ZonedDateTime.now();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "conversation")
    private ContactDO contact;

    @ElementCollection
    @CollectionTable(name = "conversation_message", joinColumns = @JoinColumn(name = "conversation_id"), foreignKey = @ForeignKey(name = "conversation_message_conversation_fk"))
    private List<Message> messages = new ArrayList<>();


    public ConversationDO()
    {
    }


    public ConversationDO(List<Message> messages)
    {
        this.messages = messages;
    }


    public Long getId()
    {
        return id;
    }


    public ZonedDateTime getDateCreated()
    {
        return dateCreated;
    }


    public ContactDO getContact()
    {
        return contact;
    }


    public void setContact(ContactDO contact)
    {
        this.contact = contact;
    }


    public List<Message> getMessages()
    {
        return messages;
    }


    public void addMessage(Message message)
    {
        this.messages.add(message);
    }
}
