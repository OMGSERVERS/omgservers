package com.omgservers.tester.operation.getLuaFile;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetLuaFileOperationImpl implements GetLuaFileOperation {

    @Override
    public String getOmgserverLua() {
        final var inputStream = this.getClass().getResourceAsStream("/omgserver.lua");
        try (Scanner scanner = new Scanner(inputStream, "UTF-8")) {
            return scanner.useDelimiter("\\A").next();
        }
    }
}
