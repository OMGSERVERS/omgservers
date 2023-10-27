package com.omgservers.model.version;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionFileModel {

    @NotEmpty
    String fileName;

    @NotEmpty
    @ToString.Exclude
    String base64content;
}
