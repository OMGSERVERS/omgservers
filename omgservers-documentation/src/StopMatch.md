# Delete match

```mermaid
graph TD;

doStopRuntime("doStopRuntime()") --> StopRequested(STOP_REQUESTED)
StopRequested(STOP_REQUESTED) --> syncMatchCommand("syncMatchCommand(STOP_MATCH)")

```

