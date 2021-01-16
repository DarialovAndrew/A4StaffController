package com.example.demo.api;

import com.example.demo.service.impl.StaffService;
import com.example.demo.service.model.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@GrpcService
public class StaffControllerGrpc extends staffServiceGrpc.staffServiceImplBase{


    private final StaffService staffService;
    @Autowired
    StaffControllerGrpc(StaffService staffService){
        this.staffService=staffService;
    }

    @Override
    public void addCleaner(addCleanerRequest request, StreamObserver<addCleanerResponse> responseObserver) {
        this.staffService.addStaff(new Cleaner(request.getCleaner().getName(),request.getCleaner().getSalary()));
        addCleanerResponse response = addCleanerResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteCleaner(deleteCleanerRequest request, StreamObserver<deleteCleanerResponse> responseObserver) {
        this.staffService.deleteCleaner(UUID.fromString(request.getId()));
        deleteCleanerResponse response = deleteCleanerResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllCleaners(getAllCleanersRequest request, StreamObserver<getAllCleanersResponse> responseObserver) {
        List<Cleaner> cleaners = staffService.getCleaners().findAll();

        List<getCleanerResponse> cleanerResponses = new ArrayList<>();
        for (Cleaner cleaner : cleaners) {
            getCleanerResponse CleanerResponse= getCleanerResponse.newBuilder()
                    .setId(cleaner.getId().toString())
                    .setName(cleaner.getName())
                    .setSalary(cleaner.getSalary())
                    .build();
            cleanerResponses.add(CleanerResponse);
        }
        getAllCleanersResponse response = getAllCleanersResponse.newBuilder().addAllCleaner(cleanerResponses).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
