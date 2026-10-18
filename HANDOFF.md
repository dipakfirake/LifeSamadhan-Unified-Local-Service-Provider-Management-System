# Project Handoff & State Document

**Date:** 2026-10-15  
**Workspace:** `d:/AAAAAA_PGDAC/Major Project Compare`

---

## 1. Executive Summary & Overview
The task has been completed under the `/goal` workflow:
1. **Resolved Startup & Registration Failures**: Fixed the Lombok bytecode generator collision, unseeded reference tables, and security authorization path mismatches.
2. **Added Maharashtrian People & Mumbai / Navi Mumbai Locations**:
   - Seeded **16 comprehensive locations** across Mumbai and Navi Mumbai (pincodes 400001 through 410210).
   - Created **8 Maharashtrian Service Providers** across 8 distinct service categories with active Mumbai/Navi Mumbai service areas, hourly rates, and verified statuses.
   - Created **6 Maharashtrian Customers** with authentic addresses located in Mumbai and Navi Mumbai.
   - All logins tested and verified via live automated JWT authentication test suites.

---

## 2. Mumbai & Navi Mumbai Locations (Seeded in DB)

| ID | District / Region | Pincode | Area / Landmark |
|---|---|---|---|
| **5** | Mumbai | `400001` | Fort / Colaba / South Mumbai |
| **6** | Mumbai | `400050` | Bandra West |
| **7** | Mumbai | `400053` | Andheri West / Lokhandwala |
| **12** | Mumbai | `400028` | Dadar West / Shivaji Park |
| **13** | Mumbai | `400092` | Borivali West |
| **14** | Mumbai | `400077` | Ghatkopar East |
| **15** | Mumbai | `400064` | Malad West |
| **16** | Mumbai | `400012` | Parel / Lalbaug |
| **9** | Navi Mumbai | `400703` | Vashi (Sector 17) |
| **17** | Navi Mumbai | `400706` | Nerul (Palm Beach) |
| **18** | Navi Mumbai | `400614` | CBD Belapur |
| **19** | Navi Mumbai | `410210` | Kharghar (Hiranandani) |
| **20** | Navi Mumbai | `410206` | Panvel |
| **21** | Navi Mumbai | `400708` | Airoli |
| **22** | Navi Mumbai | `400709` | Kopar Khairane |
| **23** | Navi Mumbai | `400705` | Sanpada |

*(Pune, Thane, Nagpur, and Nashik locations also remain available in the system).*

---

## 3. Seeded Maharashtrian Service Providers

All provider accounts are active, verified, and ready to accept bookings:

| Name | Email | Password | Mobile | Category | Type | Hourly Rate | Coverage |
|---|---|---|---|---|---|---|---|
| **Sachin Deshmukh** | `sachin.deshmukh@mail.com` | `Provider@123` | `9820011001` | Plumbing | `INDIVIDUAL` | ₹350 | Mumbai (Dadar, Bandra, Andheri) |
| **Ganesh Pawar** | `ganesh.pawar@mail.com` | `Provider@123` | `9820011002` | Electrical | `INDIVIDUAL` | ₹400 | Navi Mumbai (Vashi, Nerul, Sanpada) |
| **Santosh Shinde** | `santosh.shinde@mail.com` | `Provider@123` | `9820011003` | Carpentry | `CONTRACTOR` | ₹450 | Mumbai (Andheri, Borivali, Malad) |
| **Nitin Patil** | `nitin.patil@mail.com` | `Provider@123` | `9820011004` | AC Services | `AGENCY` | ₹550 | Navi Mumbai (Vashi, Nerul, Belapur, Kharghar) |
| **Rohan Kadam** | `rohan.kadam@mail.com` | `Provider@123` | `9820011005` | Painting | `CONTRACTOR` | ₹500 | Mumbai (Dadar, Parel, Bandra) |
| **Swapnil Gaikwad** | `swapnil.gaikwad@mail.com` | `Provider@123` | `9820011006` | Home Cleaning | `COMPANY` | ₹600 | Navi Mumbai (Kharghar, Panvel, Belapur) |
| **Mahesh Jadhav** | `mahesh.jadhav@mail.com` | `Provider@123` | `9820011007` | Appliance Repair | `INDIVIDUAL` | ₹420 | Mumbai (Ghatkopar, Dadar, Fort) |
| **Amol More** | `amol.more@mail.com` | `Provider@123` | `9820011008` | Pest Control | `AGENCY` | ₹450 | Navi Mumbai (Airoli, Kopar Khairane, Vashi) |

