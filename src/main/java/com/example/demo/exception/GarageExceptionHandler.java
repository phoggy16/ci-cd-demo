package com.example.demo.exception;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice(basePackages = {"com.metalbook.garage.handler"})
public class GarageExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGenericException(Exception ex,
                                                            WebRequest request) {
        logger.error(ex.getMessage(), ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ExceptionUtils.getStackTrace(ex));
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setClientErrorCode(ClientErrorCode.E_001);
        return buildResponseEntity(apiError);
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(GarageException.class)
    protected ResponseEntity<Object> handleGarageException(GarageException ex,
                                                           WebRequest request) {
        logger.error(ex.getMessage(), ex);
        ApiError apiError;
        if (ex.getStatusCode() != null) {
            apiError = new ApiError(ex.getStatusCode());
        } else {
            apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ExceptionUtils.getStackTrace(ex));
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setClientErrorCode(ClientErrorCode.E_002);
        return buildResponseEntity(apiError);
    }
//
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ResponseEntity<UploadResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
//        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
//                .body(new UploadResponseMessage("Unable to upload. File is too large!"));
//    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
//            stringBuilder.append(error.getField() + " "+ error.getDefaultMessage() + "; ");
//        }
//
//        String message = new String(stringBuilder);
//
//        ApiError apiError=new ApiError(HttpStatus.BAD_REQUEST);
//        apiError.setMessage(message);
//        apiError.setDebugMessage(ExceptionUtils.getStackTrace(ex));
//        apiError.setTimestamp(LocalDateTime.now());
//        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
//    }
}
