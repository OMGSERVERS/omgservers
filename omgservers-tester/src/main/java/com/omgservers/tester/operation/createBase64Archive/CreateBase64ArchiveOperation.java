package com.omgservers.tester.operation.createBase64Archive;

import java.io.IOException;
import java.util.Map;

public interface CreateBase64ArchiveOperation {

    String createBase64Archive(Map<String, String> files) throws IOException;
}
