package com.omgservers.service.service.jenkins.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetLuaJitRuntimeBuilderV1Request {

    @NotNull
    Integer buildNumber;
}
