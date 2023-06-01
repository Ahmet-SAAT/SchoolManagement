package com.schoolmanagement.payload.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.schoolmanagement.payload.request.abstracts.BaseUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AdminRequest extends BaseUserRequest {
    private boolean built_in;
}
