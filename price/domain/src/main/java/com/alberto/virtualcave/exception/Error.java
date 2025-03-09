package com.alberto.virtualcave.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    private String errorCode;
    private String errorDescription;

    public static Error validationError(){
        return Error.builder()
                .errorCode("001")
                .errorDescription("Request validation failed")
                .build();
    }

    public static Error missingOrInvalidDataInRequest(){
        return Error.builder()
                .errorCode("002")
                .errorDescription("Missing or invalid data in the request")
                .build();
    }
}
