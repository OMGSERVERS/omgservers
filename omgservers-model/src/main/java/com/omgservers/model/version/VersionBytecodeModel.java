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
public class VersionBytecodeModel {

    static public VersionBytecodeModel create() {
        VersionBytecodeModel bytecodeFiles = new VersionBytecodeModel();
        bytecodeFiles.setFiles(new ArrayList<>());
        return bytecodeFiles;
    }

    @NotNull
    @NotEmpty
    List<VersionFileModel> files;
}
