# Android Developer Assignment – Local Assignment

## Project Summary
This Android application implements an **email-based OTP authentication flow** followed by a **session screen with a live timer and logout functionality**.  
The project is built **without any backend**, as required, using **Jetpack Compose** and **MVVM architecture**.

---

## OTP Logic & Expiry Handling (Mandatory)

- A **6-digit numeric OTP** is generated locally for the entered email.
- OTP is valid for **60 seconds**.
- A maximum of **3 verification attempts** is allowed.
- If the OTP expires or attempts are exhausted:
  - Verification is blocked.
  - User must request **Resend OTP**.
- Resending OTP:
  - Invalidates the previous OTP.
  - Generates a new OTP.
  - Resets both the timer and attempt count.
- OTP validation, expiry countdown, and retry handling are fully managed inside the **ViewModel** using coroutines.

> Since no backend or delivery service was required, OTP is displayed on-screen **only for testing and demonstration purposes**.

---

## Data Structures Used & Reason (Mandatory)

- **`Map<String, OtpData>`**  
  Used to store OTP data per email for fast lookup and easy invalidation.
- **`data class AuthState`**  
  Acts as a single source of truth for UI state in Jetpack Compose.
- **`StateFlow<AuthState>`**  
  Used for reactive, lifecycle-aware UI updates.
- **Coroutine Jobs**  
  Used for handling OTP expiry timer and session timer with proper cancellation.

These choices ensure clean state management, predictable behavior, and lifecycle safety.

---

## External SDK Used & Justification (Mandatory)

### Firebase Analytics
- Chosen because it is lightweight and does not require backend setup.
- Used to track key authentication-related events.

**Events tracked:**
- OTP Generated
- OTP Verification Success
- OTP Verification Failure
- User Logout

Other Firebase services (Auth, Messaging) were not used as they were outside the assignment scope.

---

## GPT Usage Disclosure (Mandatory)

GPT was used **selectively as a development assistant**.

### Implemented with full understanding:
- OTP generation, expiry, retry, and resend logic
- Session timer and logout functionality
- MVVM architecture and state handling
- Jetpack Compose UI flow
- Technology and SDK selection

### How GPT was used:
- To speed up boilerplate code writing
- To validate syntax and Compose best practices
- To cross-check edge cases during implementation

GPT was used to **optimize development time**, while all core logic, architecture decisions, and implementation were **fully understood and owned**.

---

## Setup Instructions (Mandatory)

```bash
git clone https://github.com/MdAhmadRaja/LocalAssignment.git
