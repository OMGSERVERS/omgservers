package com.omgservers.tester.operation.createVersionArchive;

import java.io.IOException;
import java.util.Map;

public interface CreateVersionArchiveOperation {

    byte[] createArchive(Map<String, String> files) throws IOException;

    String createBase64Archive(Map<String, String> files) throws IOException;
}
