androidaccessdb
===============

access and copy db file to pc to use. (a part of Show android db flow - use SunnyPoint Utils plugin to open db file)

Setup:
------

 in root build.gradle

    allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
    }

in app build.gradle

    dependencies {
                compile 'com.github.cvnhan:androidaccessdb:[version]'
    }

ex: compile 'com.github.cvnhan:androidaccessdb:a62c687737'

How to use:
-----------

in Application:
```java

    public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SPAccessConfigs.setupInitFirst(getApplicationContext(), "main.db", null);
        SPAccessConfigs.setEnableLog(true);

        //option 1: move db file to sdcard
//        SPAccessDBUtils.backupDB();

        //option 2: register get db via socket
        SPAccessConfigs.setRegisterBackup(true);
//        SPAccessSocketClientComm.startDaemon(1234);

        //option 3:
        SPAccessConfigs.enableAllSocketFeature(true);
        SPAccessSocketClientComm.startDaemon(1234);
    }
}

```

in manifests:

    <application
            android:name=".App">
    
    </application>
