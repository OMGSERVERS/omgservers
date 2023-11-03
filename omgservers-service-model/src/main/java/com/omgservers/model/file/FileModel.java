package com.omgservers.model.file;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileModel {

    @NotBlank
    String fileName;

    @NotEmpty
    byte[] content;
}
