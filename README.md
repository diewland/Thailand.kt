# Thailand.kt
Android version of jquery.Thailand.js

### Original Project
https://github.com/earthchie/jquery.Thailand.js

### Installation

1. Download database to assets folder
```
wget https://raw.githubusercontent.com/earthchie/jquery.Thailand.js/refs/heads/master/jquery.Thailand.js/database/raw_database/raw_database.json -O app/src/main/assets/raw_database.json
```

2. Checkout code
```
git submodule add https://github.com/diewland/Thailand.kt.git app/src/main/java/com/diewland/thailand
```

### Dependency

build.gradle.kts (Module :app)
```
dependencies {
    ...
    implementation(libs.fastjson)
    implementation(libs.kotlin.reflect) // JSONException: default constructor not found
}
```

libs.versions.toml
```
[versions]
fastjson = "1.2.55"
kotlinReflect = "1.4.21"

[libraries]
fastjson = { module = "com.alibaba:fastjson", version.ref = "fastjson" }
kotlin-reflect = { module = "org.jetbrains.kotlin:kotlin-reflect", version.ref = "kotlinReflect" }
```
