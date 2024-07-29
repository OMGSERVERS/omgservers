package com.omgservers.service.entrypoint.developer.impl.operation;

import com.omgservers.schema.model.file.DecodedFileModel;
import com.omgservers.schema.model.file.EncodedFileModel;

import java.util.List;

public interface EncodeFilesOperation {
    List<EncodedFileModel> encodeFiles(List<DecodedFileModel> files);
}
