#import "ReactNativeDeviceContacts.h"
#import <Contacts/Contacts.h>
#import <ContactsUI/ContactsUI.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTUtils.h>

@implementation ReactNativeDeviceContacts {
  CNContactStore *contactStore;
}

RCT_EXPORT_MODULE()

- (CNContactStore *)getContactStore:(RCTPromiseRejectBlock)reject {
    if (!contactStore) {
        contactStore = [[CNContactStore alloc] init];
    }
    return contactStore;
}


// Import necessary Contacts framework for managing contacts
RCT_EXPORT_METHOD(getAllContacts:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject) {
    // Create a new instance of CNContactStore to interact with the Contacts framework
    CNContactStore *store = [[CNContactStore alloc] init];

    // Request access to the user's contacts
    [store requestAccessForEntityType:CNEntityTypeContacts completionHandler:^(BOOL granted, NSError *error) {
        if (granted) {
            // If access is granted, initialize an array to store contact information
            NSMutableArray *contacts = [NSMutableArray array];
            
            // Specify the keys to fetch from each contact (given name, family name, phone numbers, email addresses)
            NSArray *keys = @[CNContactIdentifierKey, CNContactNamePrefixKey, CNContactNameSuffixKey, CNContactGivenNameKey, CNContactMiddleNameKey, CNContactFamilyNameKey, CNContactPhoneNumbersKey, CNContactEmailAddressesKey, CNContactThumbnailImageDataKey];
            
            // Create a contact fetch request with the specified keys
            CNContactFetchRequest *fetchRequest = [[CNContactFetchRequest alloc] initWithKeysToFetch:keys];

            // Declare an error variable for potential fetch errors
            NSError *fetchError;
            
            // Enumerate through the contacts using a block
            [store enumerateContactsWithFetchRequest:fetchRequest error:&fetchError usingBlock:^(CNContact *contact, BOOL *stop) {
                if (!fetchError) {
                    // If no fetch error, extract and format contact information
                    NSMutableDictionary *contactInfo = [NSMutableDictionary dictionary];
                    
                    // Extract contact details like ID, first name, last name
                    NSString *contactId = [NSString stringWithFormat:@"%@", contact.identifier];
                    NSString *prefix = [NSString stringWithFormat:@"%@", contact.namePrefix];
                    NSString *suffix = [NSString stringWithFormat:@"%@", contact.nameSuffix];
                    NSString *firstName = [NSString stringWithFormat:@"%@", contact.givenName];
                    NSString *middleName = [NSString stringWithFormat:@"%@", contact.middleName];
                    NSString *lastName = [NSString stringWithFormat:@"%@", contact.familyName];
                    NSString *fullName = [NSString stringWithFormat:@"%@ %@ %@ %@ %@", contact.namePrefix, contact.givenName, contact.middleName, contact.familyName, contact.nameSuffix];
                    
                    // Initialize values to respective keys in contactInfo
                    [contactInfo setObject:contactId forKey:@"contactId"];
                    [contactInfo setObject:prefix forKey:@"prefix"];
                    [contactInfo setObject:suffix forKey:@"suffix"];
                    [contactInfo setObject:firstName forKey:@"firstName"];
                    [contactInfo setObject:middleName forKey:@"middleName"];
                    [contactInfo setObject:lastName forKey:@"lastName"];
                    [contactInfo setObject:fullName forKey:@"fullName"];

                    // Extract and format phone numbers
                    NSMutableArray *formattedPhoneNumbers = [NSMutableArray array];
                    NSArray *contactPhoneNumbers = contact.phoneNumbers;
                    for (CNLabeledValue *phoneNumberValue in contactPhoneNumbers) {
                        CNPhoneNumber *phoneNumber = phoneNumberValue.value;
                        NSString *phoneNumberString = phoneNumber.stringValue;
                        // Use localized label
                        NSString *label = [CNLabeledValue localizedStringForLabel:phoneNumberValue.label];

                        NSMutableDictionary *phoneNumberInfo = [NSMutableDictionary dictionary];
                        [phoneNumberInfo setObject:label forKey:@"label"];
                        [phoneNumberInfo setObject:phoneNumberString forKey:@"number"];

                        [formattedPhoneNumbers addObject:phoneNumberInfo];
                    }

                    // If there are phone numbers, add them to the contactInfo dictionary
                    if (formattedPhoneNumbers.count > 0) {
                        [contactInfo setObject:formattedPhoneNumbers forKey:@"phoneNumbers"];
                    }

                    // Extract and format email addresses
                    NSMutableArray *formattedEmailAddresses = [NSMutableArray array];
                    NSArray *contactEmailAddresses = contact.emailAddresses;
                    for (CNLabeledValue *emailAddressValue in contactEmailAddresses) {
                        NSString *emailAddressString = emailAddressValue.value;
                        // Use localized label
                        NSString *label = [CNLabeledValue localizedStringForLabel:emailAddressValue.label];

                        NSMutableDictionary *emailAddressInfo = [NSMutableDictionary dictionary];
                        [emailAddressInfo setObject:label forKey:@"label"];
                        [emailAddressInfo setObject:emailAddressString forKey:@"email"];

                        [formattedEmailAddresses addObject:emailAddressInfo];
                    }

                    // If there are email addresses, add them to the contactInfo dictionary
                    if (formattedEmailAddresses.count > 0) {
                        [contactInfo setObject:formattedEmailAddresses forKey:@"emailAddresses"];
                    }

                    // Add the contactInfo dictionary to the contacts array
                    [contacts addObject:contactInfo];
                } else {
                    // If there is a fetch error, stop the enumeration
                    *stop = YES;
                }
            }];

            // If there are no fetch errors, resolve the promise with the contacts array
            if (!fetchError) {
                resolve(contacts);
            } else {
                // If there is a fetch error, reject the promise with the error description
                reject(@"fetch_error", fetchError.localizedDescription, nil);
            }
        } else {
            // If access is denied, reject the promise with a permission_denied message
            reject(@"permission_denied", @"Contacts access denied", nil);
        }
    }];
}


@end
