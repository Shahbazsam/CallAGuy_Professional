# Call A Guy – Professional App

The **Professional App** of the Call A Guy service booking system.  
This Android application enables service professionals to register, manage their service offerings, accept job requests, and complete assigned tasks.  
Built with **Kotlin Multiplatform-ready components** and a modern **MVI architecture**.

---

## 📱 Features

- **Professional Registration** – Submit personal details, CV, and select offered services.
- **Approval Workflow** – New professionals are reviewed by the admin before they can start accepting jobs.
- **Approval Status Screen** – Dedicated “Not Approved Yet” screen until admin approval is granted.
- **Job Listings** – View service requests from customers based on your chosen services and service area.
- **Job Details & Actions** – Open job requests, accept assignments, and mark jobs as completed.
- **Profile Management** – Upload and update profile picture, view professional details.
- **Image Uploads** – Integrated image upload system for profile pictures.
- **Notifications** – Local notifications for new job requests and status updates.
- **Optimized UX** – Type-safe navigation, smooth image loading via Coil, and consistent UI patterns.

---

## 🛠 Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVI (UI → ViewModel → Repository → Data Source)
- **Dependency Injection**: Koin
- **Networking**: Ktor Client, OkHttp
- **Serialization**: Kotlinx Serialization
- **Image Loading**: Coil
- **Navigation**: Compose type-safe navigation
- **Notifications**: Local notifications (Android)

---

## 🏗 Architecture Overview

UI (Jetpack Compose)
↓
ViewModel
↓
Repository
↓
Data Source
↓
API Service (Ktor) / Local Storage

---

## 📸 Screens & Modules

- **Splash Screen**
- **Login Screen**
- **Register Screen** – Provide details, CV, and select offered services.
- **Not Approved Screen** – Displayed until admin approval.
- **Ongoing Services Screen** – List of jobs currently in progress.
- **Ongoing Service Detail Screen** – Full details for an in-progress job.
- **Service List Screen** – Available jobs filtered by service type and area.
- **Service Detail Screen** – Accept or decline a job request.
- **Profile Screen** – Manage personal information & upload profile picture.

---

## 📽 Demo

*(Placeholder for video link or GIFs)*

---

## 🚀 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Shahbazsam/CallAGuy_Professional.git

2. Open the project in Android Studio.

3. Sync Gradle and build the project.

4. Run on an emulator or connected Android device (minSdk = 26).

📌 Future Plans
Real-time job request notifications via push notifications.

Migration to Kotlin Multiplatform for iOS support.

Enhanced analytics for professionals.

👤 Author

Mohammad Shahbaz
