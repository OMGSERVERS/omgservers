package com.omgservers.schema.entrypoint.developer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadVersionDeveloperRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotEmpty
    @ToString.Exclude
    List<FileUpload> files;
}
