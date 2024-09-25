// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/sdkws.proto

package io.openim.android.sdk.protos.sdkws;

public interface GetMaxSeqRespOrBuilder extends
    // @@protoc_insertion_point(interface_extends:openim.sdkws.GetMaxSeqResp)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <code>map&lt;string, int64&gt; maxSeqs = 1;</code>
   */
  int getMaxSeqsCount();
  /**
   * <code>map&lt;string, int64&gt; maxSeqs = 1;</code>
   */
  boolean containsMaxSeqs(
      java.lang.String key);
  /**
   * Use {@link #getMaxSeqsMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.Long>
  getMaxSeqs();
  /**
   * <code>map&lt;string, int64&gt; maxSeqs = 1;</code>
   */
  java.util.Map<java.lang.String, java.lang.Long>
  getMaxSeqsMap();
  /**
   * <code>map&lt;string, int64&gt; maxSeqs = 1;</code>
   */

  long getMaxSeqsOrDefault(
      java.lang.String key,
      long defaultValue);
  /**
   * <code>map&lt;string, int64&gt; maxSeqs = 1;</code>
   */

  long getMaxSeqsOrThrow(
      java.lang.String key);

  /**
   * <code>map&lt;string, int64&gt; minSeqs = 2;</code>
   */
  int getMinSeqsCount();
  /**
   * <code>map&lt;string, int64&gt; minSeqs = 2;</code>
   */
  boolean containsMinSeqs(
      java.lang.String key);
  /**
   * Use {@link #getMinSeqsMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.Long>
  getMinSeqs();
  /**
   * <code>map&lt;string, int64&gt; minSeqs = 2;</code>
   */
  java.util.Map<java.lang.String, java.lang.Long>
  getMinSeqsMap();
  /**
   * <code>map&lt;string, int64&gt; minSeqs = 2;</code>
   */

  long getMinSeqsOrDefault(
      java.lang.String key,
      long defaultValue);
  /**
   * <code>map&lt;string, int64&gt; minSeqs = 2;</code>
   */

  long getMinSeqsOrThrow(
      java.lang.String key);
}