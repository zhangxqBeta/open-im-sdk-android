// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/msg.proto

package io.openim.android.sdk.protos.msg;

public interface GetConversationsHasReadAndMaxSeqReqOrBuilder extends
    // @@protoc_insertion_point(interface_extends:openim.msg.GetConversationsHasReadAndMaxSeqReq)
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
   * <code>repeated string conversationIDs = 2;</code>
   * @return A list containing the conversationIDs.
   */
  java.util.List<java.lang.String>
      getConversationIDsList();
  /**
   * <code>repeated string conversationIDs = 2;</code>
   * @return The count of conversationIDs.
   */
  int getConversationIDsCount();
  /**
   * <code>repeated string conversationIDs = 2;</code>
   * @param index The index of the element to return.
   * @return The conversationIDs at the given index.
   */
  java.lang.String getConversationIDs(int index);
  /**
   * <code>repeated string conversationIDs = 2;</code>
   * @param index The index of the element to return.
   * @return The conversationIDs at the given index.
   */
  com.google.protobuf.ByteString
      getConversationIDsBytes(int index);
}