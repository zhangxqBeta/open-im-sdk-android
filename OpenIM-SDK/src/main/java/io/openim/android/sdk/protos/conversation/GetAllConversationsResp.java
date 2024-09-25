// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/conversation.proto

package io.openim.android.sdk.protos.conversation;

/**
 * Protobuf type {@code openim.conversation.GetAllConversationsResp}
 */
public  final class GetAllConversationsResp extends
    com.google.protobuf.GeneratedMessageLite<
        GetAllConversationsResp, GetAllConversationsResp.Builder> implements
    // @@protoc_insertion_point(message_implements:openim.conversation.GetAllConversationsResp)
    GetAllConversationsRespOrBuilder {
  private GetAllConversationsResp() {
    conversations_ = emptyProtobufList();
  }
  public static final int CONVERSATIONS_FIELD_NUMBER = 2;
  private com.google.protobuf.Internal.ProtobufList<io.openim.android.sdk.protos.conversation.Conversation> conversations_;
  /**
   * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
   */
  @java.lang.Override
  public java.util.List<io.openim.android.sdk.protos.conversation.Conversation> getConversationsList() {
    return conversations_;
  }
  /**
   * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
   */
  public java.util.List<? extends io.openim.android.sdk.protos.conversation.ConversationOrBuilder> 
      getConversationsOrBuilderList() {
    return conversations_;
  }
  /**
   * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
   */
  @java.lang.Override
  public int getConversationsCount() {
    return conversations_.size();
  }
  /**
   * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
   */
  @java.lang.Override
  public io.openim.android.sdk.protos.conversation.Conversation getConversations(int index) {
    return conversations_.get(index);
  }
  /**
   * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
   */
  public io.openim.android.sdk.protos.conversation.ConversationOrBuilder getConversationsOrBuilder(
      int index) {
    return conversations_.get(index);
  }
  private void ensureConversationsIsMutable() {
    com.google.protobuf.Internal.ProtobufList<io.openim.android.sdk.protos.conversation.Conversation> tmp = conversations_;
    if (!tmp.isModifiable()) {
      conversations_ =
          com.google.protobuf.GeneratedMessageLite.mutableCopy(tmp);
     }
  }

  /**
   * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
   */
  private void setConversations(
      int index, io.openim.android.sdk.protos.conversation.Conversation value) {
    value.getClass();
  ensureConversationsIsMutable();
    conversations_.set(index, value);
  }
  /**
   * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
   */
  private void addConversations(io.openim.android.sdk.protos.conversation.Conversation value) {
    value.getClass();
  ensureConversationsIsMutable();
    conversations_.add(value);
  }
  /**
   * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
   */
  private void addConversations(
      int index, io.openim.android.sdk.protos.conversation.Conversation value) {
    value.getClass();
  ensureConversationsIsMutable();
    conversations_.add(index, value);
  }
  /**
   * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
   */
  private void addAllConversations(
      java.lang.Iterable<? extends io.openim.android.sdk.protos.conversation.Conversation> values) {
    ensureConversationsIsMutable();
    com.google.protobuf.AbstractMessageLite.addAll(
        values, conversations_);
  }
  /**
   * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
   */
  private void clearConversations() {
    conversations_ = emptyProtobufList();
  }
  /**
   * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
   */
  private void removeConversations(int index) {
    ensureConversationsIsMutable();
    conversations_.remove(index);
  }

  public static io.openim.android.sdk.protos.conversation.GetAllConversationsResp parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.conversation.GetAllConversationsResp parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.conversation.GetAllConversationsResp parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.conversation.GetAllConversationsResp parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.conversation.GetAllConversationsResp parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.conversation.GetAllConversationsResp parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.conversation.GetAllConversationsResp parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.conversation.GetAllConversationsResp parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.conversation.GetAllConversationsResp parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.conversation.GetAllConversationsResp parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.conversation.GetAllConversationsResp parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.conversation.GetAllConversationsResp parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }
  public static Builder newBuilder(io.openim.android.sdk.protos.conversation.GetAllConversationsResp prototype) {
    return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   * Protobuf type {@code openim.conversation.GetAllConversationsResp}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        io.openim.android.sdk.protos.conversation.GetAllConversationsResp, Builder> implements
      // @@protoc_insertion_point(builder_implements:openim.conversation.GetAllConversationsResp)
      io.openim.android.sdk.protos.conversation.GetAllConversationsRespOrBuilder {
    // Construct using io.openim.android.sdk.protos.conversation.GetAllConversationsResp.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
     */
    @java.lang.Override
    public java.util.List<io.openim.android.sdk.protos.conversation.Conversation> getConversationsList() {
      return java.util.Collections.unmodifiableList(
          instance.getConversationsList());
    }
    /**
     * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
     */
    @java.lang.Override
    public int getConversationsCount() {
      return instance.getConversationsCount();
    }/**
     * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
     */
    @java.lang.Override
    public io.openim.android.sdk.protos.conversation.Conversation getConversations(int index) {
      return instance.getConversations(index);
    }
    /**
     * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
     */
    public Builder setConversations(
        int index, io.openim.android.sdk.protos.conversation.Conversation value) {
      copyOnWrite();
      instance.setConversations(index, value);
      return this;
    }
    /**
     * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
     */
    public Builder setConversations(
        int index, io.openim.android.sdk.protos.conversation.Conversation.Builder builderForValue) {
      copyOnWrite();
      instance.setConversations(index,
          builderForValue.build());
      return this;
    }
    /**
     * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
     */
    public Builder addConversations(io.openim.android.sdk.protos.conversation.Conversation value) {
      copyOnWrite();
      instance.addConversations(value);
      return this;
    }
    /**
     * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
     */
    public Builder addConversations(
        int index, io.openim.android.sdk.protos.conversation.Conversation value) {
      copyOnWrite();
      instance.addConversations(index, value);
      return this;
    }
    /**
     * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
     */
    public Builder addConversations(
        io.openim.android.sdk.protos.conversation.Conversation.Builder builderForValue) {
      copyOnWrite();
      instance.addConversations(builderForValue.build());
      return this;
    }
    /**
     * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
     */
    public Builder addConversations(
        int index, io.openim.android.sdk.protos.conversation.Conversation.Builder builderForValue) {
      copyOnWrite();
      instance.addConversations(index,
          builderForValue.build());
      return this;
    }
    /**
     * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
     */
    public Builder addAllConversations(
        java.lang.Iterable<? extends io.openim.android.sdk.protos.conversation.Conversation> values) {
      copyOnWrite();
      instance.addAllConversations(values);
      return this;
    }
    /**
     * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
     */
    public Builder clearConversations() {
      copyOnWrite();
      instance.clearConversations();
      return this;
    }
    /**
     * <code>repeated .openim.conversation.Conversation conversations = 2;</code>
     */
    public Builder removeConversations(int index) {
      copyOnWrite();
      instance.removeConversations(index);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:openim.conversation.GetAllConversationsResp)
  }
  @java.lang.Override
  @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
  protected final java.lang.Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      java.lang.Object arg0, java.lang.Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new io.openim.android.sdk.protos.conversation.GetAllConversationsResp();
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case BUILD_MESSAGE_INFO: {
          java.lang.Object[] objects = new java.lang.Object[] {
            "conversations_",
            io.openim.android.sdk.protos.conversation.Conversation.class,
          };
          java.lang.String info =
              "\u0000\u0001\u0000\u0000\u0002\u0002\u0001\u0000\u0001\u0000\u0002\u001b";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
      }
      // fall through
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        com.google.protobuf.Parser<io.openim.android.sdk.protos.conversation.GetAllConversationsResp> parser = PARSER;
        if (parser == null) {
          synchronized (io.openim.android.sdk.protos.conversation.GetAllConversationsResp.class) {
            parser = PARSER;
            if (parser == null) {
              parser =
                  new DefaultInstanceBasedParser<io.openim.android.sdk.protos.conversation.GetAllConversationsResp>(
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


  // @@protoc_insertion_point(class_scope:openim.conversation.GetAllConversationsResp)
  private static final io.openim.android.sdk.protos.conversation.GetAllConversationsResp DEFAULT_INSTANCE;
  static {
    GetAllConversationsResp defaultInstance = new GetAllConversationsResp();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
      GetAllConversationsResp.class, defaultInstance);
  }

  public static io.openim.android.sdk.protos.conversation.GetAllConversationsResp getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<GetAllConversationsResp> PARSER;

  public static com.google.protobuf.Parser<GetAllConversationsResp> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}

