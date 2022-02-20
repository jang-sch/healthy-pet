package a3.mwisehart.contactsapp;

import java.util.Comparator;

public class ContactComparator implements Comparator<Contact>
{

    @Override
    public int compare(Contact contact, Contact t1) {
        return contact.getLastName().compareToIgnoreCase(t1.getLastName());
    }
}
