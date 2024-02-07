package com.reactnativedevicecontacts;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private String contactId;
    private List<EmailAddress> emailAddresses;

    private String suffix;

    private String prefix;
    private String firstName;
    private String fullName;
    private String lastName;
    private List<PhoneNumber> phoneNumbers;

    // Constructor
    public Contact() {
        // Default constructor
    }

    public Contact(String contactId, List<EmailAddress> emailAddresses, String prefix, String firstName, String fullName, String lastName, String suffix,
                   List<PhoneNumber> phoneNumbers) {
        this.contactId = contactId;
        this.emailAddresses = emailAddresses;
        this.suffix = suffix;
        this.prefix = prefix;
        this.firstName = firstName;
        this.fullName = fullName;
        this.lastName = lastName;
        this.phoneNumbers = phoneNumbers;
    }

    // Method to add phone numbers with labels
    public void addPhoneNumber(String label, String number) {
        if (phoneNumbers == null) {
            phoneNumbers = new ArrayList<>();
        }
        phoneNumbers.add(new PhoneNumber(label, number));
    }

    public void addEmailAddress(String label, String email) {
        if (emailAddresses == null) {
            emailAddresses = new ArrayList<>();
        }
        emailAddresses.add(new EmailAddress(email, label));
    }

    // Getters and Setters for Contact class
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public List<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    // Inner class representing EmailAddress
    public class EmailAddress {
        private String email;
        private String label;

        // Constructor for EmailAddress
        public EmailAddress(String email, String label) {
            this.email = email;
            this.label = label;
        }

        // Getters and Setters for EmailAddress
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return "EmailAddress{" +
                    "label='" + label + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    // Inner class representing PhoneNumber
    public class PhoneNumber {
        private String label;
        private String number;

        // Constructor for PhoneNumber
        public PhoneNumber(String label, String number) {
            this.label = label;
            this.number = number;
        }

        // Getters and Setters for PhoneNumber
        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "EmailAddress{" +
                    "label='" + label + '\'' +
                    ", number='" + number + '\'' +
                    '}';
        }
    }
}