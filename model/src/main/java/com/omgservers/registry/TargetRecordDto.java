package com.omgservers.registry;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
