package com.example.demo.exception;

public enum ClientErrorCode {

    E_001("Server unhandled Error"),
    E_002("Server handled Error"),
    O_001("Cannot create order from non resolved enquiry");

    String description;

    ClientErrorCode(String s) {
        this.description = s;
    }
}
