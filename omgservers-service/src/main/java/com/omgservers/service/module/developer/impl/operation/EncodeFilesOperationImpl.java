package com.omgservers.service.module.developer.impl.operation;

import com.omgservers.model.file.DecodedFileModel;
import com.omgservers.model.file.EncodedFileModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Slf4j
@ApplicationScoped
class EncodeFilesOperationImpl implements EncodeFilesOperation {

    @Override
    public List<EncodedFileModel> encodeFiles(final List<DecodedFileModel> files) {
        return files.stream()
                .map(file -> {
                    final var fileName = file.getFileName();
                    final var fileContent = file.getContent();
                    final var base64Content = Base64.getEncoder()
                            .encodeToString(fileContent.getBytes(StandardCharsets.UTF_8));
                    return new EncodedFileModel(fileName, base64Content);
                })
                .toList();
    }
}
