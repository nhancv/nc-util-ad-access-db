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

    public class App extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            SPAccessDBHelper.setInstance(getApplicationContext());
    
            SPAccessConfigs.setEnableLog(true);
            SPAccessDBUtils.backupDB(getApplicationContext(), "main.db", null);
        }
    }

in manifests:

    <application
            android:name=".App">
    
    </application>
