package com.test.tutipet.exception;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Builder
public class Error implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String errorCode;

    private String message;

    private Integer status;

    private String url = "Not Available";

    private String reqMethod = "Not Available";

    private Instant timestamp;


}
