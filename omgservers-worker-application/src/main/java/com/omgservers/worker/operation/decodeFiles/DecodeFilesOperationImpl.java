package com.omgservers.worker.operation.decodeFiles;

import com.omgservers.model.file.FileModel;
import com.omgservers.model.file.EncodedFileModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.List;

@Slf4j
@ApplicationScoped
class DecodeFilesOperationImpl implements DecodeFilesOperation {

    @Override
    public List<FileModel> decodeFiles(final List<EncodedFileModel> files) {
        return files.stream()
                .map(file -> {
                    final var fileName = file.getFileName();
                    final var base64Content = file.getBase64content();
                    final var decodedBytes = Base64.getDecoder().decode(base64Content);
                    return new FileModel(fileName, decodedBytes);
                })
                .toList();
    }
}
