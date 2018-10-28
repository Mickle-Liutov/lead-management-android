package com.community.jboss.leadmanagement.data.models;

import com.community.jboss.leadmanagement.data.entities.Contact;
import com.community.jboss.leadmanagement.data.entities.ContactNumber;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ContactWithNumbers {
    @Embedded
    public Contact contact;

    @Relation(parentColumn = "id", entityColumn = "contactId", entity = ContactNumber.class)
    public List<ContactNumber> contactNumberList;
}