---

## 4. Seeded Maharashtrian Customers

All customer accounts are active with realistic addresses in Mumbai and Navi Mumbai:

| Name | Email | Password | Mobile | Address | City / Area |
|---|---|---|---|---|---|
| **Aniket Kulkarni** | `aniket.kulkarni@mail.com` | `Customer@123` | `9819022001` | B-204, Shreeram Niwas, Ranade Road, Dadar West | Mumbai (400028) |
| **Pooja Salunkhe** | `pooja.salunkhe@mail.com` | `Customer@123` | `9819022002` | Flat 501, Sea View Residency, Sector 17 | Navi Mumbai (400703) |
| **Vikram Joshi** | `vikram.joshi@mail.com` | `Customer@123` | `9819022003` | A-12, Green Acres, Lokhandwala Complex, Andheri West | Mumbai (400053) |
| **Snehal Sawant** | `snehal.sawant@mail.com` | `Customer@123` | `9819022004` | Flat 102, Palm Beach Towers, Sector 28 | Navi Mumbai (400706) |
| **Rahul Bhosale** | `rahul.bhosale@mail.com` | `Customer@123` | `9819022005` | Row House No. 4, Hiranandani Estate, Sector 7 | Navi Mumbai (410210) |
| **Tanvi Chavan** | `tanvi.chavan@mail.com` | `Customer@123` | `9819022006` | Flat 803, Hill View Heights, Shimpoli Road, Borivali West | Mumbai (400092) |

---

## 5. Other Key Accounts

| Role | Email | Password |
|---|---|---|
| **Admin** | `admin@mail.com` | `Admin@LifeSamadan123` |
| **Default Customer** | `testcustomer@mail.com` | `Customer@123` |
| **Default Provider** | `testprovider@mail.com` | `Provider@123` |

---

## 6. Registration 500 Error Resolution & Validation

### Issue Diagnosis:
- When a user submitted the registration form in the browser with an already registered mobile number or email, the backend had only verified `existsByEmail()`, while the MySQL `users` table had a unique constraint on `mobile` (`UK_63cf888pmqtt5tipcne79xsbm`).
- Unhandled `SQLIntegrityConstraintViolationException` threw HTTP 500 (Internal Server Error).
- Note on browser console errors: `prompt.js:1 Uncaught SyntaxError` and `event.js:14 Permissions policy violation: unload` are injected by client-side browser extensions (e.g., AI helpers or form fillers) and do not originate from application code.

### Fix Implemented:
1. **Added Mobile Uniqueness Checks**:
   - Added `existsByMobile(String mobile)` in `UserRepository` and `UserService`.
   - Updated `AuthController.register()` to check `userService.existsByMobile()` before saving, returning HTTP 409 with `"Mobile number already registered. Please use a different mobile number."`.
   - Added explicit catch for `DataIntegrityViolationException` in `AuthController.register()` to safeguard against race conditions.
2. **Frontend Error Feedback**:
   - Updated `Frontend/src/utils/api.js` to retain `error.response` in the rejection object.
   - `Frontend/src/pages/Register.jsx` now displays specific duplicate errors directly in the UI alert banner.

### Verification Results (`scratch/test_register_robustness.ps1`):
1. **Duplicate Email**: HTTP 409 Conflict with `"Email already registered. Please login or use a different email."` [PASSED]
2. **Duplicate Mobile**: HTTP 409 Conflict with `"Mobile number already registered. Please use a different mobile number."` [PASSED]
3. **Fresh Customer Registration**: HTTP 201 Created and immediate JWT login [PASSED]
4. **Fresh Service Provider Registration**: HTTP 201 Created and immediate JWT login [PASSED]

---

## 7. Runtime Verification Results

