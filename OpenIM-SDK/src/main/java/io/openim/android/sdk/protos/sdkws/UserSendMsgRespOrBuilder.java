// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/sdkws.proto

package io.openim.android.sdk.protos.sdkws;

public interface UserSendMsgRespOrBuilder extends
    // @@protoc_insertion_point(interface_extends:openim.sdkws.UserSendMsgResp)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <code>string serverMsgID = 1;</code>
   * @return The serverMsgID.
   */
  java.lang.String getServerMsgID();
  /**
   * <code>string serverMsgID = 1;</code>
   * @return The bytes for serverMsgID.
   */
  com.google.protobuf.ByteString
      getServerMsgIDBytes();

  /**
   * <code>string clientMsgID = 2;</code>
   * @return The clientMsgID.
   */
  java.lang.String getClientMsgID();
  /**
   * <code>string clientMsgID = 2;</code>
   * @return The bytes for clientMsgID.
   */
  com.google.protobuf.ByteString
      getClientMsgIDBytes();

  /**
   * <code>int64 sendTime = 3;</code>
   * @return The sendTime.
   */
  long getSendTime();
}