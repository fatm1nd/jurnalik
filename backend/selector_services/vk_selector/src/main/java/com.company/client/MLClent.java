package com.company.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import selector.MLGrpc;
import selector.Ml;
import selector.SelectorOuterClass;

public class MLClent {
    public MLClent(){}
    public MLClent(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    private String ip = "localhost";
    private int port = 30000;
    public void pingOne(int user_id){
        System.out.println("pingOne MLServer in ip + port:" + ip + ":" + port);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(ip, port)
                .usePlaintext()
                .build();

        MLGrpc.MLBlockingStub stub
                = MLGrpc.newBlockingStub(channel);

        SelectorOuterClass.Empty helloResponse = stub.pingOne(Ml.SelectorPing.newBuilder()
                .setUser(Integer.toString(user_id))
                        .setSourceValue(0)
                .build());

        channel.shutdown();
    }

    public void pingAll(){
        System.out.println("pingAll MLServer in ip + port:" + ip + ":" + port);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(ip, port)
                .usePlaintext()
                .build();

        MLGrpc.MLBlockingStub stub
                = MLGrpc.newBlockingStub(channel);

        SelectorOuterClass.Empty helloResponse = stub.pingAll(SelectorOuterClass.Empty.newBuilder()
                .build());

        channel.shutdown();
    }
}
