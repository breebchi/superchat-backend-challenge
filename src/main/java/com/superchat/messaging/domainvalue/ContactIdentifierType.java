package com.superchat.messaging.domainvalue;

public enum ContactIdentifierType
{
    NAME("Name"), EMAIL("Email"), GUESS("Guess");
    public final String identifier;


    ContactIdentifierType(String contactIdentifier)
    {
        this.identifier = contactIdentifier;
    }
}
