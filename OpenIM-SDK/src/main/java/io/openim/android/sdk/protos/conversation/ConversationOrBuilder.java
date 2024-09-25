// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/conversation.proto

package io.openim.android.sdk.protos.conversation;

public interface ConversationOrBuilder extends
    // @@protoc_insertion_point(interface_extends:openim.conversation.Conversation)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <code>string ownerUserID = 1;</code>
   * @return The ownerUserID.
   */
  java.lang.String getOwnerUserID();
  /**
   * <code>string ownerUserID = 1;</code>
   * @return The bytes for ownerUserID.
   */
  com.google.protobuf.ByteString
      getOwnerUserIDBytes();

  /**
   * <code>string conversationID = 2;</code>
   * @return The conversationID.
   */
  java.lang.String getConversationID();
  /**
   * <code>string conversationID = 2;</code>
   * @return The bytes for conversationID.
   */
  com.google.protobuf.ByteString
      getConversationIDBytes();

  /**
   * <code>int32 recvMsgOpt = 3;</code>
   * @return The recvMsgOpt.
   */
  int getRecvMsgOpt();

  /**
   * <code>int32 conversationType = 4;</code>
   * @return The conversationType.
   */
  int getConversationType();

  /**
   * <code>string userID = 5;</code>
   * @return The userID.
   */
  java.lang.String getUserID();
  /**
   * <code>string userID = 5;</code>
   * @return The bytes for userID.
   */
  com.google.protobuf.ByteString
      getUserIDBytes();

  /**
   * <code>string groupID = 6;</code>
   * @return The groupID.
   */
  java.lang.String getGroupID();
  /**
   * <code>string groupID = 6;</code>
   * @return The bytes for groupID.
   */
  com.google.protobuf.ByteString
      getGroupIDBytes();

  /**
   * <code>bool isPinned = 7;</code>
   * @return The isPinned.
   */
  boolean getIsPinned();

  /**
   * <code>string attachedInfo = 8;</code>
   * @return The attachedInfo.
   */
  java.lang.String getAttachedInfo();
  /**
   * <code>string attachedInfo = 8;</code>
   * @return The bytes for attachedInfo.
   */
  com.google.protobuf.ByteString
      getAttachedInfoBytes();

  /**
   * <code>bool isPrivateChat = 9;</code>
   * @return The isPrivateChat.
   */
  boolean getIsPrivateChat();

  /**
   * <code>int32 groupAtType = 10;</code>
   * @return The groupAtType.
   */
  int getGroupAtType();

  /**
   * <code>string ex = 11;</code>
   * @return The ex.
   */
  java.lang.String getEx();
  /**
   * <code>string ex = 11;</code>
   * @return The bytes for ex.
   */
  com.google.protobuf.ByteString
      getExBytes();

  /**
   * <code>int32 burnDuration = 12;</code>
   * @return The burnDuration.
   */
  int getBurnDuration();

  /**
   * <code>int64 minSeq = 13;</code>
   * @return The minSeq.
   */
  long getMinSeq();

  /**
   * <code>int64 maxSeq = 14;</code>
   * @return The maxSeq.
   */
  long getMaxSeq();

  /**
   * <code>int64 msgDestructTime = 15;</code>
   * @return The msgDestructTime.
   */
  long getMsgDestructTime();

  /**
   * <code>int64 latestMsgDestructTime = 16;</code>
   * @return The latestMsgDestructTime.
   */
  long getLatestMsgDestructTime();

  /**
   * <code>bool isMsgDestruct = 17;</code>
   * @return The isMsgDestruct.
   */
  boolean getIsMsgDestruct();
}
