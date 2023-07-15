package com.company;

import com.company.server.SelectorImpl;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(50051)
                .addService((BindableService) new SelectorImpl()).build();

        server.start();
        server.awaitTermination();
    }
}