package com.omgservers.model.version;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionSourceCodeModel {

    static public VersionSourceCodeModel create() {
        final var sourceCodeFiles = new VersionSourceCodeModel();
        sourceCodeFiles.setFiles(new ArrayList<>());
        return sourceCodeFiles;
    }

    @NotNull
    @NotEmpty
    List<VersionFileModel> files;
}
