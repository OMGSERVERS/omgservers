package com.omgservers.ctl.operation.ctl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class OutputObjectOperationImpl implements OutputObjectOperation {

    private final String RESET = "\u001B[0m";
    private final String BLUE = "\u001B[34m";
    private final String GREEN = "\u001B[32m";

    final ObjectMapper objectMapper;

    @Override
    public void execute(final Object object) {
        execute(object, false);
    }

    @Override
    @SneakyThrows
    public void execute(final Object object,
                        final boolean prettyPrint) {
        if (prettyPrint) {
            final var currentWriter = objectMapper.writerWithDefaultPrettyPrinter();
            final var json = currentWriter.writeValueAsString(object);
            printColoredJson(json);
        } else {
            System.err.println(objectMapper.writeValueAsString(object));
        }
    }

    void printColoredJson(final String json) {
        final var coloredJson = json
                .replaceAll("\"(.*?)\"(?=\\s*:)", BLUE + "\"$1\"" + RESET)
                .replaceAll(":\\s*\"(.*?)\"", ": " + GREEN + "\"$1\"" + RESET);
        System.err.println(coloredJson);
    }
}
