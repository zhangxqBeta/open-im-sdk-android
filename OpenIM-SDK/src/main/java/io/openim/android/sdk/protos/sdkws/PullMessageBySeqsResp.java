// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/sdkws.proto

package io.openim.android.sdk.protos.sdkws;

/**
 * Protobuf type {@code openim.sdkws.PullMessageBySeqsResp}
 */
public  final class PullMessageBySeqsResp extends
    com.google.protobuf.GeneratedMessageLite<
        PullMessageBySeqsResp, PullMessageBySeqsResp.Builder> implements
    // @@protoc_insertion_point(message_implements:openim.sdkws.PullMessageBySeqsResp)
    PullMessageBySeqsRespOrBuilder {
  private PullMessageBySeqsResp() {
  }
  public static final int MSGS_FIELD_NUMBER = 1;
  private static final class MsgsDefaultEntryHolder {
    static final com.google.protobuf.MapEntryLite<
        java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> defaultEntry =
            com.google.protobuf.MapEntryLite
            .<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs>newDefaultInstance(
                com.google.protobuf.WireFormat.FieldType.STRING,
                "",
                com.google.protobuf.WireFormat.FieldType.MESSAGE,
                io.openim.android.sdk.protos.sdkws.PullMsgs.getDefaultInstance());
  }
  private com.google.protobuf.MapFieldLite<
      java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> msgs_ =
          com.google.protobuf.MapFieldLite.emptyMapField();
  private com.google.protobuf.MapFieldLite<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs>
  internalGetMsgs() {
    return msgs_;
  }
  private com.google.protobuf.MapFieldLite<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs>
  internalGetMutableMsgs() {
    if (!msgs_.isMutable()) {
      msgs_ = msgs_.mutableCopy();
    }
    return msgs_;
  }
  @java.lang.Override

  public int getMsgsCount() {
    return internalGetMsgs().size();
  }
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
   */
  @java.lang.Override

  public boolean containsMsgs(
      java.lang.String key) {
    java.lang.Class<?> keyClass = key.getClass();
    return internalGetMsgs().containsKey(key);
  }
  /**
   * Use {@link #getMsgsMap()} instead.
   */
  @java.lang.Override
  @java.lang.Deprecated
  public java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> getMsgs() {
    return getMsgsMap();
  }
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
   */
  @java.lang.Override

  public java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> getMsgsMap() {
    return java.util.Collections.unmodifiableMap(
        internalGetMsgs());
  }
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
   */
  @java.lang.Override

  public io.openim.android.sdk.protos.sdkws.PullMsgs getMsgsOrDefault(
      java.lang.String key,
      io.openim.android.sdk.protos.sdkws.PullMsgs defaultValue) {
    java.lang.Class<?> keyClass = key.getClass();
    java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> map =
        internalGetMsgs();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
   */
  @java.lang.Override

  public io.openim.android.sdk.protos.sdkws.PullMsgs getMsgsOrThrow(
      java.lang.String key) {
    java.lang.Class<?> keyClass = key.getClass();
    java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> map =
        internalGetMsgs();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
   */
  private java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs>
  getMutableMsgsMap() {
    return internalGetMutableMsgs();
  }

  public static final int NOTIFICATIONMSGS_FIELD_NUMBER = 2;
  private static final class NotificationMsgsDefaultEntryHolder {
    static final com.google.protobuf.MapEntryLite<
        java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> defaultEntry =
            com.google.protobuf.MapEntryLite
            .<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs>newDefaultInstance(
                com.google.protobuf.WireFormat.FieldType.STRING,
                "",
                com.google.protobuf.WireFormat.FieldType.MESSAGE,
                io.openim.android.sdk.protos.sdkws.PullMsgs.getDefaultInstance());
  }
  private com.google.protobuf.MapFieldLite<
      java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> notificationMsgs_ =
          com.google.protobuf.MapFieldLite.emptyMapField();
  private com.google.protobuf.MapFieldLite<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs>
  internalGetNotificationMsgs() {
    return notificationMsgs_;
  }
  private com.google.protobuf.MapFieldLite<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs>
  internalGetMutableNotificationMsgs() {
    if (!notificationMsgs_.isMutable()) {
      notificationMsgs_ = notificationMsgs_.mutableCopy();
    }
    return notificationMsgs_;
  }
  @java.lang.Override

  public int getNotificationMsgsCount() {
    return internalGetNotificationMsgs().size();
  }
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
   */
  @java.lang.Override

  public boolean containsNotificationMsgs(
      java.lang.String key) {
    java.lang.Class<?> keyClass = key.getClass();
    return internalGetNotificationMsgs().containsKey(key);
  }
  /**
   * Use {@link #getNotificationMsgsMap()} instead.
   */
  @java.lang.Override
  @java.lang.Deprecated
  public java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> getNotificationMsgs() {
    return getNotificationMsgsMap();
  }
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
   */
  @java.lang.Override

  public java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> getNotificationMsgsMap() {
    return java.util.Collections.unmodifiableMap(
        internalGetNotificationMsgs());
  }
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
   */
  @java.lang.Override

  public io.openim.android.sdk.protos.sdkws.PullMsgs getNotificationMsgsOrDefault(
      java.lang.String key,
      io.openim.android.sdk.protos.sdkws.PullMsgs defaultValue) {
    java.lang.Class<?> keyClass = key.getClass();
    java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> map =
        internalGetNotificationMsgs();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
   */
  @java.lang.Override

  public io.openim.android.sdk.protos.sdkws.PullMsgs getNotificationMsgsOrThrow(
      java.lang.String key) {
    java.lang.Class<?> keyClass = key.getClass();
    java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> map =
        internalGetNotificationMsgs();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }
  /**
   * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
   */
  private java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs>
  getMutableNotificationMsgsMap() {
    return internalGetMutableNotificationMsgs();
  }

  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }
  public static Builder newBuilder(io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp prototype) {
    return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   * Protobuf type {@code openim.sdkws.PullMessageBySeqsResp}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp, Builder> implements
      // @@protoc_insertion_point(builder_implements:openim.sdkws.PullMessageBySeqsResp)
      io.openim.android.sdk.protos.sdkws.PullMessageBySeqsRespOrBuilder {
    // Construct using io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    @java.lang.Override

    public int getMsgsCount() {
      return instance.getMsgsMap().size();
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
     */
    @java.lang.Override

    public boolean containsMsgs(
        java.lang.String key) {
      java.lang.Class<?> keyClass = key.getClass();
      return instance.getMsgsMap().containsKey(key);
    }

    public Builder clearMsgs() {
      copyOnWrite();
      instance.getMutableMsgsMap().clear();
      return this;
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
     */

    public Builder removeMsgs(
        java.lang.String key) {
      java.lang.Class<?> keyClass = key.getClass();
      copyOnWrite();
      instance.getMutableMsgsMap().remove(key);
      return this;
    }
    /**
     * Use {@link #getMsgsMap()} instead.
     */
    @java.lang.Override
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> getMsgs() {
      return getMsgsMap();
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
     */
    @java.lang.Override
    public java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> getMsgsMap() {
      return java.util.Collections.unmodifiableMap(
          instance.getMsgsMap());
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
     */
    @java.lang.Override

    public io.openim.android.sdk.protos.sdkws.PullMsgs getMsgsOrDefault(
        java.lang.String key,
        io.openim.android.sdk.protos.sdkws.PullMsgs defaultValue) {
      java.lang.Class<?> keyClass = key.getClass();
      java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> map =
          instance.getMsgsMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
     */
    @java.lang.Override

    public io.openim.android.sdk.protos.sdkws.PullMsgs getMsgsOrThrow(
        java.lang.String key) {
      java.lang.Class<?> keyClass = key.getClass();
      java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> map =
          instance.getMsgsMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
     */
    public Builder putMsgs(
        java.lang.String key,
        io.openim.android.sdk.protos.sdkws.PullMsgs value) {
      java.lang.Class<?> keyClass = key.getClass();
      java.lang.Class<?> valueClass = value.getClass();
      copyOnWrite();
      instance.getMutableMsgsMap().put(key, value);
      return this;
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; msgs = 1;</code>
     */
    public Builder putAllMsgs(
        java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> values) {
      copyOnWrite();
      instance.getMutableMsgsMap().putAll(values);
      return this;
    }

    @java.lang.Override

    public int getNotificationMsgsCount() {
      return instance.getNotificationMsgsMap().size();
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
     */
    @java.lang.Override

    public boolean containsNotificationMsgs(
        java.lang.String key) {
      java.lang.Class<?> keyClass = key.getClass();
      return instance.getNotificationMsgsMap().containsKey(key);
    }

    public Builder clearNotificationMsgs() {
      copyOnWrite();
      instance.getMutableNotificationMsgsMap().clear();
      return this;
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
     */

    public Builder removeNotificationMsgs(
        java.lang.String key) {
      java.lang.Class<?> keyClass = key.getClass();
      copyOnWrite();
      instance.getMutableNotificationMsgsMap().remove(key);
      return this;
    }
    /**
     * Use {@link #getNotificationMsgsMap()} instead.
     */
    @java.lang.Override
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> getNotificationMsgs() {
      return getNotificationMsgsMap();
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
     */
    @java.lang.Override
    public java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> getNotificationMsgsMap() {
      return java.util.Collections.unmodifiableMap(
          instance.getNotificationMsgsMap());
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
     */
    @java.lang.Override

    public io.openim.android.sdk.protos.sdkws.PullMsgs getNotificationMsgsOrDefault(
        java.lang.String key,
        io.openim.android.sdk.protos.sdkws.PullMsgs defaultValue) {
      java.lang.Class<?> keyClass = key.getClass();
      java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> map =
          instance.getNotificationMsgsMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
     */
    @java.lang.Override

    public io.openim.android.sdk.protos.sdkws.PullMsgs getNotificationMsgsOrThrow(
        java.lang.String key) {
      java.lang.Class<?> keyClass = key.getClass();
      java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> map =
          instance.getNotificationMsgsMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
     */
    public Builder putNotificationMsgs(
        java.lang.String key,
        io.openim.android.sdk.protos.sdkws.PullMsgs value) {
      java.lang.Class<?> keyClass = key.getClass();
      java.lang.Class<?> valueClass = value.getClass();
      copyOnWrite();
      instance.getMutableNotificationMsgsMap().put(key, value);
      return this;
    }
    /**
     * <code>map&lt;string, .openim.sdkws.PullMsgs&gt; notificationMsgs = 2;</code>
     */
    public Builder putAllNotificationMsgs(
        java.util.Map<java.lang.String, io.openim.android.sdk.protos.sdkws.PullMsgs> values) {
      copyOnWrite();
      instance.getMutableNotificationMsgsMap().putAll(values);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:openim.sdkws.PullMessageBySeqsResp)
  }
  @java.lang.Override
  @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
  protected final java.lang.Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      java.lang.Object arg0, java.lang.Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp();
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case BUILD_MESSAGE_INFO: {
          java.lang.Object[] objects = new java.lang.Object[] {
            "msgs_",
            MsgsDefaultEntryHolder.defaultEntry,
            "notificationMsgs_",
            NotificationMsgsDefaultEntryHolder.defaultEntry,
          };
          java.lang.String info =
              "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0002\u0000\u0000\u00012\u00022";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
      }
      // fall through
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        com.google.protobuf.Parser<io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp> parser = PARSER;
        if (parser == null) {
          synchronized (io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp.class) {
            parser = PARSER;
            if (parser == null) {
              parser =
                  new DefaultInstanceBasedParser<io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp>(
                      DEFAULT_INSTANCE);
              PARSER = parser;
            }
          }
        }
        return parser;
    }
    case GET_MEMOIZED_IS_INITIALIZED: {
      return (byte) 1;
    }
    case SET_MEMOIZED_IS_INITIALIZED: {
      return null;
    }
    }
    throw new UnsupportedOperationException();
  }


  // @@protoc_insertion_point(class_scope:openim.sdkws.PullMessageBySeqsResp)
  private static final io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp DEFAULT_INSTANCE;
  static {
    PullMessageBySeqsResp defaultInstance = new PullMessageBySeqsResp();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
      PullMessageBySeqsResp.class, defaultInstance);
  }

  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<PullMessageBySeqsResp> PARSER;

  public static com.google.protobuf.Parser<PullMessageBySeqsResp> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
