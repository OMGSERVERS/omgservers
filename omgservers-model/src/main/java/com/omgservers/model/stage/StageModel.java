package com.omgservers.model.stage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StageModel {

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Long projectId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotBlank
    @Size(max = 1024)
    @ToString.Exclude
    String secret;

    @NotNull
    Long matchmakerId;

    @NotNull
    @ToString.Exclude
    StageConfigModel config;
}
