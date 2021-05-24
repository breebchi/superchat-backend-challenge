package com.superchat.messaging.controller.mapper;

import com.superchat.messaging.datatransferobject.ContactDTO;
import com.superchat.messaging.domainobject.ContactDO;
import com.superchat.messaging.domainobject.ConversationDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ContactMapper
{
    private ContactMapper()
    {
    }


    public static ContactDO makeContactDO(ContactDTO contactDTO)
    {
        return new ContactDO(contactDTO.getName(), contactDTO.getEmail(), new ConversationDO());
    }


    public static ContactDTO makeContactDTO(ContactDO contactDO)
    {
        return ContactDTO.newBuilder()
            .setId(contactDO.getId())
            .setName(contactDO.getName())
            .setEmail(contactDO.getEmail())
            .createContactDTO();

    }


    public static List<ContactDTO> makeContactDTOList(Collection<ContactDO> contacts)
    {
        return contacts.stream()
            .map(ContactMapper::makeContactDTO)
            .collect(Collectors.toList());
    }
}
