package com.alberto.virtualcave.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private int httpStatusCode;

    private String message;

    private List<Error> errors;

    private LocalDateTime timestamp;

}
