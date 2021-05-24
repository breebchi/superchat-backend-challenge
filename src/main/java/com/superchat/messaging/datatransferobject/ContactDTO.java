package com.superchat.messaging.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "name", "email" })
public class ContactDTO
{

    @JsonIgnore
    private Long id;

    @NotNull(message = "Name can not be null!")
    private String name;

    @NotNull(message = "E-mail can not be null!")
    private String email;


    private ContactDTO()
    {
    }


    private ContactDTO(Long id, String name, String email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }


    public static ContactDTO.ContactDTOBuilder newBuilder()
    {
        return new ContactDTO.ContactDTOBuilder();
    }


    public String getName()
    {
        return name;
    }


    public String getEmail()
    {
        return email;
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public static final class ContactDTOBuilder
    {
        private Long id;
        private String name;
        private String email;


        private ContactDTOBuilder()
        {
        }


        public ContactDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public ContactDTOBuilder setName(String name)
        {
            this.name = name;
            return this;
        }


        public ContactDTOBuilder setEmail(String email)
        {
            this.email = email;
            return this;
        }


        public ContactDTO createContactDTO()
        {
            return new ContactDTO(id, name, email);
        }
    }
}
