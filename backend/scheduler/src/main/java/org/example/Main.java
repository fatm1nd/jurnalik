package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import selector.SelectorGrpc;
import selector.SelectorOuterClass;

import java.time.Instant;
import java.time.ZoneId;

import static java.lang.Boolean.TRUE;
import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int cnt = 0;
        while (TRUE) {
            if (Instant.now().atZone(ZoneId.systemDefault()).getHour() == 0 && Instant.now().atZone(ZoneId.systemDefault()).getMinute() < 5 && cnt == 0) {
                //for VK SELECTOR
                ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 30002)
                        .usePlaintext()
                        .build();

                SelectorGrpc.SelectorBlockingStub stub
                        = SelectorGrpc.newBlockingStub(channel);

                SelectorOuterClass.Empty helloResponse = stub.selectAll(SelectorOuterClass.Empty.newBuilder()
                        .build());

                //for TG SELECTOR
                channel = ManagedChannelBuilder.forAddress("localhost", 30003)
                        .usePlaintext()
                        .build();

                stub = SelectorGrpc.newBlockingStub(channel);

                SelectorOuterClass.Empty helloResponse = stub.selectAll(SelectorOuterClass.Empty.newBuilder()
                        .build());

                channel.shutdown();
                cnt++;
            }
            else if(Instant.now().atZone(ZoneId.systemDefault()).getHour() == 0 && Instant.now().atZone(ZoneId.systemDefault()).getMinute() < 5)
                sleep(100000);
            else {
                cnt = 0;
                sleep(100000);
            }
        }

    }
}