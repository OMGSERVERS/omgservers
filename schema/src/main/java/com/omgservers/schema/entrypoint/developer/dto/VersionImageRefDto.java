package com.omgservers.schema.entrypoint.developer.dto;

import com.omgservers.schema.model.versionImageRef.VersionImageRefQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionImageRefDto {

    Long id;

    Instant created;

    VersionImageRefQualifierEnum qualifier;

    String imageId;
}
