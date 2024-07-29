package com.omgservers.schema.entrypoint.registry.handleEvents;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TargetRecordDto {

    String mediaType;
    Long size;
    String digest;
    Long length;

    @NotBlank
    String repository;

    String url;

    @NotBlank
    String tag;
}
