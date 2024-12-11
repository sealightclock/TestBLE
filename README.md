# TestBLE
Use this Android Studio project to learn more about BLE API.

Implemented:
- BLE device scanning.

Baseline technologies:
- Kotlin
- Jetpack Compose
- libs.versions.toml
- build.gradle.kts

Additional technologies:
- MVVM
  - ViewModel:
    val isScanning: StateFlow<Boolean> = _isScanning
    val scanResults = bleManager.scanResults (LiveData)
  - Activity:
    ViewModelProvider.get()
  - View:
    val isScanning by viewModel.isScanning.collectAsState()
    val scanResults by viewModel.scanResults.observeAsState(emptyList())

Features:
- BLE API
- Accompanist permissions library: accompanist-permissions = { module = "com.google.accompanist:accompanist-permissions", version.ref = "accompanist" }

Notes:
- Use a smartphone to test the code, as the emulator does not support BLE scan.
- It looks like Jetpack Compose is very suitable for the single-Activity architecture: no need for multiple Activities or single Activity with multiple Fragments.

TODO:
- Clean up the code.