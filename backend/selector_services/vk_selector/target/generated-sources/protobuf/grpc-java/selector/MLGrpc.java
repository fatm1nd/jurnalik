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
    comments = "Source: ml.proto")
public final class MLGrpc {

  private MLGrpc() {}

  public static final String SERVICE_NAME = "selector.ML";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<selector.Ml.SelectorPing,
      selector.SelectorOuterClass.Empty> getPingOneMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PingOne",
      requestType = selector.Ml.SelectorPing.class,
      responseType = selector.SelectorOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<selector.Ml.SelectorPing,
      selector.SelectorOuterClass.Empty> getPingOneMethod() {
    io.grpc.MethodDescriptor<selector.Ml.SelectorPing, selector.SelectorOuterClass.Empty> getPingOneMethod;
    if ((getPingOneMethod = MLGrpc.getPingOneMethod) == null) {
      synchronized (MLGrpc.class) {
        if ((getPingOneMethod = MLGrpc.getPingOneMethod) == null) {
          MLGrpc.getPingOneMethod = getPingOneMethod = 
              io.grpc.MethodDescriptor.<selector.Ml.SelectorPing, selector.SelectorOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "selector.ML", "PingOne"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  selector.Ml.SelectorPing.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  selector.SelectorOuterClass.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new MLMethodDescriptorSupplier("PingOne"))
                  .build();
          }
        }
     }
     return getPingOneMethod;
  }

  private static volatile io.grpc.MethodDescriptor<selector.SelectorOuterClass.Empty,
      selector.SelectorOuterClass.Empty> getPingAllMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PingAll",
      requestType = selector.SelectorOuterClass.Empty.class,
      responseType = selector.SelectorOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<selector.SelectorOuterClass.Empty,
      selector.SelectorOuterClass.Empty> getPingAllMethod() {
    io.grpc.MethodDescriptor<selector.SelectorOuterClass.Empty, selector.SelectorOuterClass.Empty> getPingAllMethod;
    if ((getPingAllMethod = MLGrpc.getPingAllMethod) == null) {
      synchronized (MLGrpc.class) {
        if ((getPingAllMethod = MLGrpc.getPingAllMethod) == null) {
          MLGrpc.getPingAllMethod = getPingAllMethod = 
              io.grpc.MethodDescriptor.<selector.SelectorOuterClass.Empty, selector.SelectorOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "selector.ML", "PingAll"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  selector.SelectorOuterClass.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  selector.SelectorOuterClass.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new MLMethodDescriptorSupplier("PingAll"))
                  .build();
          }
        }
     }
     return getPingAllMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MLStub newStub(io.grpc.Channel channel) {
    return new MLStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MLBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MLBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MLFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MLFutureStub(channel);
  }

  /**
   */
  public static abstract class MLImplBase implements io.grpc.BindableService {

    /**
     */
    public void pingOne(selector.Ml.SelectorPing request,
        io.grpc.stub.StreamObserver<selector.SelectorOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getPingOneMethod(), responseObserver);
    }

    /**
     */
    public void pingAll(selector.SelectorOuterClass.Empty request,
        io.grpc.stub.StreamObserver<selector.SelectorOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getPingAllMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPingOneMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                selector.Ml.SelectorPing,
                selector.SelectorOuterClass.Empty>(
                  this, METHODID_PING_ONE)))
          .addMethod(
            getPingAllMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                selector.SelectorOuterClass.Empty,
                selector.SelectorOuterClass.Empty>(
                  this, METHODID_PING_ALL)))
          .build();
    }
  }

  /**
   */
  public static final class MLStub extends io.grpc.stub.AbstractStub<MLStub> {
    private MLStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MLStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MLStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MLStub(channel, callOptions);
    }

    /**
     */
    public void pingOne(selector.Ml.SelectorPing request,
        io.grpc.stub.StreamObserver<selector.SelectorOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPingOneMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void pingAll(selector.SelectorOuterClass.Empty request,
        io.grpc.stub.StreamObserver<selector.SelectorOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPingAllMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MLBlockingStub extends io.grpc.stub.AbstractStub<MLBlockingStub> {
    private MLBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MLBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MLBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MLBlockingStub(channel, callOptions);
    }

    /**
     */
    public selector.SelectorOuterClass.Empty pingOne(selector.Ml.SelectorPing request) {
      return blockingUnaryCall(
          getChannel(), getPingOneMethod(), getCallOptions(), request);
    }

    /**
     */
    public selector.SelectorOuterClass.Empty pingAll(selector.SelectorOuterClass.Empty request) {
      return blockingUnaryCall(
          getChannel(), getPingAllMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MLFutureStub extends io.grpc.stub.AbstractStub<MLFutureStub> {
    private MLFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MLFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MLFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MLFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<selector.SelectorOuterClass.Empty> pingOne(
        selector.Ml.SelectorPing request) {
      return futureUnaryCall(
          getChannel().newCall(getPingOneMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<selector.SelectorOuterClass.Empty> pingAll(
        selector.SelectorOuterClass.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getPingAllMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PING_ONE = 0;
  private static final int METHODID_PING_ALL = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MLImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MLImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PING_ONE:
          serviceImpl.pingOne((selector.Ml.SelectorPing) request,
              (io.grpc.stub.StreamObserver<selector.SelectorOuterClass.Empty>) responseObserver);
          break;
        case METHODID_PING_ALL:
          serviceImpl.pingAll((selector.SelectorOuterClass.Empty) request,
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

  private static abstract class MLBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MLBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return selector.Ml.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ML");
    }
  }

  private static final class MLFileDescriptorSupplier
      extends MLBaseDescriptorSupplier {
    MLFileDescriptorSupplier() {}
  }

  private static final class MLMethodDescriptorSupplier
      extends MLBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MLMethodDescriptorSupplier(String methodName) {
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
      synchronized (MLGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MLFileDescriptorSupplier())
              .addMethod(getPingOneMethod())
              .addMethod(getPingAllMethod())
              .build();
        }
      }
    }
    return result;
  }
}
