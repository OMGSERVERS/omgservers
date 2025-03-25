package com.omgservers.schema.message.body;

public enum DisconnectionReasonEnum {
    /**
     * Client was inactive more than allowed.
     */
    CLIENT_INACTIVITY,

    /**
     * Client was disconnected due to internal failure.
     */
    INTERNAL_FAILURE
}
