package com.omgservers.schema.model.file;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecodedFileModel {

    @NotEmpty
    String fileName;

    @NotEmpty
    @ToString.Exclude
    String content;
}
