# Coupon System Design (LLD)

## 1. Problem Statement

Design a Coupon Management System that:

* Supports multiple coupon types
* Validates coupon applicability
* Applies discounts to orders
* Supports extensible rule evaluation
* Handles concurrent coupon evaluation
* Allows easy addition of new coupon types and rules

---

# 2. Requirements

## Functional Requirements

### Coupon Features

* Create coupon
* Validate coupon
* Apply coupon to order
* Support multiple coupon categories:

    * Percentage discount
    * Flat discount
    * Buy X Get Y
    * Free shipping

### Rule Features

* Minimum order amount
* User eligibility
* Product category validation
* Usage limits
* Expiry validation

---

## 3. High Level Flow

```text
User
 ↓
CouponService
 ↓
CouponManager
 ↓
Coupon
 ↓
Rule Evaluation
 ↓
Discount Calculation
 ↓
Updated Order
```

---

# 4. Class Diagram

```text
+----------------+
| CouponService  |
+----------------+
| applyCoupon()  |
+----------------+
        |
        ↓

+----------------+
| Coupon         |
+----------------+
| id             |
| code           |
| ruleList       |
| strategy       |
+----------------+
| isApplicable() |
| applyDiscount()|
+----------------+

        |
        ↓

+----------------+
| Rule           |
+----------------+
| check(order)   |
+----------------+

        ↑
-------------------------
|       |       |        |
Min   User   Expiry   Usage

```

---

# 5. Core Entities

## Order

```java
class Order {
    String orderId;
    double totalAmount;
    User user;
    List<Item> items;
}
```

---

## Coupon

```java
class Coupon {

    String code;

    List<Rule> ruleList;

    DiscountStrategy strategy;

    public boolean isApplicable(Order order){

        for(Rule rule: ruleList){
            if(!rule.check(order)){
                return false;
            }
        }

        return true;
    }

    public double applyDiscount(Order order){
        return strategy.calculate(order);
    }
}
```

---

## Rule Interface

```java
interface Rule {
    boolean check(Order order);
}
```

---

### Rule Implementations

```java
class MinOrderRule
class ExpiryRule
class UserRule
class UsageLimitRule
```

Example:

```java
class MinOrderRule implements Rule {

    double minAmount;

    public boolean check(Order order){
        return order.totalAmount >= minAmount;
    }
}
```

---

# 6. Strategy Pattern

Used for discount calculation.

```java
interface DiscountStrategy {
    double calculate(Order order);
}
```

Implementations:

```text
PercentageDiscount
FlatDiscount
BuyXGetY
FreeShipping
```

Example:

```java
class PercentageDiscount
implements DiscountStrategy {

    double percent;

    public double calculate(
        Order order
    ){
        return order.totalAmount
               * percent / 100;
    }
}
```

---

# 7. Coupon Service

```java
class CouponService {

    CouponRepository repo;

    public Order applyCoupon(
            String code,
            Order order
    ){

        Coupon coupon =
            repo.getCoupon(code);

        if(!coupon.isApplicable(order)){
            throw new RuntimeException(
                "Invalid Coupon"
            );
        }

        double discount =
            coupon.applyDiscount(order);

        order.totalAmount -= discount;

        return order;
    }
}
```

---

# 8. Concurrent Coupon Evaluation

When checking multiple coupons:

```java
ExecutorService executor;

List<Future<Coupon>> futures;
```

Flow:

```text
Submit coupon validation
        ↓
Parallel evaluation
        ↓
Collect results
        ↓
Return applicable coupons
```

Better implementation:

```java
List<Coupon> coupons =
allCoupons.stream()
.filter(
 c -> c.isApplicable(order)
)
.toList();
```

Use async only if:

* DB calls
* Remote APIs
* Heavy computation

---

# 9. Design Patterns Used

| Pattern    | Usage                 |
| ---------- | --------------------- |
| Strategy   | Discount calculation  |
| Composite  | Multiple rules        |
| Factory    | Coupon creation       |
| Repository | Coupon storage        |
| Singleton  | Coupon Manager        |
| Executor   | Concurrent evaluation |

---

# 10. Database Schema

## coupon

```sql
coupon(
 id,
 code,
 type,
 discount,
 start_time,
 end_time
)
```

## coupon_rule

```sql
coupon_rule(
 id,
 coupon_id,
 rule_type,
 config
)
```

## coupon_usage

```sql
coupon_usage(
 id,
 user_id,
 coupon_id,
 usage_count
)
```

---

# 11. Scalability Improvements

* Redis cache for coupon lookup
* Event-driven usage tracking
* Rule engine support
* Distributed locking
* CQRS for coupon analytics

---

# 12. Complexity

| Operation       | Complexity |
| --------------- | ---------- |
| Coupon Lookup   | O(1)       |
| Rule Validation | O(R)       |
| Discount Apply  | O(1)       |

---

# 13. Future Enhancements

* Dynamic coupon creation
* Coupon stacking
* Campaign engine
* Real-time analytics
* ML-based coupon recommendation
