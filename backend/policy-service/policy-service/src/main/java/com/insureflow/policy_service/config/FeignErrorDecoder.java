package com.insureflow.policy_service.config;

import com.insureflow.policy_service.dto.response.UserServiceErrorResponse;
import com.insureflow.policy_service.exception.UserServiceException;
import feign.Response;
import feign.codec.ErrorDecoder;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String s, Response response) {
        try (InputStream inputStream = response.body().asInputStream()) {

            UserServiceErrorResponse errorResponse =
                    objectMapper.readValue(inputStream, UserServiceErrorResponse.class);

            String message = errorResponse.getMessage();

            if (message == null || message.isBlank()) {
                message = "User service returned error";
            }

            return new UserServiceException(message);

        } catch (Exception ex) {

            if (response.status() == 404) {
                return new UserServiceException("User not found");
            }

            if (response.status() >= 500) {
                return new UserServiceException("User service is currently unavailable");
            }

            return new UserServiceException("Unable to validate user from user service");
        }
    }
}
