# NavCast - Weather-Integrated Navigation App

<<<<<<< HEAD
<!-- Tags -->
<p align="center">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android" />
  <img src="https://img.shields.io/badge/Kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin" />
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpack-compose" />
  <img src="https://img.shields.io/badge/OpenStreetMap-7EBC6F?style=for-the-badge&logo=openstreetmap" />
  <img src="https://img.shields.io/badge/Retrofit-009688?style=for-the-badge&logo=retrofit" />
  <img src="https://img.shields.io/badge/Hilt-34A853?style=for-the-badge&logo=google" />
</p>
=======
<div align="center">

<img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android" />
<img src="https://img.shields.io/badge/Kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin" />
<img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpack-compose" />

**An innovative Android navigation app that overlays live weather data on your journey routes.**

📌 [Overview](#-overview) • [Features](#-features) • [Tech Stack](#-tech-stack) • [Installation](#-installation) • [Usage](#-usage) • [Architecture](#-architecture) • [Project Structure](#-project-structure) • [Contributing](#-contributing) • [License](#-license)

</div>

---

## 🌟 Overview

NavCast revolutionizes navigation by combining route planning with weather forecasting. Instead of checking weather separately, users can see real-time weather conditions at key points along their journey, enabling better travel planning and decision-making.

-----

## ✨ Features

  * **🗺️ Smart Navigation**

      * Real-time route generation using OpenStreetMap
      * Interactive map with zoom, pan, and location controls
      * Address-to-coordinate geocoding via Nominatim API
      * Dynamic route visualization with curved paths

  * **🌤️ Weather Integration**

      * Live weather data from OpenWeatherMap API
      * Weather bubbles displayed at strategic route points
      * Temperature, humidity, wind speed, and conditions
      * Automatic weather updates for route segments

  * **📱 Modern UI/UX**

      * Clean, intuitive Material 3 design
      * Jetpack Compose reactive UI
      * Responsive input fields with validation
      * Loading states and error handling

  * **🏗️ Architecture & Performance**

      * MVVM architecture pattern
      * Hilt dependency injection
      * Kotlin Coroutines for async operations
      * Efficient memory management and caching

-----

## 🛠️ Tech Stack

| Category            | Technology                 | Platform            |
| :------------------ | :------------------------- | :------------------ |
| **Android** | (API 24+)                  | Language: Kotlin    |
| **UI Framework** | Jetpack Compose            |                     |
| **Architecture** | MVVM + Repository Pattern  |                     |
| **Dependency Injection** | Hilt                       |                     |
| **Async Operations**| Kotlin Coroutines          |                     |
| **Networking** | Retrofit + OkHttp          |                     |
| **Maps** | OpenStreetMap + OSMDroid   |                     |
| **Weather API** | OpenWeatherMap             |                     |
| **Geocoding** | Nominatim (OpenStreetMap)  |                     |

-----

## 📋 Prerequisites

  * Android Studio Arctic Fox or later
  * Android SDK 24+ (Android 7.0)
  * OpenWeatherMap API key (free)
  * Internet connection for map tiles and weather data

-----

## 🚀 Installation

### 1\. Clone the Repository

```bash
git clone https://github.com/HellDeadShot/navcast.git
cd navcast
```

### 2\. Get API Keys

Sign up at [OpenWeatherMap](https://openweathermap.org/api) for a free API key.

**Note:** No Google Maps API key is required (uses OpenStreetMap).

### 3\. Configure API Key

Add your OpenWeatherMap API key to `local.properties`:

```text
WEATHER_API_KEY=your_openweathermap_api_key_here
```

### 4\. Build and Run

```bash
./gradlew assembleDebug
```

Or open in Android Studio and run directly.

-----

## 📱 Usage

### Basic Navigation

  * **Enter Origin:** Type your starting location in the "From" field.
  * **Enter Destination:** Type your destination in the "Destination" field.
  * **Get Route:** Tap "Get Route" to generate the navigation path.
  * **View Weather:** Tap weather bubbles along the route for detailed forecasts.

### Supported Address Formats

  * Full addresses: `123 Main Street, New York, NY 10001`
  * City names: `New York, NY`
  * Landmarks: `Times Square, New York`
  * Postal codes: `10001`
  * International: `London, UK`

### Example Routes for Testing

  * From: `New York, NY` To: `Boston, MA`
  * From: `Los Angeles, CA` To: `San Francisco, CA`
  * From: `London, UK` To: `Manchester, UK`

-----

## 🏗️ Project Structure

```text
app/src/main/java/com/helldeadshot/navcast/
├── data/
│   ├── api/
│   │   ├── NominatimService.kt
│   │   └── WeatherApiService.kt
│   ├── models/
│   │   ├── OSMModels.kt
│   │   ├── RouteModels.kt
│   │   └── WeatherModels.kt
│   └── repository/
│       └── NavCastRepository.kt
├── di/
│   └── AppModule.kt
├── ui/
│   ├── components/
│   │   ├── InputSection.kt
│   │   ├── MapSection.kt
│   │   └── ErrorSnackbar.kt
│   ├── screens/
│   │   ├── NavCastScreen.kt
│   │   └── NavCastViewModel.kt
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
├── MainActivity.kt
└── NavCastApplication.kt
```

-----

## 🔧 Configuration

### Gradle Dependencies

The project uses version catalogs (`libs.versions.toml`) for dependency management:

```toml
[versions]
kotlin = "2.0.21"
compose = "2024.09.00"
hilt = "2.52"
retrofit = "2.11.0"
osmdroid = "6.1.18"

[libraries]
# Core Android & Compose
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }

# Maps & Location
osmdroid-android = { group = "org.osmdroid", name = "osmdroid-android", version.ref = "osmdroid" }

# Networking
retrofit = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit" }

# Dependency Injection
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
```

### Network Security

The app includes network security configuration for OpenStreetMap and weather API endpoints:

```xml
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">nominatim.openstreetmap.org</domain>
        <domain includeSubdomains="true">api.openweathermap.org</domain>
    </domain-config>
</network-security-config>
```

-----

## 🔄 Architecture Overview

### MVVM Pattern

  * **Model:** Data classes and repository for API interactions.
  * **View:** Jetpack Compose UI components.
  * **ViewModel:** Business logic and state management.

### Data Flow

User input → ViewModel → Repository → API Services (Nominatim + OpenWeatherMap) → Repository → ViewModel → UI State → Compose UI

### Key Components

  * `NavCastRepository`: Centralized data access layer
  * `NominatimService`: Address geocoding
  * `WeatherApiService`: Weather data retrieval
  * `NavCastViewModel`: UI state management
  * `MapSection`: Interactive map display

-----

## 🤝 Contributing

We welcome contributions\! Please follow these steps:

1.  **Fork** the repository.
2.  **Create** a feature branch (`git checkout -b feature/amazing-feature`).
3.  **Commit** your changes (`git commit -m 'Add amazing feature'`).
4.  **Push** to the branch (`git push origin feature/amazing-feature`).
5.  **Open** a Pull Request.

### Development Guidelines

  * Follow Kotlin coding conventions.
  * Use meaningful commit messages.
  * Add unit tests for new features.
  * Update documentation as needed.

-----

## 📄 License

This project is licensed under the MIT License - see the `LICENSE` file for details.

-----

## 🙏 Acknowledgments

  * [OpenStreetMap](https://www.openstreetmap.org) for free mapping services.
  * [OpenWeatherMap](https://openweathermap.org) for weather data API.
  * Android Jetpack team for Compose framework.
  * [OSMDroid](https://github.com/osmdroid/osmdroid) library maintainers.

-----

## 📞 Contact & Support

  * **Developer:** [@helldeadshot](https://github.com/helldeadshot)
  * **Issues:** [GitHub Issues](https://github.com/yourusername/navcast/issues)
  * **Discussions:** [GitHub Discussions](https://github.com/yourusername/navcast/discussions)

-----
⭐ Star this repository if you found it helpful\!

Made with ❤️ and ☕ by **HellDeadShot**

>>>>>>> 5e1c33ff654a67d901d3c5b59e35d257fa7a51a4
