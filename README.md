# TestGapScanner

## Project Setup

The plugin requires `JDK 11` but the project has to be compiled with `JDK 17+`, because certain dependencies require a
higher JDK:

```
[ERROR] Errors: 
[ERROR]   TestGapScannerMojoTest.test:22 » UnsupportedClassVersion org/eclipse/core/runtime/IAdaptable has been compiled 
by a more recent version of the Java Runtime (class file version 61.0), this version of the Java Runtime only recognizes
class file versions up to 55.0
```

## Running Tests

Before being able to run `TestGapScannerMojoTest` one has to execute `mvn clean test` so the necessary test resources
are generated by Takari.