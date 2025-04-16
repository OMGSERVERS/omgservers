package com.omgservers.service.operation.server;

public interface GenerateIdOperation {
    long TIMESTAMP_EPOCH = 1690056599000L;

    int SEQUENCE_BITS = 10;
    int SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;
    int NODE_ID_BITS = 12;
    int NODE_ID_MASK = (1 << NODE_ID_BITS) - 1;
    int NODE_ID_OFFSET = SEQUENCE_BITS;

    int TIMESTAMP_BITS = 41;
    int TIMESTAMP_OFFSET = SEQUENCE_BITS + NODE_ID_BITS;

    long generateId();

    default String generateStringId() {
        return String.valueOf(generateId());
    }
}
