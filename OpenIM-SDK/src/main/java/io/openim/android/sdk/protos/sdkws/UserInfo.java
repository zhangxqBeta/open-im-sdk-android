// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/sdkws.proto

package io.openim.android.sdk.protos.sdkws;

/**
 * Protobuf type {@code openim.sdkws.UserInfo}
 */
public  final class UserInfo extends
    com.google.protobuf.GeneratedMessageLite<
        UserInfo, UserInfo.Builder> implements
    // @@protoc_insertion_point(message_implements:openim.sdkws.UserInfo)
    UserInfoOrBuilder {
  private UserInfo() {
    userID_ = "";
    nickname_ = "";
    faceURL_ = "";
    ex_ = "";
  }
  public static final int USERID_FIELD_NUMBER = 1;
  private java.lang.String userID_;
  /**
   * <code>string userID = 1;</code>
   * @return The userID.
   */
  @java.lang.Override
  public java.lang.String getUserID() {
    return userID_;
  }
  /**
   * <code>string userID = 1;</code>
   * @return The bytes for userID.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getUserIDBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(userID_);
  }
  /**
   * <code>string userID = 1;</code>
   * @param value The userID to set.
   */
  private void setUserID(
      java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();
  
    userID_ = value;
  }
  /**
   * <code>string userID = 1;</code>
   */
  private void clearUserID() {
    
    userID_ = getDefaultInstance().getUserID();
  }
  /**
   * <code>string userID = 1;</code>
   * @param value The bytes for userID to set.
   */
  private void setUserIDBytes(
      com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    userID_ = value.toStringUtf8();
    
  }

  public static final int NICKNAME_FIELD_NUMBER = 2;
  private java.lang.String nickname_;
  /**
   * <code>string nickname = 2;</code>
   * @return The nickname.
   */
  @java.lang.Override
  public java.lang.String getNickname() {
    return nickname_;
  }
  /**
   * <code>string nickname = 2;</code>
   * @return The bytes for nickname.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getNicknameBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(nickname_);
  }
  /**
   * <code>string nickname = 2;</code>
   * @param value The nickname to set.
   */
  private void setNickname(
      java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();
  
    nickname_ = value;
  }
  /**
   * <code>string nickname = 2;</code>
   */
  private void clearNickname() {
    
    nickname_ = getDefaultInstance().getNickname();
  }
  /**
   * <code>string nickname = 2;</code>
   * @param value The bytes for nickname to set.
   */
  private void setNicknameBytes(
      com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    nickname_ = value.toStringUtf8();
    
  }

  public static final int FACEURL_FIELD_NUMBER = 3;
  private java.lang.String faceURL_;
  /**
   * <code>string faceURL = 3;</code>
   * @return The faceURL.
   */
  @java.lang.Override
  public java.lang.String getFaceURL() {
    return faceURL_;
  }
  /**
   * <code>string faceURL = 3;</code>
   * @return The bytes for faceURL.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getFaceURLBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(faceURL_);
  }
  /**
   * <code>string faceURL = 3;</code>
   * @param value The faceURL to set.
   */
  private void setFaceURL(
      java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();
  
    faceURL_ = value;
  }
  /**
   * <code>string faceURL = 3;</code>
   */
  private void clearFaceURL() {
    
    faceURL_ = getDefaultInstance().getFaceURL();
  }
  /**
   * <code>string faceURL = 3;</code>
   * @param value The bytes for faceURL to set.
   */
  private void setFaceURLBytes(
      com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    faceURL_ = value.toStringUtf8();
    
  }

  public static final int EX_FIELD_NUMBER = 4;
  private java.lang.String ex_;
  /**
   * <code>string ex = 4;</code>
   * @return The ex.
   */
  @java.lang.Override
  public java.lang.String getEx() {
    return ex_;
  }
  /**
   * <code>string ex = 4;</code>
   * @return The bytes for ex.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getExBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(ex_);
  }
  /**
   * <code>string ex = 4;</code>
   * @param value The ex to set.
   */
  private void setEx(
      java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();
  
    ex_ = value;
  }
  /**
   * <code>string ex = 4;</code>
   */
  private void clearEx() {
    
    ex_ = getDefaultInstance().getEx();
  }
  /**
   * <code>string ex = 4;</code>
   * @param value The bytes for ex to set.
   */
  private void setExBytes(
      com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    ex_ = value.toStringUtf8();
    
  }

  public static final int CREATETIME_FIELD_NUMBER = 5;
  private long createTime_;
  /**
   * <code>int64 createTime = 5;</code>
   * @return The createTime.
   */
  @java.lang.Override
  public long getCreateTime() {
    return createTime_;
  }
  /**
   * <code>int64 createTime = 5;</code>
   * @param value The createTime to set.
   */
  private void setCreateTime(long value) {
    
    createTime_ = value;
  }
  /**
   * <code>int64 createTime = 5;</code>
   */
  private void clearCreateTime() {
    
    createTime_ = 0L;
  }

  public static final int APPMANGERLEVEL_FIELD_NUMBER = 6;
  private int appMangerLevel_;
  /**
   * <code>int32 appMangerLevel = 6;</code>
   * @return The appMangerLevel.
   */
  @java.lang.Override
  public int getAppMangerLevel() {
    return appMangerLevel_;
  }
  /**
   * <code>int32 appMangerLevel = 6;</code>
   * @param value The appMangerLevel to set.
   */
  private void setAppMangerLevel(int value) {
    
    appMangerLevel_ = value;
  }
  /**
   * <code>int32 appMangerLevel = 6;</code>
   */
  private void clearAppMangerLevel() {
    
    appMangerLevel_ = 0;
  }

  public static final int GLOBALRECVMSGOPT_FIELD_NUMBER = 7;
  private int globalRecvMsgOpt_;
  /**
   * <code>int32 globalRecvMsgOpt = 7;</code>
   * @return The globalRecvMsgOpt.
   */
  @java.lang.Override
  public int getGlobalRecvMsgOpt() {
    return globalRecvMsgOpt_;
  }
  /**
   * <code>int32 globalRecvMsgOpt = 7;</code>
   * @param value The globalRecvMsgOpt to set.
   */
  private void setGlobalRecvMsgOpt(int value) {
    
    globalRecvMsgOpt_ = value;
  }
  /**
   * <code>int32 globalRecvMsgOpt = 7;</code>
   */
  private void clearGlobalRecvMsgOpt() {
    
    globalRecvMsgOpt_ = 0;
  }

  public static io.openim.android.sdk.protos.sdkws.UserInfo parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.sdkws.UserInfo parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.UserInfo parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.sdkws.UserInfo parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.UserInfo parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.sdkws.UserInfo parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.UserInfo parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.sdkws.UserInfo parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.UserInfo parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.sdkws.UserInfo parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.UserInfo parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.sdkws.UserInfo parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }
  public static Builder newBuilder(io.openim.android.sdk.protos.sdkws.UserInfo prototype) {
    return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   * Protobuf type {@code openim.sdkws.UserInfo}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        io.openim.android.sdk.protos.sdkws.UserInfo, Builder> implements
      // @@protoc_insertion_point(builder_implements:openim.sdkws.UserInfo)
      io.openim.android.sdk.protos.sdkws.UserInfoOrBuilder {
    // Construct using io.openim.android.sdk.protos.sdkws.UserInfo.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <code>string userID = 1;</code>
     * @return The userID.
     */
    @java.lang.Override
    public java.lang.String getUserID() {
      return instance.getUserID();
    }
    /**
     * <code>string userID = 1;</code>
     * @return The bytes for userID.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getUserIDBytes() {
      return instance.getUserIDBytes();
    }
    /**
     * <code>string userID = 1;</code>
     * @param value The userID to set.
     * @return This builder for chaining.
     */
    public Builder setUserID(
        java.lang.String value) {
      copyOnWrite();
      instance.setUserID(value);
      return this;
    }
    /**
     * <code>string userID = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearUserID() {
      copyOnWrite();
      instance.clearUserID();
      return this;
    }
    /**
     * <code>string userID = 1;</code>
     * @param value The bytes for userID to set.
     * @return This builder for chaining.
     */
    public Builder setUserIDBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setUserIDBytes(value);
      return this;
    }

    /**
     * <code>string nickname = 2;</code>
     * @return The nickname.
     */
    @java.lang.Override
    public java.lang.String getNickname() {
      return instance.getNickname();
    }
    /**
     * <code>string nickname = 2;</code>
     * @return The bytes for nickname.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getNicknameBytes() {
      return instance.getNicknameBytes();
    }
    /**
     * <code>string nickname = 2;</code>
     * @param value The nickname to set.
     * @return This builder for chaining.
     */
    public Builder setNickname(
        java.lang.String value) {
      copyOnWrite();
      instance.setNickname(value);
      return this;
    }
    /**
     * <code>string nickname = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearNickname() {
      copyOnWrite();
      instance.clearNickname();
      return this;
    }
    /**
     * <code>string nickname = 2;</code>
     * @param value The bytes for nickname to set.
     * @return This builder for chaining.
     */
    public Builder setNicknameBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setNicknameBytes(value);
      return this;
    }

    /**
     * <code>string faceURL = 3;</code>
     * @return The faceURL.
     */
    @java.lang.Override
    public java.lang.String getFaceURL() {
      return instance.getFaceURL();
    }
    /**
     * <code>string faceURL = 3;</code>
     * @return The bytes for faceURL.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getFaceURLBytes() {
      return instance.getFaceURLBytes();
    }
    /**
     * <code>string faceURL = 3;</code>
     * @param value The faceURL to set.
     * @return This builder for chaining.
     */
    public Builder setFaceURL(
        java.lang.String value) {
      copyOnWrite();
      instance.setFaceURL(value);
      return this;
    }
    /**
     * <code>string faceURL = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearFaceURL() {
      copyOnWrite();
      instance.clearFaceURL();
      return this;
    }
    /**
     * <code>string faceURL = 3;</code>
     * @param value The bytes for faceURL to set.
     * @return This builder for chaining.
     */
    public Builder setFaceURLBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setFaceURLBytes(value);
      return this;
    }

    /**
     * <code>string ex = 4;</code>
     * @return The ex.
     */
    @java.lang.Override
    public java.lang.String getEx() {
      return instance.getEx();
    }
    /**
     * <code>string ex = 4;</code>
     * @return The bytes for ex.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getExBytes() {
      return instance.getExBytes();
    }
    /**
     * <code>string ex = 4;</code>
     * @param value The ex to set.
     * @return This builder for chaining.
     */
    public Builder setEx(
        java.lang.String value) {
      copyOnWrite();
      instance.setEx(value);
      return this;
    }
    /**
     * <code>string ex = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearEx() {
      copyOnWrite();
      instance.clearEx();
      return this;
    }
    /**
     * <code>string ex = 4;</code>
     * @param value The bytes for ex to set.
     * @return This builder for chaining.
     */
    public Builder setExBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setExBytes(value);
      return this;
    }

    /**
     * <code>int64 createTime = 5;</code>
     * @return The createTime.
     */
    @java.lang.Override
    public long getCreateTime() {
      return instance.getCreateTime();
    }
    /**
     * <code>int64 createTime = 5;</code>
     * @param value The createTime to set.
     * @return This builder for chaining.
     */
    public Builder setCreateTime(long value) {
      copyOnWrite();
      instance.setCreateTime(value);
      return this;
    }
    /**
     * <code>int64 createTime = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearCreateTime() {
      copyOnWrite();
      instance.clearCreateTime();
      return this;
    }

    /**
     * <code>int32 appMangerLevel = 6;</code>
     * @return The appMangerLevel.
     */
    @java.lang.Override
    public int getAppMangerLevel() {
      return instance.getAppMangerLevel();
    }
    /**
     * <code>int32 appMangerLevel = 6;</code>
     * @param value The appMangerLevel to set.
     * @return This builder for chaining.
     */
    public Builder setAppMangerLevel(int value) {
      copyOnWrite();
      instance.setAppMangerLevel(value);
      return this;
    }
    /**
     * <code>int32 appMangerLevel = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearAppMangerLevel() {
      copyOnWrite();
      instance.clearAppMangerLevel();
      return this;
    }

    /**
     * <code>int32 globalRecvMsgOpt = 7;</code>
     * @return The globalRecvMsgOpt.
     */
    @java.lang.Override
    public int getGlobalRecvMsgOpt() {
      return instance.getGlobalRecvMsgOpt();
    }
    /**
     * <code>int32 globalRecvMsgOpt = 7;</code>
     * @param value The globalRecvMsgOpt to set.
     * @return This builder for chaining.
     */
    public Builder setGlobalRecvMsgOpt(int value) {
      copyOnWrite();
      instance.setGlobalRecvMsgOpt(value);
      return this;
    }
    /**
     * <code>int32 globalRecvMsgOpt = 7;</code>
     * @return This builder for chaining.
     */
    public Builder clearGlobalRecvMsgOpt() {
      copyOnWrite();
      instance.clearGlobalRecvMsgOpt();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:openim.sdkws.UserInfo)
  }
  @java.lang.Override
  @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
  protected final java.lang.Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      java.lang.Object arg0, java.lang.Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new io.openim.android.sdk.protos.sdkws.UserInfo();
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case BUILD_MESSAGE_INFO: {
          java.lang.Object[] objects = new java.lang.Object[] {
            "userID_",
            "nickname_",
            "faceURL_",
            "ex_",
            "createTime_",
            "appMangerLevel_",
            "globalRecvMsgOpt_",
          };
          java.lang.String info =
              "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0000\u0000\u0001\u0208\u0002\u0208" +
              "\u0003\u0208\u0004\u0208\u0005\u0002\u0006\u0004\u0007\u0004";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
      }
      // fall through
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        com.google.protobuf.Parser<io.openim.android.sdk.protos.sdkws.UserInfo> parser = PARSER;
        if (parser == null) {
          synchronized (io.openim.android.sdk.protos.sdkws.UserInfo.class) {
            parser = PARSER;
            if (parser == null) {
              parser =
                  new DefaultInstanceBasedParser<io.openim.android.sdk.protos.sdkws.UserInfo>(
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


  // @@protoc_insertion_point(class_scope:openim.sdkws.UserInfo)
  private static final io.openim.android.sdk.protos.sdkws.UserInfo DEFAULT_INSTANCE;
  static {
    UserInfo defaultInstance = new UserInfo();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
      UserInfo.class, defaultInstance);
  }

  public static io.openim.android.sdk.protos.sdkws.UserInfo getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<UserInfo> PARSER;

  public static com.google.protobuf.Parser<UserInfo> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}

