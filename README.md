# 🚀 Android Developer Assignment – Local Assignment

> **Email & OTP Authentication with Session Management**  
> Developed using **Jetpack Compose**, **Kotlin**, and **MVVM Architecture**

---

## 📌 Project Overview

This Android application demonstrates an **email-based authentication flow using OTP**, followed by a **session screen with a live timer and logout functionality**.

The assignment is implemented **without any backend**, as instructed, with the primary focus on:
- Correct OTP logic and expiry handling
- Clean state management
- Proper architecture separation
- Modern Android development practices

---

## ✨ Features Implemented

- Email-based login
- 6-digit OTP generation and validation
- OTP expiry after **60 seconds**
- Maximum **3 OTP verification attempts**
- Resend OTP invalidates the previous OTP
- Session screen with **live running timer**
- Logout functionality that stops the session
- Firebase Analytics integration
- UI built entirely using **Jetpack Compose (no XML layouts)**

---

## 🔐 Application Flow

```text
Login Screen
    ↓
OTP Verification Screen
    ↓
Session Screen (Live Timer)
    ↓
Logout → Back to Login
