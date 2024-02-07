# React Native Device Contacts

A React Native package for fetching device contacts on both Android and iOS.

## Installation

```
npm install react-native-device-contacts
```

## Usage

Import the package in your React Native component:

```
import DeviceContacts from 'react-native-device-contacts';
```

Now, you can use the DeviceContacts object in your code to fetch contacts.

## `getAllContacts()`

Use the getAllContacts function to retrieve all contacts from the device:

```
DeviceContacts.getAllContacts()
  .then(contacts => {
    console.log('All contacts:', contacts);
  })
  .catch(error => {
    console.error('Error fetching contacts:', error);
  });
```

## Platform Support

- Android
- iOS

## Troubleshooting

If you encounter any issues or have questions, please check the GitHub Issues or open a new issue.

## Contributing

Feel free to contribute to this project by opening issues or submitting pull requests. Contributions are welcome!
