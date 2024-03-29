/**
 * This code was generated by [react-native-codegen](https://www.npmjs.com/package/react-native-codegen).
 *
 * Do not edit this file as changes may cause incorrect behavior and will be lost
 * once the code is regenerated.
 *
 * @generated by codegen project: GenerateModuleH.js
 */

#pragma once

#include <ReactCommon/TurboModule.h>
#include <react/bridging/Bridging.h>

namespace facebook {
namespace react {


  class JSI_EXPORT NativeDeviceContactsCxxSpecJSI : public TurboModule {
protected:
  NativeDeviceContactsCxxSpecJSI(std::shared_ptr<CallInvoker> jsInvoker);

public:
  virtual jsi::Value fetchAllContacts(jsi::Runtime &rt) = 0;

};

template <typename T>
class JSI_EXPORT NativeDeviceContactsCxxSpec : public TurboModule {
public:
  jsi::Value get(jsi::Runtime &rt, const jsi::PropNameID &propName) override {
    return delegate_.get(rt, propName);
  }

protected:
  NativeDeviceContactsCxxSpec(std::shared_ptr<CallInvoker> jsInvoker)
    : TurboModule("ReactNativeDeviceContacts", jsInvoker),
      delegate_(static_cast<T*>(this), jsInvoker) {}

private:
  class Delegate : public NativeDeviceContactsCxxSpecJSI {
  public:
    Delegate(T *instance, std::shared_ptr<CallInvoker> jsInvoker) :
      NativeDeviceContactsCxxSpecJSI(std::move(jsInvoker)), instance_(instance) {}

    jsi::Value fetchAllContacts(jsi::Runtime &rt) override {
      static_assert(
          bridging::getParameterCount(&T::fetchAllContacts) == 1,
          "Expected fetchAllContacts(...) to have 1 parameters");

      return bridging::callFromJs<jsi::Value>(
          rt, &T::fetchAllContacts, jsInvoker_, instance_);
    }

  private:
    T *instance_;
  };

  Delegate delegate_;
};

} // namespace react
} // namespace facebook
