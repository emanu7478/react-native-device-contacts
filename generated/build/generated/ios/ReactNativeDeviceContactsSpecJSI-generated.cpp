/**
 * This code was generated by [react-native-codegen](https://www.npmjs.com/package/react-native-codegen).
 *
 * Do not edit this file as changes may cause incorrect behavior and will be lost
 * once the code is regenerated.
 *
 * @generated by codegen project: GenerateModuleH.js
 */

#include "ReactNativeDeviceContactsSpecJSI.h"

namespace facebook {
namespace react {

static jsi::Value __hostFunction_NativeDeviceContactsCxxSpecJSI_fetchAllContacts(jsi::Runtime &rt, TurboModule &turboModule, const jsi::Value* args, size_t count) {
  return static_cast<NativeDeviceContactsCxxSpecJSI *>(&turboModule)->fetchAllContacts(rt);
}

NativeDeviceContactsCxxSpecJSI::NativeDeviceContactsCxxSpecJSI(std::shared_ptr<CallInvoker> jsInvoker)
  : TurboModule("ReactNativeDeviceContacts", jsInvoker) {
  methodMap_["fetchAllContacts"] = MethodMetadata {0, __hostFunction_NativeDeviceContactsCxxSpecJSI_fetchAllContacts};
}


} // namespace react
} // namespace facebook