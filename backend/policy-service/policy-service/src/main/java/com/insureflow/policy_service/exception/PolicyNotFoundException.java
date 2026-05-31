package com.insureflow.policy_service.exception;

public class PolicyNotFoundException extends RuntimeException {

    public PolicyNotFoundException(String message){
        super(message);
    }

}
