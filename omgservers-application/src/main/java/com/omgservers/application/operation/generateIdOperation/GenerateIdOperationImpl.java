package com.omgservers.application.operation.generateIdOperation;

import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.operation.getConfigOperation.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GenerateIdOperationImpl implements GenerateIdOperation {

    final long nodeId;

    long lastTimestamp;
    long sequence;

    public GenerateIdOperationImpl(GetConfigOperation getConfigOperation) {
        nodeId = getConfigOperation.getConfig().nodeId();

        lastTimestamp = 0;
        sequence = 0;
    }

    @Override
    public synchronized long generateId() {
        var timestamp = timestamp();

        if (timestamp == lastTimestamp) {
            sequence += 1;

            if (sequence >= 1024) {
                throw new ServerSideInternalException("sequence was overflowed, timestamp=" + timestamp);
            }
        } else if (timestamp > lastTimestamp) {
            sequence = 0;
        }

        if (timestamp < lastTimestamp) {
            throw new ServerSideInternalException(String.format("wrong system time, " +
                    "current=%d, last=%d", timestamp, lastTimestamp));
        } else {
            lastTimestamp = timestamp;
        }

        var id = timestamp << 23 | nodeId << 13 | sequence;
        return id;
    }

    long timestamp() {
        return System.currentTimeMillis() - 1690056599000L;
    }
}
