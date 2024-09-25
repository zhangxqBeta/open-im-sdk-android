// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user.proto

package io.openim.android.sdk.protos.user;

/**
 * Protobuf type {@code openim.user.GetDesignateUsersReq}
 */
public  final class GetDesignateUsersReq extends
    com.google.protobuf.GeneratedMessageLite<
        GetDesignateUsersReq, GetDesignateUsersReq.Builder> implements
    // @@protoc_insertion_point(message_implements:openim.user.GetDesignateUsersReq)
    GetDesignateUsersReqOrBuilder {
  private GetDesignateUsersReq() {
    userIDs_ = com.google.protobuf.GeneratedMessageLite.emptyProtobufList();
  }
  public static final int USERIDS_FIELD_NUMBER = 1;
  private com.google.protobuf.Internal.ProtobufList<java.lang.String> userIDs_;
  /**
   * <code>repeated string userIDs = 1;</code>
   * @return A list containing the userIDs.
   */
  @java.lang.Override
  public java.util.List<java.lang.String> getUserIDsList() {
    return userIDs_;
  }
  /**
   * <code>repeated string userIDs = 1;</code>
   * @return The count of userIDs.
   */
  @java.lang.Override
  public int getUserIDsCount() {
    return userIDs_.size();
  }
  /**
   * <code>repeated string userIDs = 1;</code>
   * @param index The index of the element to return.
   * @return The userIDs at the given index.
   */
  @java.lang.Override
  public java.lang.String getUserIDs(int index) {
    return userIDs_.get(index);
  }
  /**
   * <code>repeated string userIDs = 1;</code>
   * @param index The index of the value to return.
   * @return The bytes of the userIDs at the given index.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getUserIDsBytes(int index) {
    return com.google.protobuf.ByteString.copyFromUtf8(
        userIDs_.get(index));
  }
  private void ensureUserIDsIsMutable() {
    com.google.protobuf.Internal.ProtobufList<java.lang.String> tmp =
        userIDs_;  if (!tmp.isModifiable()) {
      userIDs_ =
          com.google.protobuf.GeneratedMessageLite.mutableCopy(tmp);
     }
  }
  /**
   * <code>repeated string userIDs = 1;</code>
   * @param index The index to set the value at.
   * @param value The userIDs to set.
   */
  private void setUserIDs(
      int index, java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();
  ensureUserIDsIsMutable();
    userIDs_.set(index, value);
  }
  /**
   * <code>repeated string userIDs = 1;</code>
   * @param value The userIDs to add.
   */
  private void addUserIDs(
      java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();
  ensureUserIDsIsMutable();
    userIDs_.add(value);
  }
  /**
   * <code>repeated string userIDs = 1;</code>
   * @param values The userIDs to add.
   */
  private void addAllUserIDs(
      java.lang.Iterable<java.lang.String> values) {
    ensureUserIDsIsMutable();
    com.google.protobuf.AbstractMessageLite.addAll(
        values, userIDs_);
  }
  /**
   * <code>repeated string userIDs = 1;</code>
   */
  private void clearUserIDs() {
    userIDs_ = com.google.protobuf.GeneratedMessageLite.emptyProtobufList();
  }
  /**
   * <code>repeated string userIDs = 1;</code>
   * @param value The bytes of the userIDs to add.
   */
  private void addUserIDsBytes(
      com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    ensureUserIDsIsMutable();
    userIDs_.add(value.toStringUtf8());
  }

  public static io.openim.android.sdk.protos.user.GetDesignateUsersReq parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.user.GetDesignateUsersReq parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.user.GetDesignateUsersReq parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.user.GetDesignateUsersReq parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.user.GetDesignateUsersReq parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.user.GetDesignateUsersReq parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.user.GetDesignateUsersReq parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.user.GetDesignateUsersReq parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.user.GetDesignateUsersReq parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.user.GetDesignateUsersReq parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.user.GetDesignateUsersReq parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.user.GetDesignateUsersReq parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }
  public static Builder newBuilder(io.openim.android.sdk.protos.user.GetDesignateUsersReq prototype) {
    return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   * Protobuf type {@code openim.user.GetDesignateUsersReq}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        io.openim.android.sdk.protos.user.GetDesignateUsersReq, Builder> implements
      // @@protoc_insertion_point(builder_implements:openim.user.GetDesignateUsersReq)
      io.openim.android.sdk.protos.user.GetDesignateUsersReqOrBuilder {
    // Construct using io.openim.android.sdk.protos.user.GetDesignateUsersReq.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <code>repeated string userIDs = 1;</code>
     * @return A list containing the userIDs.
     */
    @java.lang.Override
    public java.util.List<java.lang.String>
        getUserIDsList() {
      return java.util.Collections.unmodifiableList(
          instance.getUserIDsList());
    }
    /**
     * <code>repeated string userIDs = 1;</code>
     * @return The count of userIDs.
     */
    @java.lang.Override
    public int getUserIDsCount() {
      return instance.getUserIDsCount();
    }
    /**
     * <code>repeated string userIDs = 1;</code>
     * @param index The index of the element to return.
     * @return The userIDs at the given index.
     */
    @java.lang.Override
    public java.lang.String getUserIDs(int index) {
      return instance.getUserIDs(index);
    }
    /**
     * <code>repeated string userIDs = 1;</code>
     * @param index The index of the value to return.
     * @return The bytes of the userIDs at the given index.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getUserIDsBytes(int index) {
      return instance.getUserIDsBytes(index);
    }
    /**
     * <code>repeated string userIDs = 1;</code>
     * @param index The index to set the value at.
     * @param value The userIDs to set.
     * @return This builder for chaining.
     */
    public Builder setUserIDs(
        int index, java.lang.String value) {
      copyOnWrite();
      instance.setUserIDs(index, value);
      return this;
    }
    /**
     * <code>repeated string userIDs = 1;</code>
     * @param value The userIDs to add.
     * @return This builder for chaining.
     */
    public Builder addUserIDs(
        java.lang.String value) {
      copyOnWrite();
      instance.addUserIDs(value);
      return this;
    }
    /**
     * <code>repeated string userIDs = 1;</code>
     * @param values The userIDs to add.
     * @return This builder for chaining.
     */
    public Builder addAllUserIDs(
        java.lang.Iterable<java.lang.String> values) {
      copyOnWrite();
      instance.addAllUserIDs(values);
      return this;
    }
    /**
     * <code>repeated string userIDs = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearUserIDs() {
      copyOnWrite();
      instance.clearUserIDs();
      return this;
    }
    /**
     * <code>repeated string userIDs = 1;</code>
     * @param value The bytes of the userIDs to add.
     * @return This builder for chaining.
     */
    public Builder addUserIDsBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.addUserIDsBytes(value);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:openim.user.GetDesignateUsersReq)
  }
  @java.lang.Override
  @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
  protected final java.lang.Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      java.lang.Object arg0, java.lang.Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new io.openim.android.sdk.protos.user.GetDesignateUsersReq();
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case BUILD_MESSAGE_INFO: {
          java.lang.Object[] objects = new java.lang.Object[] {
            "userIDs_",
          };
          java.lang.String info =
              "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u021a";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
      }
      // fall through
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        com.google.protobuf.Parser<io.openim.android.sdk.protos.user.GetDesignateUsersReq> parser = PARSER;
        if (parser == null) {
          synchronized (io.openim.android.sdk.protos.user.GetDesignateUsersReq.class) {
            parser = PARSER;
            if (parser == null) {
              parser =
                  new DefaultInstanceBasedParser<io.openim.android.sdk.protos.user.GetDesignateUsersReq>(
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


  // @@protoc_insertion_point(class_scope:openim.user.GetDesignateUsersReq)
  private static final io.openim.android.sdk.protos.user.GetDesignateUsersReq DEFAULT_INSTANCE;
  static {
    GetDesignateUsersReq defaultInstance = new GetDesignateUsersReq();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
      GetDesignateUsersReq.class, defaultInstance);
  }

  public static io.openim.android.sdk.protos.user.GetDesignateUsersReq getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<GetDesignateUsersReq> PARSER;

  public static com.google.protobuf.Parser<GetDesignateUsersReq> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
