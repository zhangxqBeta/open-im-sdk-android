// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/sdkws.proto

package io.openim.android.sdk.protos.sdkws;

/**
 * Protobuf type {@code openim.sdkws.PullMessageBySeqsReq}
 */
public  final class PullMessageBySeqsReq extends
    com.google.protobuf.GeneratedMessageLite<
        PullMessageBySeqsReq, PullMessageBySeqsReq.Builder> implements
    // @@protoc_insertion_point(message_implements:openim.sdkws.PullMessageBySeqsReq)
    PullMessageBySeqsReqOrBuilder {
  private PullMessageBySeqsReq() {
    userID_ = "";
    seqRanges_ = emptyProtobufList();
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

  public static final int SEQRANGES_FIELD_NUMBER = 2;
  private com.google.protobuf.Internal.ProtobufList<io.openim.android.sdk.protos.sdkws.SeqRange> seqRanges_;
  /**
   * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
   */
  @java.lang.Override
  public java.util.List<io.openim.android.sdk.protos.sdkws.SeqRange> getSeqRangesList() {
    return seqRanges_;
  }
  /**
   * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
   */
  public java.util.List<? extends io.openim.android.sdk.protos.sdkws.SeqRangeOrBuilder> 
      getSeqRangesOrBuilderList() {
    return seqRanges_;
  }
  /**
   * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
   */
  @java.lang.Override
  public int getSeqRangesCount() {
    return seqRanges_.size();
  }
  /**
   * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
   */
  @java.lang.Override
  public io.openim.android.sdk.protos.sdkws.SeqRange getSeqRanges(int index) {
    return seqRanges_.get(index);
  }
  /**
   * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
   */
  public io.openim.android.sdk.protos.sdkws.SeqRangeOrBuilder getSeqRangesOrBuilder(
      int index) {
    return seqRanges_.get(index);
  }
  private void ensureSeqRangesIsMutable() {
    com.google.protobuf.Internal.ProtobufList<io.openim.android.sdk.protos.sdkws.SeqRange> tmp = seqRanges_;
    if (!tmp.isModifiable()) {
      seqRanges_ =
          com.google.protobuf.GeneratedMessageLite.mutableCopy(tmp);
     }
  }

  /**
   * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
   */
  private void setSeqRanges(
      int index, io.openim.android.sdk.protos.sdkws.SeqRange value) {
    value.getClass();
  ensureSeqRangesIsMutable();
    seqRanges_.set(index, value);
  }
  /**
   * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
   */
  private void addSeqRanges(io.openim.android.sdk.protos.sdkws.SeqRange value) {
    value.getClass();
  ensureSeqRangesIsMutable();
    seqRanges_.add(value);
  }
  /**
   * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
   */
  private void addSeqRanges(
      int index, io.openim.android.sdk.protos.sdkws.SeqRange value) {
    value.getClass();
  ensureSeqRangesIsMutable();
    seqRanges_.add(index, value);
  }
  /**
   * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
   */
  private void addAllSeqRanges(
      java.lang.Iterable<? extends io.openim.android.sdk.protos.sdkws.SeqRange> values) {
    ensureSeqRangesIsMutable();
    com.google.protobuf.AbstractMessageLite.addAll(
        values, seqRanges_);
  }
  /**
   * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
   */
  private void clearSeqRanges() {
    seqRanges_ = emptyProtobufList();
  }
  /**
   * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
   */
  private void removeSeqRanges(int index) {
    ensureSeqRangesIsMutable();
    seqRanges_.remove(index);
  }

  public static final int ORDER_FIELD_NUMBER = 3;
  private int order_;
  /**
   * <code>.openim.sdkws.PullOrder order = 3;</code>
   * @return The enum numeric value on the wire for order.
   */
  @java.lang.Override
  public int getOrderValue() {
    return order_;
  }
  /**
   * <code>.openim.sdkws.PullOrder order = 3;</code>
   * @return The order.
   */
  @java.lang.Override
  public io.openim.android.sdk.protos.sdkws.PullOrder getOrder() {
    io.openim.android.sdk.protos.sdkws.PullOrder result = io.openim.android.sdk.protos.sdkws.PullOrder.forNumber(order_);
    return result == null ? io.openim.android.sdk.protos.sdkws.PullOrder.UNRECOGNIZED : result;
  }
  /**
   * <code>.openim.sdkws.PullOrder order = 3;</code>
   * @param value The enum numeric value on the wire for order to set.
   */
  private void setOrderValue(int value) {
      order_ = value;
  }
  /**
   * <code>.openim.sdkws.PullOrder order = 3;</code>
   * @param value The order to set.
   */
  private void setOrder(io.openim.android.sdk.protos.sdkws.PullOrder value) {
    order_ = value.getNumber();
    
  }
  /**
   * <code>.openim.sdkws.PullOrder order = 3;</code>
   */
  private void clearOrder() {
    
    order_ = 0;
  }

  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }
  public static Builder newBuilder(io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq prototype) {
    return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   * Protobuf type {@code openim.sdkws.PullMessageBySeqsReq}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq, Builder> implements
      // @@protoc_insertion_point(builder_implements:openim.sdkws.PullMessageBySeqsReq)
      io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReqOrBuilder {
    // Construct using io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq.newBuilder()
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
     * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
     */
    @java.lang.Override
    public java.util.List<io.openim.android.sdk.protos.sdkws.SeqRange> getSeqRangesList() {
      return java.util.Collections.unmodifiableList(
          instance.getSeqRangesList());
    }
    /**
     * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
     */
    @java.lang.Override
    public int getSeqRangesCount() {
      return instance.getSeqRangesCount();
    }/**
     * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
     */
    @java.lang.Override
    public io.openim.android.sdk.protos.sdkws.SeqRange getSeqRanges(int index) {
      return instance.getSeqRanges(index);
    }
    /**
     * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
     */
    public Builder setSeqRanges(
        int index, io.openim.android.sdk.protos.sdkws.SeqRange value) {
      copyOnWrite();
      instance.setSeqRanges(index, value);
      return this;
    }
    /**
     * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
     */
    public Builder setSeqRanges(
        int index, io.openim.android.sdk.protos.sdkws.SeqRange.Builder builderForValue) {
      copyOnWrite();
      instance.setSeqRanges(index,
          builderForValue.build());
      return this;
    }
    /**
     * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
     */
    public Builder addSeqRanges(io.openim.android.sdk.protos.sdkws.SeqRange value) {
      copyOnWrite();
      instance.addSeqRanges(value);
      return this;
    }
    /**
     * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
     */
    public Builder addSeqRanges(
        int index, io.openim.android.sdk.protos.sdkws.SeqRange value) {
      copyOnWrite();
      instance.addSeqRanges(index, value);
      return this;
    }
    /**
     * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
     */
    public Builder addSeqRanges(
        io.openim.android.sdk.protos.sdkws.SeqRange.Builder builderForValue) {
      copyOnWrite();
      instance.addSeqRanges(builderForValue.build());
      return this;
    }
    /**
     * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
     */
    public Builder addSeqRanges(
        int index, io.openim.android.sdk.protos.sdkws.SeqRange.Builder builderForValue) {
      copyOnWrite();
      instance.addSeqRanges(index,
          builderForValue.build());
      return this;
    }
    /**
     * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
     */
    public Builder addAllSeqRanges(
        java.lang.Iterable<? extends io.openim.android.sdk.protos.sdkws.SeqRange> values) {
      copyOnWrite();
      instance.addAllSeqRanges(values);
      return this;
    }
    /**
     * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
     */
    public Builder clearSeqRanges() {
      copyOnWrite();
      instance.clearSeqRanges();
      return this;
    }
    /**
     * <code>repeated .openim.sdkws.SeqRange seqRanges = 2;</code>
     */
    public Builder removeSeqRanges(int index) {
      copyOnWrite();
      instance.removeSeqRanges(index);
      return this;
    }

    /**
     * <code>.openim.sdkws.PullOrder order = 3;</code>
     * @return The enum numeric value on the wire for order.
     */
    @java.lang.Override
    public int getOrderValue() {
      return instance.getOrderValue();
    }
    /**
     * <code>.openim.sdkws.PullOrder order = 3;</code>
     * @param value The order to set.
     * @return This builder for chaining.
     */
    public Builder setOrderValue(int value) {
      copyOnWrite();
      instance.setOrderValue(value);
      return this;
    }
    /**
     * <code>.openim.sdkws.PullOrder order = 3;</code>
     * @return The order.
     */
    @java.lang.Override
    public io.openim.android.sdk.protos.sdkws.PullOrder getOrder() {
      return instance.getOrder();
    }
    /**
     * <code>.openim.sdkws.PullOrder order = 3;</code>
     * @param value The enum numeric value on the wire for order to set.
     * @return This builder for chaining.
     */
    public Builder setOrder(io.openim.android.sdk.protos.sdkws.PullOrder value) {
      copyOnWrite();
      instance.setOrder(value);
      return this;
    }
    /**
     * <code>.openim.sdkws.PullOrder order = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearOrder() {
      copyOnWrite();
      instance.clearOrder();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:openim.sdkws.PullMessageBySeqsReq)
  }
  @java.lang.Override
  @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
  protected final java.lang.Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      java.lang.Object arg0, java.lang.Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq();
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case BUILD_MESSAGE_INFO: {
          java.lang.Object[] objects = new java.lang.Object[] {
            "userID_",
            "seqRanges_",
            io.openim.android.sdk.protos.sdkws.SeqRange.class,
            "order_",
          };
          java.lang.String info =
              "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0001\u0000\u0001\u0208\u0002\u001b" +
              "\u0003\f";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
      }
      // fall through
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        com.google.protobuf.Parser<io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq> parser = PARSER;
        if (parser == null) {
          synchronized (io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq.class) {
            parser = PARSER;
            if (parser == null) {
              parser =
                  new DefaultInstanceBasedParser<io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq>(
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


  // @@protoc_insertion_point(class_scope:openim.sdkws.PullMessageBySeqsReq)
  private static final io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq DEFAULT_INSTANCE;
  static {
    PullMessageBySeqsReq defaultInstance = new PullMessageBySeqsReq();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
      PullMessageBySeqsReq.class, defaultInstance);
  }

  public static io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<PullMessageBySeqsReq> PARSER;

  public static com.google.protobuf.Parser<PullMessageBySeqsReq> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
