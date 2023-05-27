package com.schoolmanagement.payload.response;

import com.schoolmanagement.payload.request.abstracts.BaseUserRequest;
import com.schoolmanagement.payload.response.abstracts.BaseUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AdminResponse extends BaseUserResponse {
}
