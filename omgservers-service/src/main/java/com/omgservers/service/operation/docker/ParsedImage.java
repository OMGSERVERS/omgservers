package com.omgservers.service.operation.docker;

public record ParsedImage(String namespace,
                          String name,
                          String tag) {
}
