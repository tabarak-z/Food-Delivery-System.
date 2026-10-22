# Food Delivery System

A simple Java console application that lets a customer place a food order, calculate the bill, pay, and track the order until it is delivered. Built with object-oriented design principles from a UML class diagram.

## Features

- Create an order with an auto-generated order number (starts at 1001)
- Menu with two item types:
  - **Meal:** base price + $1.50 per extra topping
  - **Drink:** base price + $1 if large
- Add items with a default quantity of 1 or a specific quantity
- Delivery options: **Standard** ($2) and **Express** ($5)
- Payment methods: **Cash** (no fee) and **Card** (2% fee)
- Order status tracking: `NEW`, `PAID`, `DELIVERED`, `CANCELLED`

## Business Rules

- Items can only be added while the order is `NEW`
- Subtotal = sum of (item price x quantity)
- Total = subtotal + delivery fee
- Only a `NEW` order can be cancelled
- Only a `PAID` order can be delivered

## OOP Concepts Used

- **Abstraction and inheritance:** `MenuItem` is the parent of `Meal` and `Drink`
- **Polymorphism:** each item calculates its own final price
- **Interfaces:** `DeliveryOption` and `PaymentMethod`, so new options can be added without changing the `Order` class
- **Composition:** an `Order` contains its `OrderLine` objects
- **Encapsulation:** private fields with public getters and validation
- **Method overloading:** two `addItem` methods in `Order`
- **Enum:** `OrderStatus`

## Project Structure
