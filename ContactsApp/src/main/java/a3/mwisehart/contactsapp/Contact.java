package a3.mwisehart.contactsapp;

import org.json.JSONObject;

public class Contact
{
    private String firstName, lastName, phone;

    public Contact(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public Contact(JSONObject contact)
    {
        this.firstName = contact.getString("firstName");
        this.lastName = contact.getString("lastName");
        this.phone = contact.getString("phone");
    }

    public String getJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("firstName", this.firstName);
        json.put("lastName", this.lastName);
        json.put("phone", this.phone);
        return json.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Override
    public String toString()
    {
        return String.format("%-35s | %-35s | %-20s", this.lastName, this.firstName, this.phone);
    }
}
