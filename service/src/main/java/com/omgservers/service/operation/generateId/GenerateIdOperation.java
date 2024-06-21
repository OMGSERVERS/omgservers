package com.omgservers.service.operation.generateId;

public interface GenerateIdOperation {
    long TIMESTAMP_EPOCH = 1690056599000L;

    int SEQUENCE_BITS = 10;
    int SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;
    int INSTANCE_ID_BITS = 8;
    int INSTANCE_ID_MASK = (1 << INSTANCE_ID_BITS) - 1;
    int DATACENTER_ID_BITS = 5;
    int DATACENTER_ID_MASK = (1 << DATACENTER_ID_BITS) - 1;

    int INSTANCE_ID_OFFSET = SEQUENCE_BITS;
    int DATACENTER_ID_OFFSET = SEQUENCE_BITS + INSTANCE_ID_BITS;
    int TIMESTAMP_BITS = 41;
    int TIMESTAMP_OFFSET = DATACENTER_ID_OFFSET + DATACENTER_ID_BITS;

    long generateId();

    default String generateStringId() {
        return String.valueOf(generateId());
    }
}