Executed automated test suite `scratch/test_maharashtrian_records.ps1`:
1. **Locations**: 23 total records verified. Mumbai: 8 locations, Navi Mumbai: 8 locations.
2. **Provider Logins**: All 8 Maharashtrian providers successfully authenticated via `POST /api/auth/login` returning valid JWTs with `role: "SERVICEPROVIDER"`.
3. **Customer Logins**: All 6 Maharashtrian customers successfully authenticated via `POST /api/auth/login` returning valid JWTs with `role: "CUSTOMER"`.
4. **Provider Search (Mumbai)**:
   - Plumbing in Mumbai -> `Sachin Deshmukh` returned.
   - Carpentry in Mumbai -> `Santosh Shinde` returned.
   - Painting in Mumbai -> `Rohan Kadam` returned.
5. **Provider Search (Navi Mumbai)**:
   - Electrical in Navi Mumbai -> `Ganesh Pawar` returned.
   - AC Services in Navi Mumbai -> `Nitin Patil` returned.
   - Home Cleaning in Navi Mumbai -> `Swapnil Gaikwad` returned.
6. **Customer Dashboard Profile API**: Verified `Aniket Kulkarni` loads his Dadar, Mumbai address and location ID 12.
7. **Provider Dashboard Me API**: Verified `Sachin Deshmukh` loads his provider ID and availability.

---

## 9. Real-time Notifications & Cancellation Resolution

