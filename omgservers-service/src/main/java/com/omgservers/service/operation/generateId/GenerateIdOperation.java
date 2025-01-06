package com.omgservers.service.operation.generateId;

public interface GenerateIdOperation {
    long TIMESTAMP_EPOCH = 1690056599000L;

    int SEQUENCE_BITS = 12;
    int SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;
    int INSTANCE_ID_BITS = 10;
    int INSTANCE_ID_MASK = (1 << INSTANCE_ID_BITS) - 1;

    int INSTANCE_ID_OFFSET = SEQUENCE_BITS;
    int TIMESTAMP_BITS = 41;
    int TIMESTAMP_OFFSET = SEQUENCE_BITS + INSTANCE_ID_OFFSET;

    long generateId();

    default String generateStringId() {
        return String.valueOf(generateId());
    }
}
