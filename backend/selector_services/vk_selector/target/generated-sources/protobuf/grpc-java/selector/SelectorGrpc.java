package selector;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.19.0)",
    comments = "Source: selector.proto")
public final class SelectorGrpc {

  private SelectorGrpc() {}

  public static final String SERVICE_NAME = "selector.Selector";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<selector.SelectorOuterClass.User,
      selector.SelectorOuterClass.Empty> getSelectOneMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SelectOne",
      requestType = selector.SelectorOuterClass.User.class,
      responseType = selector.SelectorOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<selector.SelectorOuterClass.User,
      selector.SelectorOuterClass.Empty> getSelectOneMethod() {
    io.grpc.MethodDescriptor<selector.SelectorOuterClass.User, selector.SelectorOuterClass.Empty> getSelectOneMethod;
    if ((getSelectOneMethod = SelectorGrpc.getSelectOneMethod) == null) {
      synchronized (SelectorGrpc.class) {
        if ((getSelectOneMethod = SelectorGrpc.getSelectOneMethod) == null) {
          SelectorGrpc.getSelectOneMethod = getSelectOneMethod = 
              io.grpc.MethodDescriptor.<selector.SelectorOuterClass.User, selector.SelectorOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "selector.Selector", "SelectOne"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  selector.SelectorOuterClass.User.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  selector.SelectorOuterClass.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new SelectorMethodDescriptorSupplier("SelectOne"))
                  .build();
          }
        }
     }
     return getSelectOneMethod;
  }

  private static volatile io.grpc.MethodDescriptor<selector.SelectorOuterClass.Empty,
      selector.SelectorOuterClass.Empty> getSelectAllMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SelectAll",
      requestType = selector.SelectorOuterClass.Empty.class,
      responseType = selector.SelectorOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<selector.SelectorOuterClass.Empty,
      selector.SelectorOuterClass.Empty> getSelectAllMethod() {
    io.grpc.MethodDescriptor<selector.SelectorOuterClass.Empty, selector.SelectorOuterClass.Empty> getSelectAllMethod;
    if ((getSelectAllMethod = SelectorGrpc.getSelectAllMethod) == null) {
      synchronized (SelectorGrpc.class) {
        if ((getSelectAllMethod = SelectorGrpc.getSelectAllMethod) == null) {
          SelectorGrpc.getSelectAllMethod = getSelectAllMethod = 
              io.grpc.MethodDescriptor.<selector.SelectorOuterClass.Empty, selector.SelectorOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "selector.Selector", "SelectAll"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  selector.SelectorOuterClass.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  selector.SelectorOuterClass.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new SelectorMethodDescriptorSupplier("SelectAll"))
                  .build();
          }
        }
     }
     return getSelectAllMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SelectorStub newStub(io.grpc.Channel channel) {
    return new SelectorStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SelectorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SelectorBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SelectorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SelectorFutureStub(channel);
  }

  /**
   */
  public static abstract class SelectorImplBase implements io.grpc.BindableService {

    /**
     */
    public void selectOne(selector.SelectorOuterClass.User request,
        io.grpc.stub.StreamObserver<selector.SelectorOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getSelectOneMethod(), responseObserver);
    }

    /**
     */
    public void selectAll(selector.SelectorOuterClass.Empty request,
        io.grpc.stub.StreamObserver<selector.SelectorOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getSelectAllMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSelectOneMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                selector.SelectorOuterClass.User,
                selector.SelectorOuterClass.Empty>(
                  this, METHODID_SELECT_ONE)))
          .addMethod(
            getSelectAllMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                selector.SelectorOuterClass.Empty,
                selector.SelectorOuterClass.Empty>(
                  this, METHODID_SELECT_ALL)))
          .build();
    }
  }

  /**
   */
  public static final class SelectorStub extends io.grpc.stub.AbstractStub<SelectorStub> {
    private SelectorStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SelectorStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SelectorStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SelectorStub(channel, callOptions);
    }

    /**
     */
    public void selectOne(selector.SelectorOuterClass.User request,
        io.grpc.stub.StreamObserver<selector.SelectorOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSelectOneMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void selectAll(selector.SelectorOuterClass.Empty request,
        io.grpc.stub.StreamObserver<selector.SelectorOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSelectAllMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SelectorBlockingStub extends io.grpc.stub.AbstractStub<SelectorBlockingStub> {
    private SelectorBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SelectorBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SelectorBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SelectorBlockingStub(channel, callOptions);
    }

    /**
     */
    public selector.SelectorOuterClass.Empty selectOne(selector.SelectorOuterClass.User request) {
      return blockingUnaryCall(
          getChannel(), getSelectOneMethod(), getCallOptions(), request);
    }

    /**
     */
    public selector.SelectorOuterClass.Empty selectAll(selector.SelectorOuterClass.Empty request) {
      return blockingUnaryCall(
          getChannel(), getSelectAllMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SelectorFutureStub extends io.grpc.stub.AbstractStub<SelectorFutureStub> {
    private SelectorFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SelectorFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SelectorFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SelectorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<selector.SelectorOuterClass.Empty> selectOne(
        selector.SelectorOuterClass.User request) {
      return futureUnaryCall(
          getChannel().newCall(getSelectOneMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<selector.SelectorOuterClass.Empty> selectAll(
        selector.SelectorOuterClass.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getSelectAllMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SELECT_ONE = 0;
  private static final int METHODID_SELECT_ALL = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SelectorImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SelectorImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SELECT_ONE:
          serviceImpl.selectOne((selector.SelectorOuterClass.User) request,
              (io.grpc.stub.StreamObserver<selector.SelectorOuterClass.Empty>) responseObserver);
          break;
        case METHODID_SELECT_ALL:
          serviceImpl.selectAll((selector.SelectorOuterClass.Empty) request,
              (io.grpc.stub.StreamObserver<selector.SelectorOuterClass.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SelectorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SelectorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return selector.SelectorOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Selector");
    }
  }

  private static final class SelectorFileDescriptorSupplier
      extends SelectorBaseDescriptorSupplier {
    SelectorFileDescriptorSupplier() {}
  }

  private static final class SelectorMethodDescriptorSupplier
      extends SelectorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SelectorMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SelectorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SelectorFileDescriptorSupplier())
              .addMethod(getSelectOneMethod())
              .addMethod(getSelectAllMethod())
              .build();
        }
      }
    }
    return result;
  }
}
