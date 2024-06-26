package com.omgservers.tester.operation.createVersionArchive;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateVersionArchiveOperationImpl implements CreateVersionArchiveOperation {

    @Override
    public byte[] createArchive(Map<String, String> files) throws IOException {
        try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             final var zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {

            for (var entry : files.entrySet()) {
                final var fileName = entry.getKey();
                final var fileContent = entry.getValue();

                final var zipEntry = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(fileContent.getBytes(StandardCharsets.UTF_8));
                zipOutputStream.closeEntry();
            }

            zipOutputStream.finish();

            return byteArrayOutputStream.toByteArray();
        }
    }

    @Override
    public String createBase64Archive(Map<String, String> files) throws IOException {
        return Base64.getEncoder().encodeToString(createArchive(files));
    }
}
