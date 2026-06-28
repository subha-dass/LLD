# Bounded Priority Blocking Queue


## Problem

PriorityBlockingQueue is thread safe but UNBOUNDED.

Example:

PriorityBlockingQueue<>(100)

does NOT mean max size 100.

100 is only initial capacity.

Queue can grow:

100
1000
100000


This can cause memory problems.


---

# Solution

Combine:

1. PriorityBlockingQueue
2. Semaphore


Responsibilities:


## PriorityBlockingQueue

Responsible for ordering.


Example:

Priority:

HIGH
MEDIUM
LOW


Queue always gives HIGH first.


---

## Semaphore

Responsible for capacity.


Example:

capacity = 3


Semaphore permits:

3


Every insert:

acquire()

permits decreases:


3 -> 2 -> 1 -> 0


When permits becomes 0:

producer blocks.


---

# Flow


Producer:

put(task)


Step 1:

semaphore.acquire()


Step 2:

queue.put(task)



Consumer:

take()


Step 1:

queue.take()


Step 2:

semaphore.release()



---

# Example


Capacity = 3


Producer adds:


Task1
Task2
Task3


Queue:

Task1
Task2
Task3


Semaphore:

0 permits



Now producer tries:

Task4


It blocks.


Consumer removes Task1.


Semaphore.release()


Permit becomes 1.


Producer wakes up and adds Task4.



---

# Why not ArrayBlockingQueue?


ArrayBlockingQueue already provides:

- bounded
- blocking


But it does NOT provide priority ordering.


PriorityBlockingQueue provides:

- priority ordering
- thread safety


Semaphore adds:

- bounded capacity


---

# Real world usage


Notification system:


HIGH priority:

OTP
Payment failure


MEDIUM:

Marketing


LOW:

Daily report



Queue:

HIGH
MEDIUM
LOW


Semaphore:

max pending notifications allowed


---

# Complexity


Insert:

O(log n)


Remove:

O(log n)


Semaphore:

O(1)



---

# Interview Explanation


"I used PriorityBlockingQueue for ordering tasks by priority.
Since PriorityBlockingQueue is unbounded, I added Semaphore to enforce maximum capacity.
Producer acquires a permit before inserting.
Consumer releases permit after consuming.
This provides bounded + priority based + thread safe queue."