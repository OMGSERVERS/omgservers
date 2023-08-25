package com.omgservers.model.version;

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

    List<VersionFileModel> files;
}
