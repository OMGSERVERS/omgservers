package com.omgservers.application.operation.generateIdOperation;

public interface GenerateIdOperation {
    long TIMESTAMP_EPOCH = 1690056599000L;

    int SEQUENCE_BITS = 10;
    int SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;
    int NODE_ID_BITS = 8;
    int NODE_ID_MASK = (1 << NODE_ID_BITS) - 1;
    int DATACENTER_ID_BITS = 5;
    int DATACENTER_ID_MASK = (1 << DATACENTER_ID_BITS) - 1;

    int NODE_ID_OFFSET = SEQUENCE_BITS;
    int DATACENTER_ID_OFFSET = SEQUENCE_BITS + NODE_ID_BITS;
    int TIMESTAMP_OFFSET = DATACENTER_ID_OFFSET + DATACENTER_ID_BITS;

    long generateId();
}
