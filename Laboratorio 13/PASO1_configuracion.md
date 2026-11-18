# Paso 1 · Permisos y clave de Maps

## AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

<application ...>
    <meta-data
        android:name="com.google.android.geo.API_KEY"
        android:value="${MAPS_API_KEY}" />
    ...
</application>
```

## app/build.gradle.kts
```kotlin
val localProps = java.util.Properties().apply {
    val archivo = rootProject.file("local.properties")
    if (archivo.exists()) archivo.inputStream().use { load(it) }
}

android {
    defaultConfig {
        manifestPlaceholders["MAPS_API_KEY"] = localProps.getProperty("MAPS_API_KEY", "")
    }
}
```

## local.properties (NO se sube a GitHub)
```
MAPS_API_KEY=TU_CLAVE_AQUI
```
Verifica que `.gitignore` contenga `local.properties`.
