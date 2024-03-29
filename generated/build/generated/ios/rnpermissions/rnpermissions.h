/**
 * This code was generated by [react-native-codegen](https://www.npmjs.com/package/react-native-codegen).
 *
 * Do not edit this file as changes may cause incorrect behavior and will be lost
 * once the code is regenerated.
 *
 * @generated by codegen project: GenerateModuleObjCpp
 *
 * We create an umbrella header (and corresponding implementation) here since
 * Cxx compilation in BUCK has a limitation: source-code producing genrule()s
 * must have a single output. More files => more genrule()s => slower builds.
 */

#ifndef __cplusplus
#error This file must be compiled as Obj-C++. If you are importing it, you must change your file extension to .mm.
#endif
#import <Foundation/Foundation.h>
#import <RCTRequired/RCTRequired.h>
#import <RCTTypeSafety/RCTConvertHelpers.h>
#import <RCTTypeSafety/RCTTypedModuleConstants.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTCxxConvert.h>
#import <React/RCTManagedPointer.h>
#import <ReactCommon/RCTTurboModule.h>
#import <optional>
#import <vector>

namespace JS {
  namespace NativePermissionsModule {
    struct Constants {

      struct Builder {
        struct Input {
          std::optional<std::vector<NSString *>> available;
        };

        /** Initialize with a set of values */
        Builder(const Input i);
        /** Initialize with an existing Constants */
        Builder(Constants i);
        /** Builds the object. Generally used only by the infrastructure. */
        NSDictionary *buildUnsafeRawValue() const { return _factory(); };
      private:
        NSDictionary *(^_factory)(void);
      };

      static Constants fromUnsafeRawValue(NSDictionary *const v) { return {v}; }
      NSDictionary *unsafeRawValue() const { return _v; }
    private:
      Constants(NSDictionary *const v) : _v(v) {}
      NSDictionary *_v;
    };
  }
}
@protocol NativePermissionsModuleSpec <RCTBridgeModule, RCTTurboModule>

- (void)checkNotifications:(RCTPromiseResolveBlock)resolve
                    reject:(RCTPromiseRejectBlock)reject;
- (void)openSettings:(RCTPromiseResolveBlock)resolve
              reject:(RCTPromiseRejectBlock)reject;
- (void)checkMultiplePermissions:(NSArray *)permissions
                         resolve:(RCTPromiseResolveBlock)resolve
                          reject:(RCTPromiseRejectBlock)reject;
- (void)checkPermission:(NSString *)permission
                resolve:(RCTPromiseResolveBlock)resolve
                 reject:(RCTPromiseRejectBlock)reject;
- (void)requestMultiplePermissions:(NSArray *)permissions
                           resolve:(RCTPromiseResolveBlock)resolve
                            reject:(RCTPromiseRejectBlock)reject;
- (void)requestPermission:(NSString *)permission
                  resolve:(RCTPromiseResolveBlock)resolve
                   reject:(RCTPromiseRejectBlock)reject;
- (void)shouldShowRequestPermissionRationale:(NSString *)permission
                                     resolve:(RCTPromiseResolveBlock)resolve
                                      reject:(RCTPromiseRejectBlock)reject;
- (void)check:(NSString *)permission
      resolve:(RCTPromiseResolveBlock)resolve
       reject:(RCTPromiseRejectBlock)reject;
- (void)checkLocationAccuracy:(RCTPromiseResolveBlock)resolve
                       reject:(RCTPromiseRejectBlock)reject;
- (void)openLimitedPhotoLibraryPicker:(RCTPromiseResolveBlock)resolve
                               reject:(RCTPromiseRejectBlock)reject;
- (void)request:(NSString *)permission
        resolve:(RCTPromiseResolveBlock)resolve
         reject:(RCTPromiseRejectBlock)reject;
- (void)requestLocationAccuracy:(NSString *)purposeKey
                        resolve:(RCTPromiseResolveBlock)resolve
                         reject:(RCTPromiseRejectBlock)reject;
- (void)requestNotifications:(NSArray *)options
                     resolve:(RCTPromiseResolveBlock)resolve
                      reject:(RCTPromiseRejectBlock)reject;
- (facebook::react::ModuleConstants<JS::NativePermissionsModule::Constants::Builder>)constantsToExport;
- (facebook::react::ModuleConstants<JS::NativePermissionsModule::Constants::Builder>)getConstants;

@end
namespace facebook {
  namespace react {
    /**
     * ObjC++ class for module 'NativePermissionsModule'
     */
    class JSI_EXPORT NativePermissionsModuleSpecJSI : public ObjCTurboModule {
    public:
      NativePermissionsModuleSpecJSI(const ObjCTurboModule::InitParams &params);
    };
  } // namespace react
} // namespace facebook
inline JS::NativePermissionsModule::Constants::Builder::Builder(const Input i) : _factory(^{
  NSMutableDictionary *d = [NSMutableDictionary new];
  auto available = i.available;
  d[@"available"] = RCTConvertOptionalVecToArray(available, ^id(NSString * el_) { return el_; });
  return d;
}) {}
inline JS::NativePermissionsModule::Constants::Builder::Builder(Constants i) : _factory(^{
  return i.unsafeRawValue();
}) {}