### Issue A: SignalR 403 Forbidden Negotiation Error
- **Root Cause**: `Frontend/.env` had `VITE_BACKEND_TYPE=DOTNET`, which forced `notificationService.js` to initialize `signalRService` on `/hubs/notifications/negotiate`. Since the Spring Boot backend is active, Spring Boot does not serve `/hubs` endpoints.
- **Fix**:
  1. Updated `Frontend/.env` to `VITE_BACKEND_TYPE=SPRINGBOOT`.
  2. Enhanced `detectBackend()` in `Frontend/src/utils/api.js` to proactively test `/api/auth/test` and save `SPRINGBOOT` to `localStorage`.
  3. Updated `notificationService.js` to prioritize `SPRINGBOOT`, establishing SockJS/STOMP over `/ws` endpoints.
  4. Removed redundant `export { detectBackend }` from [Frontend/src/utils/api.js](file:///d:/AAAAAA_PGDAC/Major%20Project%20Compare/Frontend/src/utils/api.js) (as it is declared as `export const detectBackend`), resolving the JavaScript syntax error.

### Issue B: Cancellation 400 Bad Request / ByteBuddy Serialization Failure
- **Root Cause**:
  1. In Spring Boot 3.2+ (Spring Framework 6.1+), path variables require explicit names when compiled without `-parameters`. `@PathVariable Long assignmentId` caused `IllegalArgumentException: Name for argument not specified`.
  2. `CustomerController.cancelRequest` returned `Map.of("assignment", assignment)` with a raw Hibernate entity instead of `dtoMapper.toLegacyAssignmentMap(assignment)`, triggering `HttpMessageConversionException: ByteBuddyInterceptor`.
- **Fix**:
  1. Named all parameters explicitly: `@PathVariable("assignmentId")` and `@PathVariable("id")` in `CustomerController.java`.
  2. Replaced raw entity return with `dtoMapper.toLegacyAssignmentMap(assignment)` to serialize clean DTO JSON.
  3. Recompiled with `javac --release 17 -parameters`.
  4. In `CustomerDashboard.jsx`, updated `handleCancel` to show exact server error messages and refresh the request list.
- **Verification (`scratch/test_cancel_real.ps1`)**:
  - Authenticated as `aniket.kulkarni@mail.com` (Customer, Dadar Mumbai).
  - Booked new request with `sachin.deshmukh@mail.com` (Plumber).
  - Successfully called `POST /api/customer/cancel/{id}` with 200 OK.
  - Verified assignment status updated to `CANCELLED` [PASSED 100%].

---

## 10. Past Date/Time Booking Prevention & Request Filtering

### Problem Statement & Requirements:
1. Prevent users from selecting or submitting past dates or past times when booking a service.
2. Filter out past/elapsed requests from active views ("requests before time want not to be shown") so stale or past jobs do not clutter upcoming work for either customers or providers.

### Solutions & Architecture Implemented:

1. **Frontend Booking Restrictions (`CustomerDashboard.jsx`)**:
   - **Date Picker**: Bound `min={getTodayDateString()}` (local date in `YYYY-MM-DD` format via `toLocaleDateString('en-CA')`), natively disabling past calendar days.
   - **Time Picker**: When today's date is selected, bound `min={getCurrentTimeString()}` (local `HH:mm`), preventing selection of past hours/minutes.
   - **Reset on Day Change**: If a customer switches back to today after picking an earlier time, the time field automatically resets to empty.
   - **Real-Time Warning Banner**: If a user attempts to manually type or bypass constraints with a past timestamp, a red warning alert appears: `⚠️ Cannot select a past date or time. Please select an upcoming date and time.`.
   - **Button Guard**: The "Confirm Booking" button is strictly disabled (`disabled={bookingLoading || isDateTimeInPast(...)}`).
   - **Client Submit Guard**: `handleBookService` verifies `dateTime > new Date()` before transmitting any network payload.

2. **Backend API Validation (`CustomerController.java` & `.NET CustomerController.cs`)**:
   - In Spring Boot `CustomerController.createServiceRequest`:
     - Parses `scheduledDate` supporting ISO-8601 UTC offsets, local timestamps, and epoch instants.
     - Enforces that `scheduledDate` cannot be null or empty (HTTP 400: `"A valid scheduled date and time is required."`).
     - Verifies `!scheduledDate.isBefore(LocalDateTime.now().minusMinutes(2))`. If in the past, immediately rejects with HTTP 400 Bad Request: `"Cannot book a service for a past date or time. Please select an upcoming date and time."`.
   - In .NET `CustomerController.SubmitRequest`:
     - Added identical past check rejecting `scheduledDate < DateTime.Now.AddMinutes(-2)` with HTTP 400.

3. **Customer Dashboard Filtering ("Requests Before Time Hidden from Active View")**:
   - Added filter tabs in `CustomerDashboard.jsx`:
     - **Upcoming & Active (Default)**: Shows only requests that are active or scheduled for the present/future (`scheduledDate >= now - 10m` or `STARTED`). Requests whose scheduled time has elapsed or are finished/cancelled are hidden from this view.
     - **Past & Completed**: Neatly archives all past, completed, cancelled, or time-elapsed requests with exact counts.
     - **All Requests**: Allows viewing full history.
   - Enhanced Card UI: Added `📅 Scheduled: ...` with localized date & time and red `Time Elapsed` indicator for unfulfilled past bookings.

4. **Provider Dashboard Filtering ("Stale/Past Jobs Hidden from Active Jobs")**:
   - In `ProviderDashboard.jsx`, implemented `isAssignmentBeforeTime(item)`.
   - Any job whose scheduled time has passed (`scheduledDate < now - 15m`) or whose 30-minute response timer expired without starting is filtered out of `Active Jobs`.
   - Stale/elapsed jobs automatically move to the `History` tab with an explicit `EXPIRED / TIME ELAPSED` badge, keeping the provider's active job queue clean and actionable.

### Automated Test Verification (`scratch/verify_all_past_date_handling.ps1`):
1. **Past Date Rejection (Yesterday)**: Returned HTTP 400 Bad Request with `"Cannot book a service for a past date or time. Please select an upcoming date and time."` [PASSED]
2. **Past Time Rejection Today (-2 hours)**: Returned HTTP 400 Bad Request with `"Cannot book a service for a past date or time. Please select an upcoming date and time."` [PASSED]
3. **Missing Date Rejection (Null)**: Returned HTTP 400 Bad Request with `"A valid scheduled date and time is required."` [PASSED]
4. **Valid Future Booking (+3 days)**: Returned HTTP 200 OK with Assignment ID 7 [PASSED]
5. **Customer Request Partitioning**:
   - Upcoming / Active: 2 requests (including new future booking ID 7)
   - Past / Completed: 4 requests correctly filtered away from the default active view [PASSED]
6. **Provider Workflow & Acceptance**: Provider `Sachin Deshmukh` fetched assignments and accepted assignment 7 -> Status `ACCEPTED` [PASSED]
7. **Production Frontend Build**: `npm run build` completed in 5.2s with 0 errors [PASSED]

---

## 11. Active Services
- **Spring Boot Backend**: Running on `http://localhost:8080` (Task 2059).
- **Vite React Frontend**: Running on `http://localhost:5173`.
- **MySQL Database**: `lifesamadhan_spring` on `localhost:3306`.

---

## 12. Fix for Timezone Discrepancy on Upcoming Date/Time Bookings

### Root Cause
- When customers selected an upcoming time today (e.g. 10:00 PM / 11:00 PM) or early morning next day (e.g. 2:00 AM) in India Standard Time (+05:30), the frontend `dateTime.toISOString()` converted it to UTC (5 hours 30 minutes earlier, e.g. 4:30 PM / 5:30 PM).
- In Spring Boot `CustomerController.java`, `ZonedDateTime.parse(dateStr).toLocalDateTime()` stripped the timezone offset without converting to server local time, yielding the UTC clock reading (`16:30` / `17:30`).
- Because server local time was `21:58`, the backend saw `17:30` and mistakenly treated it as in the past, rejecting the request with HTTP 400.

### Solution Applied
1. **Frontend (`CustomerDashboard.jsx`)**:
   - Switched from UTC `toISOString()` to local ISO string: `${bookingData.scheduledDate}T${bookingData.scheduledTime}:00` (e.g. `2026-10-16T10:00:00`).
   - Hardened `getTodayDateString()` and `getCurrentTimeString()` to use explicit year, month, day, hour, and minute parsing.
   - Added a 1-minute client-side buffer for clock latency.
2. **Spring Boot Backend (`CustomerController.java`)**:
   - Replaced naive `ZonedDateTime.toLocalDateTime()` with `LocalDateTime.ofInstant(instant, ZoneId.systemDefault())` and `withZoneSameInstant(ZoneId.systemDefault())`.
   - Now, whether a client sends a local ISO string (`2026-10-15T23:00:00`) or a UTC ISO string (`2026-10-15T17:30:00.000Z`), the server accurately translates it to the server's local timezone before performing the past-date validation.
3. **.NET Backend (`CustomerController.cs`)**:
   - Added `scheduledDate.ToLocalTime()` conversion for `DateTimeKind.Utc` before comparing with `DateTime.Now`.
4. **Verification**:
   - Automated test suite `scratch/test_timezone_and_past_validation.ps1` verified:
     - Past date rejection: HTTP 400 [PASSED]
     - Past time today rejection: HTTP 400 [PASSED]
     - Upcoming time today (local ISO): HTTP 200 [PASSED]
     - Next date (local ISO): HTTP 200 [PASSED]
     - Next date (UTC ISO with 'Z'): HTTP 200 [PASSED]
   - Frontend production build (`npm run build`) succeeded with 0 errors.

---

## 13. Production Deployment & GitHub Secrets Sanitization

### Security Audit Findings & Resolutions
1. **Gmail App Password & Razorpay Secret**:
   - `application.properties` previously contained raw Gmail App Password (`[REDACTED_GMAIL_KEY]`), Razorpay Secret (`[REDACTED_RAZORPAY_SECRET]`), and DB Password (`[REDACTED_DB_PASS]`).
   - `LifeSamadhan/Backend/appsettings.json` previously contained identical credentials.
2. **Protective Measures Implemented**:
   - Created **Root `.gitignore`** (`D:\AAAAAA_PGDAC\Major Project Compare\.gitignore`) protecting:
     - All `.env`, `.env.*` files (allowing only `.env.example`).
     - `application-local.properties` & `appsettings.Development.json` (where raw local keys live).
     - `target/`, `bin/`, `obj/`, `node_modules/`, `dist/`, `.metadata/`, `BOOT-INF/`, build/test logs.
   - Externalized `SpringBootLifeSamadhan/src/main/resources/application.properties` with environment variables (`${DB_URL}`, `${DB_USERNAME}`, `${DB_PASSWORD}`, `${JWT_SECRET}`, `${RAZORPAY_KEY_ID}`, `${RAZORPAY_KEY_SECRET}`, `${MAIL_USERNAME}`, `${MAIL_PASSWORD}`) and safe placeholders.
   - Created `SpringBootLifeSamadhan/src/main/resources/application-local.properties` (ignored by git) so local development works seamlessly with zero configuration.
   - Created `Frontend/vercel.json` with SPA routing rewrites.
   - Updated `Frontend/src/utils/api.js` and `Frontend/src/services/springboot-websocket.js` to dynamically connect to `VITE_API_URL` and `VITE_WS_URL`.
   - Verified with automated leak detector: **0 personal credentials exposed in any git-committable file**.
