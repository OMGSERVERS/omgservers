package com.omgservers.model.version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionFileModel {
    String fileName;
    @ToString.Exclude
    String base64content;
}
