# Vending Machine Low Level Design

## Overview

This document describes the design of a Vending Machine system using multiple design patterns.

The system supports:

- Product management
- Inventory handling
- Different payment methods
- Machine state changes
- Event notifications

---

# Design Patterns Used

## 1. State Pattern

### Purpose

The State Pattern is used to change the behavior of the vending machine based on its current state.

Instead of writing multiple `if-else` conditions inside the machine, each state handles its own behavior.

---

## States

### IdleState

Represents the default state when the machine is waiting for a user.

Responsibilities:

- Accept money
- Move machine to `HasMoneyState`

Flow:
