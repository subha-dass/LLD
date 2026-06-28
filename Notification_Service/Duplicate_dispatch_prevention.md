# AtomicReference<DeliveryStatus> - Duplicate Dispatch Prevention

## Problem Statement

In a notification system:

- Producer adds notification to queue.
- Worker threads consume notifications.
- Worker sends notification.
- If sending fails, notification can be retried.

Problem:

During retry/re-enqueue, the same notification can be picked by multiple workers.

