# Thread Pool Low-Level Design (LLD)

## Features

- **Fixed Number of Worker Threads**
    - Initialize with a predefined number of threads.
    - Each thread continuously polls the task queue for work.

- **Dynamic Thread Scaling**
    - Ability to increase or decrease threads based on system load.
    - Scaling policies (e.g., CPU usage, queue length) determine adjustments.

- **Task Queue**
    - Centralized queue to hold incoming tasks.
    - Supports FIFO ordering by default.

- **Task Prioritization (Optional)**
    - Priority-based scheduling for tasks.
    - Higher priority tasks are dequeued before lower priority ones.

- **Graceful Shutdown**
    - Stop accepting new tasks.
    - Complete all queued tasks before shutting down.
    - Ensure worker threads terminate cleanly.

- **Thread-Safe Implementation**
    - Synchronization mechanisms (locks, condition variables, atomic operations).
    - Prevent race conditions and ensure safe concurrent access.



# Design Patterns in Thread Pool System

## 1. Strategy Pattern → FIFO vs Priority Scheduling
- **Purpose**: Allow flexible task scheduling strategies.
- **Implementation**:
  - Define a `SchedulingStrategy` interface.
  - Implement `FifoStrategy` (queue tasks in arrival order).
  - Implement `PriorityStrategy` (queue tasks based on priority).
- **Benefit**: Easily switch between FIFO and priority scheduling without changing core thread pool logic.

## 2. Producer-Consumer Pattern → Tasks and Workers
- **Purpose**: Decouple task submission (producer) from task execution (consumer).
- **Implementation**:
  - Producers submit tasks into a shared `BlockingQueue`.
  - Workers continuously consume tasks from the queue and execute them.
- **Benefit**: Ensures safe concurrent task handling and balances workload between producers and consumers.

## 3. Factory Pattern → Worker Creation
- **Purpose**: Encapsulate worker object creation.
- **Implementation**:
  - A `WorkerFactory` class creates new `Worker` instances.
  - Thread pool uses the factory when scaling up.
- **Benefit**: Centralizes worker creation logic, making it easier to extend or customize worker behavior.

## 4. Singleton Pattern (Optional) → Global Thread Pool
- **Purpose**: Ensure only one instance of the thread pool exists globally.
- **Implementation**:
  - Use a `ThreadPool` singleton with a private constructor and static `getInstance()` method.
- **Benefit**: Provides a shared, globally accessible pool for all parts of the application.

## 5. Thread Pool Pattern → Reuse Threads
- **Purpose**: Avoid overhead of creating/destroying threads repeatedly.
- **Implementation**:
  - Maintain a pool of reusable worker threads.
  - Workers loop and fetch tasks until shutdown.
- **Benefit**: Improves performance and resource utilization.

## 6. Monitor Pattern → Scale-Up/Scale-Down Logic
- **Purpose**: Dynamically adjust pool size based on system load.
- **Implementation**:
  - A monitor thread periodically checks queue length and worker utilization.
  - Calls `scaleUpIfRequired()` or `scaleDownIfRequired()` accordingly.
- **Benefit**: Keeps the system responsive under varying load while avoiding resource waste.

---

## Summary
- **Strategy Pattern** → Flexible scheduling (FIFO vs Priority).
- **Producer-Consumer Pattern** → Safe task submission/execution.
- **Factory Pattern** → Centralized worker creation.
- **Singleton Pattern** → Optional global pool instance.
- **Thread Pool Pattern** → Efficient thread reuse.
- **Monitor Pattern** → Adaptive scaling logic.
