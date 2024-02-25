package com.omgservers.service.module.developer.impl.operation;

import com.omgservers.model.file.DecodedFileModel;
import com.omgservers.model.file.EncodedFileModel;

import java.util.List;

public interface EncodeFilesOperation {
    List<EncodedFileModel> encodeFiles(List<DecodedFileModel> files);
}
