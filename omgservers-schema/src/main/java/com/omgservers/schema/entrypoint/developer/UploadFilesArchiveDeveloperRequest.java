package com.omgservers.schema.entrypoint.developer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFilesArchiveDeveloperRequest {

    @NotBlank
    @Size(max = 64)
    String tenant;

    @NotNull
    Long versionId;

    @NotEmpty
    @ToString.Exclude
    List<FileUpload> files;
}
