package com.omgservers.service.operation.generateId;

import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GenerateIdOperationImpl implements GenerateIdOperation {

    final long datacenterId;
    final long nodeId;

    long lastTimestamp;
    long sequence;

    public GenerateIdOperationImpl(final GetConfigOperation getConfigOperation) {
        datacenterId = getConfigOperation.getServiceConfig().datacenterId();
        if (datacenterId < 0 || datacenterId >= 1 << DATACENTER_ID_BITS) {
            throw new ServerSideConflictException("wrong datacenterId, value=" + datacenterId);
        }

        nodeId = getConfigOperation.getServiceConfig().nodeId();
        if (nodeId < 0 || nodeId >= 1 << NODE_ID_BITS) {
            throw new ServerSideConflictException("wrong nodeId, value=" + nodeId);
        }

        log.info("Generator was initialized, " +
                        "(timestampBits={}, datacenterIdBits={}, nodeIdBits={}, sequenceBits={}) " +
                        "datacenterId={}, nodeId={}",
                TIMESTAMP_BITS, DATACENTER_ID_BITS, NODE_ID_BITS, SEQUENCE_BITS,
                datacenterId, nodeId);

        lastTimestamp = 0;
        sequence = 0;
    }

    @Override
    public synchronized long generateId() {
        var timestamp = System.currentTimeMillis() - TIMESTAMP_EPOCH;

        if (timestamp == lastTimestamp) {
            sequence += 1;

            if (sequence >= (1 << SEQUENCE_BITS)) {
                throw new ServerSideInternalException(String.format("sequence was overflowed, " +
                        "sequence=%d, timestamp=%d", sequence, timestamp));
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
