package com.superchat.messaging.domainobject;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Optional;

@Entity
@Table(
    name = "contact",
    uniqueConstraints = {
        @UniqueConstraint(name = "uc_name", columnNames = {"name"}),
        @UniqueConstraint(name = "uc_email", columnNames = {"email"})}
)
public class ContactDO
{
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final ZonedDateTime dateCreated = ZonedDateTime.now();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull(message = "Name can not be null!")
    private String name;
    @Column(nullable = false)
    @NotNull(message = "E-mail can not be null!")
    private String email;
    @OneToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    private ConversationDO conversation;

    public ContactDO()
    {
    }


    public ContactDO(String name, String email, ConversationDO conversation)
    {
        this.name = name;
        this.email = email;
        this.conversation = conversation;
    }


    public Long getId()
    {
        return id;
    }


    public ZonedDateTime getDateCreated()
    {
        return dateCreated;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public String getEmail()
    {
        return email;
    }


    public void setEmail(String email)
    {
        this.email = email;
    }


    public Optional<ConversationDO> getConversation()
    {
        return Optional.ofNullable(conversation);
    }


    public void setConversation(ConversationDO conversation)
    {
        this.conversation = conversation;
    }

    @Override
    public String toString() {
        return String.format("id = %d, name = %s, email =%s", id, name, email);
    }
}
