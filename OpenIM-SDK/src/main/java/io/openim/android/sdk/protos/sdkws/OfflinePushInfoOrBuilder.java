// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/sdkws.proto

package io.openim.android.sdk.protos.sdkws;

public interface OfflinePushInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:openim.sdkws.OfflinePushInfo)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <code>string title = 1;</code>
   * @return The title.
   */
  java.lang.String getTitle();
  /**
   * <code>string title = 1;</code>
   * @return The bytes for title.
   */
  com.google.protobuf.ByteString
      getTitleBytes();

  /**
   * <code>string desc = 2;</code>
   * @return The desc.
   */
  java.lang.String getDesc();
  /**
   * <code>string desc = 2;</code>
   * @return The bytes for desc.
   */
  com.google.protobuf.ByteString
      getDescBytes();

  /**
   * <code>string ex = 3;</code>
   * @return The ex.
   */
  java.lang.String getEx();
  /**
   * <code>string ex = 3;</code>
   * @return The bytes for ex.
   */
  com.google.protobuf.ByteString
      getExBytes();

  /**
   * <code>string iOSPushSound = 4;</code>
   * @return The iOSPushSound.
   */
  java.lang.String getIOSPushSound();
  /**
   * <code>string iOSPushSound = 4;</code>
   * @return The bytes for iOSPushSound.
   */
  com.google.protobuf.ByteString
      getIOSPushSoundBytes();

  /**
   * <code>bool iOSBadgeCount = 5;</code>
   * @return The iOSBadgeCount.
   */
  boolean getIOSBadgeCount();

  /**
   * <code>string signalInfo = 6;</code>
   * @return The signalInfo.
   */
  java.lang.String getSignalInfo();
  /**
   * <code>string signalInfo = 6;</code>
   * @return The bytes for signalInfo.
   */
  com.google.protobuf.ByteString
      getSignalInfoBytes();
}
