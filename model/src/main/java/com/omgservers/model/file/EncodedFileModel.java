package com.omgservers.model.file;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncodedFileModel {

    @NotEmpty
    String fileName;

    @NotEmpty
    @ToString.Exclude
    String base64content;
}
