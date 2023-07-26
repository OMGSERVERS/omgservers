package com.omgservers.application.operation.generateIdOperation;

import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.operation.getConfigOperation.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GenerateIdOperationImpl implements GenerateIdOperation {

    final long datacenterId;
    final long nodeId;

    long lastTimestamp;
    long sequence;

    public GenerateIdOperationImpl(GetConfigOperation getConfigOperation) {
        datacenterId = getConfigOperation.getConfig().datacenterId();
        if (datacenterId < 0 || datacenterId >= 1 << DATACENTER_ID_BITS) {
            throw new ServerSideConflictException("wrong datacenterId, value=" + datacenterId);
        }

        nodeId = getConfigOperation.getConfig().nodeId();
        if (nodeId < 0 || nodeId >= 1 << NODE_ID_BITS) {
            throw new ServerSideConflictException("wrong nodeId, value=" + nodeId);
        }

        log.info("Generator was initialized, datacenterId={}, nodeId={}", datacenterId, nodeId);

        lastTimestamp = 0;
        sequence = 0;
    }

    @Override
    public synchronized long generateId() {
        var timestamp = System.currentTimeMillis() - TIMESTAMP_EPOCH;

        if (timestamp == lastTimestamp) {
            sequence += 1;

            if (sequence >= (1 << SEQUENCE_BITS)) {
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

        var id = timestamp << TIMESTAMP_OFFSET |
                datacenterId << DATACENTER_ID_OFFSET |
                nodeId << NODE_ID_OFFSET |
                sequence;
        return id;
    }
}
