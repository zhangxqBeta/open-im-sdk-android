// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/sdkws.proto

package io.openim.android.sdk.protos.sdkws;

public interface UserInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:openim.sdkws.UserInfo)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <code>string userID = 1;</code>
   * @return The userID.
   */
  java.lang.String getUserID();
  /**
   * <code>string userID = 1;</code>
   * @return The bytes for userID.
   */
  com.google.protobuf.ByteString
      getUserIDBytes();

  /**
   * <code>string nickname = 2;</code>
   * @return The nickname.
   */
  java.lang.String getNickname();
  /**
   * <code>string nickname = 2;</code>
   * @return The bytes for nickname.
   */
  com.google.protobuf.ByteString
      getNicknameBytes();

  /**
   * <code>string faceURL = 3;</code>
   * @return The faceURL.
   */
  java.lang.String getFaceURL();
  /**
   * <code>string faceURL = 3;</code>
   * @return The bytes for faceURL.
   */
  com.google.protobuf.ByteString
      getFaceURLBytes();

  /**
   * <code>string ex = 4;</code>
   * @return The ex.
   */
  java.lang.String getEx();
  /**
   * <code>string ex = 4;</code>
   * @return The bytes for ex.
   */
  com.google.protobuf.ByteString
      getExBytes();

  /**
   * <code>int64 createTime = 5;</code>
   * @return The createTime.
   */
  long getCreateTime();

  /**
   * <code>int32 appMangerLevel = 6;</code>
   * @return The appMangerLevel.
   */
  int getAppMangerLevel();

  /**
   * <code>int32 globalRecvMsgOpt = 7;</code>
   * @return The globalRecvMsgOpt.
   */
  int getGlobalRecvMsgOpt();
}