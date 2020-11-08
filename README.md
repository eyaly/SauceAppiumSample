# Running Appium Tests on Sauce Labs Platform
This project contains Java examples for running Appium tests on Sauce Labs platform:

- [Native Android App on real devices](#run-native-app-tests-on-sauce-labs-android-real-devices-in-the-sauce-labs-platform)
- [Web App on real Android devices](#run-web-app-tests-on-sauce-labs-android-real-devices-in-the-sauce-labs-platform)
- [Native iOS App on real devices](#run-native-app-tests-on-sauce-labs-ios-real-devices-in-the-sauce-labs-platform)
- [Web App on real iOS devices](#run-web-app-tests-on-sauce-labs-ios-real-devices-in-the-sauce-labs-platform)

## Important information
### Environment variables for Sauce Labs
The examples in this repository that can run on Sauce Labs use environment variables, make sure you've added the following

    # For Sauce Labs Real devices in the New UI
    export SAUCE_USERNAME=********
    export SAUCE_ACCESS_KEY=*******
    
### Demo app(s)
The Native demo app that has been used for all these tests can be found [here](https://github.com/saucelabs/sample-app-mobile/releases).
Be aware of the fact that you need the build for the iOS real device. So please check the file you download.

> The advice is to download the files to an `apps` folder in the root of this folder.

### Upload apps to Sauce Storage
* If you want to use iOS real devices and Android real devices in the New Sauce Labs UI you need to upload the apps to the Sauce Storage.
For more information on this step please visit: [Application Storage](https://wiki.saucelabs.com/display/DOCS/Application+Storage).
* In the app capability you must use `storage:<app-id>` or `storage:filename=<file-name>`. For more information on this step please visit: [Application Storage](https://wiki.saucelabs.com/display/DOCS/Application+Storage).
* Change the value of appName in the native apps tests for Android and iOS according to your app name.
### Useful Links 
* How to upload the apps to the Sauce Storage and the app capability: [Application Storage](https://wiki.saucelabs.com/display/DOCS/Application+Storage).
* Appium Capabilities for Real Device Testing: [Appium Capabilities](https://wiki.saucelabs.com/display/DOCS/Appium+Capabilities+for+Real+Device+Testing).
* Sauce Labs Data Center Endpoints: [Data Center EndPoints](https://wiki.saucelabs.com/display/DOCS/Data+Center+Endpoints).
* How to set the pass/fail status of a test: [Annotating Tests with Selenium's JavaScript Executor](https://wiki.saucelabs.com/display/DOCS/Annotating+Tests+with+Selenium%27s+JavaScript+Executor).
## Run Native App tests on Sauce Labs Android real devices in the Sauce Labs Platform
If you want to run the Native Android App tests on Sauce Labs real Android devices then you can run the Android tests with

    // If using the US DC
    mvn clean install -DtestngXmlFile=testng_appium_android.xml -Dregion=us
    
    // If using the EU DC
    mvn clean install -DtestngXmlFile=testng_appium_android.xml -Dregion=eu
    
The tests will be executed on an any available Samsung device.

> NOTE: Make sure you are in the folder `SauceAppiumSample` when you execute this command

## Run Web App tests on Sauce Labs Android real devices in the Sauce Labs Platform
If you want to run the Web App tests on Sauce Labs real Android devices then you can run the web tests with

    // If using the US DC
    mvn clean install -DtestngXmlFile=testng_appium_android_web.xml -Dregion=us
    
    // If using the EU DC
    mvn clean install -DtestngXmlFile=testng_appium_android_web.xml -Dregion=eu
    
The tests will be executed on an any available Samsung device.

> NOTE: Make sure you are in the folder `SauceAppiumSample` when you execute this command

## Run Native App tests on Sauce Labs iOS real devices in the Sauce Labs Platform
If you want to run the Native iOS App tests on Sauce Labs real devices then you can run the iOS tests with

    // If using the US DC
    mvn clean install -DtestngXmlFile=testng_appium_ios.xml -Dregion=us
    
    // If using the EU DC
    mvn clean install -DtestngXmlFile=testng_appium_ios.xml -Dregion=eu
    
The tests will be executed on a iPhone 8.
> NOTE: Make sure you are in the folder `SauceAppiumSample` when you execute this command

## Run Web App tests on Sauce Labs iOS real devices in the Sauce Labs Platform
If you want to run the Web App tests on Sauce Labs real iOS devices then you can run the web tests with

    // If using the US DC
    mvn clean install -DtestngXmlFile=testng_appium_ios_web.xml -Dregion=us
    
    // If using the EU DC
    mvn clean install -DtestngXmlFile=testng_appium_ios_web.xml -Dregion=eu
    
The tests will be executed on a iPhone 8.
> NOTE: Make sure you are in the folder `SauceAppiumSample` when you execute this command
