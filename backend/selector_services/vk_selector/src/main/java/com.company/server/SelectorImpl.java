package com.company.server;

import com.company.Selector;

import java.io.IOException;
import java.sql.SQLException;

import io.grpc.stub.StreamObserver;
import selector.SelectorGrpc;
import selector.SelectorOuterClass;

public class SelectorImpl extends SelectorGrpc.SelectorImplBase {
    @Override
    public void selectOne(SelectorOuterClass.User request, StreamObserver<selector.SelectorOuterClass.Empty> responseObserver) {
        int user_id = Integer.parseInt(request.getUser());
        System.out.println("select One");

        Selector select = new Selector();
        select.selectOne(user_id);

        selector.SelectorOuterClass.Empty response = selector.SelectorOuterClass.Empty.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void selectAll(selector.SelectorOuterClass.Empty request, StreamObserver<selector.SelectorOuterClass.Empty> responseObserver) {
        System.out.println("select All");

        Selector select = new Selector();
        select.selectAll();

        selector.SelectorOuterClass.Empty response = selector.SelectorOuterClass.Empty.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
