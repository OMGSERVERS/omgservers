package com.omgservers.worker.operation.decodeFiles;

import com.omgservers.model.file.FileModel;
import com.omgservers.model.file.EncodedFileModel;

import java.util.List;

public interface DecodeFilesOperation {
    List<FileModel> decodeFiles(List<EncodedFileModel> files);
}
