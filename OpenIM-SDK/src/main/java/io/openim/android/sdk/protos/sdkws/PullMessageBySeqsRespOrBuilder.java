// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/sdkws.proto

package io.openim.android.sdk.protos.sdkws;

public interface PullMessageBySeqsRespOrBuilder extends
    // @@protoc_insertion_point(interface_extends:openim.sdkws.PullMessageBySeqsResp)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
   */
  int getMsgsCount();
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
   */
  boolean containsMsgs(
      java.lang.String key);
  /**
   * Use {@link #getMsgsMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs>
  getMsgs();
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
   */
  java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs>
  getMsgsMap();
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
   */

  /* nullable */
io.openim.android.sdk.protos.sdkws.PullMsgs getMsgsOrDefault(
      java.lang.String key,
      /* nullable */
io.openim.android.sdk.protos.sdkws.PullMsgs defaultValue);
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
   */

  io.openim.android.sdk.protos.sdkws.PullMsgs getMsgsOrThrow(
      java.lang.String key);

  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
   */
  int getNotificationMsgsCount();
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
   */
  boolean containsNotificationMsgs(
      java.lang.String key);
  /**
   * Use {@link #getNotificationMsgsMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs>
  getNotificationMsgs();
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
   */
  java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs>
  getNotificationMsgsMap();
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
   */

  /* nullable */
io.openim.android.sdk.protos.sdkws.PullMsgs getNotificationMsgsOrDefault(
      java.lang.String key,
      /* nullable */
io.openim.android.sdk.protos.sdkws.PullMsgs defaultValue);
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
   */

  io.openim.android.sdk.protos.sdkws.PullMsgs getNotificationMsgsOrThrow(
      java.lang.String key);
}