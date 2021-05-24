package com.superchat.messaging.service;

import com.superchat.messaging.domainobject.ContactDO;
import com.superchat.messaging.exception.ConstraintsViolationException;
import com.superchat.messaging.exception.EntityNotFoundException;

import java.util.List;

public interface ContactService
{
    ContactDO create(ContactDO contactDO) throws ConstraintsViolationException;
    ContactDO find(long contactId) throws EntityNotFoundException;
    ContactDO find(String contactName) throws EntityNotFoundException;
    List<ContactDO> find();
}
