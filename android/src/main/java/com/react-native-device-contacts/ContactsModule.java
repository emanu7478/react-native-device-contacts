package com.reactnativedevicecontacts;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;


import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.reactnativedevicecontacts.Contact;

import java.util.ArrayList;

import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.HashMap;
import org.json.JSONArray;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ContactsModule extends NativeDeviceContactsSpec {

    private ContentResolver contentResolver;

    public static String NAME = "ReactNativeDeviceContacts";

    private static final List<String> CONTACT_DATA = Stream.of(
            ContactsContract.Data._ID,
            StructuredName.CONTACT_ID,
            ContactsContract.Data.RAW_CONTACT_ID,
            ContactsContract.Data.LOOKUP_KEY,
            ContactsContract.Data.STARRED,
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.Profile.DISPLAY_NAME,
            StructuredName.GIVEN_NAME,
            StructuredName.MIDDLE_NAME,
            StructuredName.FAMILY_NAME,
            StructuredName.PREFIX,
            StructuredName.SUFFIX,
            Phone.NUMBER,
            Phone.NORMALIZED_NUMBER,
            Phone.TYPE,
            Phone.LABEL,
            Email.DATA,
            Email.ADDRESS,
            Email.TYPE,
            Email.LABEL
            // Add other elements here...
    ).collect(Collectors.toList());

    String[] contactProjection = CONTACT_DATA.toArray(new String[0]);

    private Promise contactPromise;

    private static final String PHONE_TYPE_LABEL_HOME = "home";
    private static final String PHONE_TYPE_LABEL_MOBILE = "mobile";
    private static final String PHONE_TYPE_LABEL_WORK = "work";
    private static final String EMAIL_TYPE_LABEL_HOME = "home";
    private static final String EMAIL_TYPE_LABEL_WORK = "work";

    private ReactApplicationContext reactContext;

    ContactsModule(ReactApplicationContext context) {
        super(context);
        this.reactContext = context;
        this.contentResolver = context.getContentResolver();
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    private List<Contact> getContactsFromCursor(Cursor cursor) {
        // Initialize a list to store Contact objects
        List<Contact> contactsList = new ArrayList<>();

        // Use a HashMap to efficiently store and retrieve contacts based on display name
        HashMap<String, Contact> contactsMap = new HashMap<>();

        // Iterate through the cursor to retrieve contact information
        while (cursor.moveToNext()) {
            // Extract relevant data from the cursor
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID));
            String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            // Create a Contact object or retrieve it if already exists in the map
            Contact contact;
            if (!contactsMap.containsKey(displayName)) {
                contact = new Contact(
                        contactId,
                        new ArrayList<>(),
                        null,
                        null,
                        displayName,
                        null,
                        null,
                        new ArrayList<>()
                );
                contactsMap.put(displayName, contact);
            } else {
                contact = contactsMap.get(displayName);
            }

            // Process data based on MIME type
            switch (mimeType) {
                case StructuredName.CONTENT_ITEM_TYPE:
                    // Extract structured name details
                    String givenName = cursor.getString(cursor.getColumnIndex(StructuredName.GIVEN_NAME));
                    String familyName = cursor.getString(cursor.getColumnIndex(StructuredName.FAMILY_NAME));
                    String prefix = cursor.getString(cursor.getColumnIndex(StructuredName.PREFIX));
                    String suffix = cursor.getString(cursor.getColumnIndex(StructuredName.SUFFIX));
                    contact.setFirstName(givenName);
                    contact.setLastName(familyName);
                    contact.setPrefix(prefix);
                    contact.setSuffix(suffix);
                    break;
                case Phone.CONTENT_ITEM_TYPE:
                    // Extract phone details
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                    int phoneType = cursor.getInt(cursor.getColumnIndex(Phone.TYPE));
                    String label;
                    switch (phoneType) {
                        // Convert phone type to a readable label
                        case Phone.TYPE_HOME:
                            label = "home";
                            break;
                        case Phone.TYPE_WORK:
                            label = "work";
                            break;
                        case Phone.TYPE_MOBILE:
                            label = "mobile";
                            break;
                        case Phone.TYPE_OTHER:
                        default:
                            label = "other";
                            break;
                    }
                    contact.addPhoneNumber(label, phoneNumber);
                    break;
                case Email.CONTENT_ITEM_TYPE:
                    // Extract email details
                    String email = cursor.getString(cursor.getColumnIndex(Email.ADDRESS));
                    int emailType = cursor.getInt(cursor.getColumnIndex(Email.TYPE));
                    String emailLabel;
                    switch (emailType) {
                        // Convert email type to a readable label
                        case Email.TYPE_HOME:
                            emailLabel = "home";
                            break;
                        case Email.TYPE_WORK:
                            emailLabel = "work";
                            break;
                        case Email.TYPE_MOBILE:
                            emailLabel = "mobile";
                            break;
                        case Email.TYPE_OTHER:
                        default:
                            emailLabel = "other";
                            break;
                    }
                    contact.addEmailAddress(emailLabel, email);
                    break;
            }

            // Update the contact in the map
            contactsMap.put(displayName, contact);
        }

        // Add all unique contacts to the final list
        contactsList.addAll(contactsMap.values());

        return contactsList;
    }

    // Helper method to convert a Contact object to a WritableMap
    private WritableMap contactToWritableMap(Contact contact) {
        WritableMap contactMap = new WritableNativeMap();
        contactMap.putString("contactId", contact.getContactId());
        contactMap.putString("fullName", contact.getFullName());

        // Convert phone numbers to WritableArray
        WritableArray phoneNumbersArray = new WritableNativeArray();
        if (contact.getPhoneNumbers() != null) {
            for (Contact.PhoneNumber phoneNumber : contact.getPhoneNumbers()) {
                WritableMap phoneNumberMap = new WritableNativeMap();
                phoneNumberMap.putString("label", phoneNumber.getLabel());
                phoneNumberMap.putString("number", phoneNumber.getNumber());
                phoneNumbersArray.pushMap(phoneNumberMap);
            }
        }

        // Convert email addresses to WritableArray
        WritableArray emailAddressArray = new WritableNativeArray();
        if (contact.getEmailAddresses() != null) {
            for (Contact.EmailAddress emailAddress : contact.getEmailAddresses()) {
                WritableMap emailAddressMap = new WritableNativeMap();
                emailAddressMap.putString("label", emailAddress.getLabel());
                emailAddressMap.putString("email", emailAddress.getEmail());
                emailAddressArray.pushMap(emailAddressMap);
            }
        }

        // Add phone numbers and email addresses to the contact map
        contactMap.putArray("phoneNumbers", phoneNumbersArray);
        contactMap.putArray("emailAddress", emailAddressArray);

        return contactMap;
    }

    // Method to retrieve all contacts and convert them to a WritableArray for React Native
    @Override
    public void getAllContacts(Promise promise) {
        try {
            // Query the content resolver for relevant contact data
            Cursor structuredQueryCursor = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ECLAIR) {
                structuredQueryCursor = contentResolver.query(
                        ContactsContract.Data.CONTENT_URI,
                        contactProjection,
                        ContactsContract.Data.MIMETYPE + "=? OR "
                                + ContactsContract.Data.MIMETYPE + "=? OR "
                                + ContactsContract.Data.MIMETYPE + "=? OR "
                                + ContactsContract.Data.MIMETYPE + "=? OR "
                                + ContactsContract.Data.MIMETYPE + "=?",
                        new String[]{
                                Email.CONTENT_ITEM_TYPE,
                                Phone.CONTENT_ITEM_TYPE,
                                StructuredName.CONTENT_ITEM_TYPE,
                                Organization.CONTENT_ITEM_TYPE,
                                StructuredPostal.CONTENT_ITEM_TYPE,
                        },
                        null
                );
            }

            // Process the cursor data and resolve the promise with the result
            if (structuredQueryCursor != null) {
                List<Contact> contacts = new ArrayList<>(getContactsFromCursor(structuredQueryCursor));
                WritableArray contactsArray = new WritableNativeArray();
                for (Contact contact : contacts) {
                    contactsArray.pushMap(contactToWritableMap(contact));
                }
                structuredQueryCursor.close();
                promise.resolve(contactsArray);
            } else {
                promise.reject("CONTACTS_ERROR", "Failed to retrieve contacts");
            }
        } catch (Exception e) {
            // Handle any exceptions and reject the promise with an error message
            promise.reject("CONTACTS_ERROR", e.getMessage());
        }
    }

}
