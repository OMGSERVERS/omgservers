package com.omgservers.model.version;

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

    List<VersionFileModel> files;
}
