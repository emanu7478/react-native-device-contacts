import type { TurboModule } from "react-native/Libraries/TurboModule/RCTExport";
import { TurboModuleRegistry } from "react-native";

export interface Contact {
  contactId?: string;
  fullName: string;
  prefix?: string;
  firstName?: string;
  middleName: string;
  lastName?: string;
  suffix?: string;
  emailAddress?: { label: string; email: string }[];
  phoneNumbers: { label: string; number: string }[];
}

export interface Spec extends TurboModule {
  getAllContacts(): Promise<Contact[]>;
}

export default TurboModuleRegistry.get<Spec>(
  "ReactNativeDeviceContacts"
) as Spec | null;
