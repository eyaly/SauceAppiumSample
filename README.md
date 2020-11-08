# Running Appium Tests on Sauce Labs platform
This project contains Java examples for running Appium tests on Sauce Labs platform:

- [Native Android App real devices on the new Sauce Labs UI](#run-native-app-tests-on-sauce-labs-android-real-devices-in-the-new-sauce-labs-ui)
- [Native iOS App real devices on the new Sauce Labs UI](#run-native-app-tests-on-sauce-labs-ios-real-devices-in-the-new-sauce-labs-ui)

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
* In the app capability you must use storage:<app-id>. For more information on this step please visit: [Using Application Storage with Automated Test Builds](https://wiki.saucelabs.com/display/DOCSDEV/Application+Storage#ApplicationStorage-UsingApplicationStoragewithAutomatedTestBuilds) section of [Application Storage](https://wiki.saucelabs.com/display/DOCS/Application+Storage)
* Change the value of appID parameter in SwagLabsTest.java for Android and iOS according to your app-id.
## Run Native App tests on Sauce Labs Android real devices in the New Sauce Labs UI
If you want to run the Native Android App tests on Sauce Labs real devices in the **New Sauce Labs UI** then you can run the Android test with

    // If using the US DC
    mvn clean install -DtestngXmlFile=testng_appium_android.xml -Dregion=us
    
    // If using the EU DC
    mvn clean install -DtestngXmlFile=testng_appium_android.xml -Dregion=eu
    
The tests will be executed on a Samsung Galaxy 10.

> NOTE: Make sure you are in the folder `SauceAppiumSample` when you execute this command

## Run Native App tests on Sauce Labs iOS real devices in the New Sauce Labs UI
If you want to run the Native iOS App tests on Sauce Labs real devices in the **New Sauce Labs UI** then you can run the iOS test with

    // If using the US DC
    mvn clean install -DtestngXmlFile=testng_appium_ios.xml -Dregion=us
    
    // If using the EU DC
    mvn clean install -DtestngXmlFile=testng_appium_ios.xml -Dregion=eu
    
The tests will be executed on a iPhone 8.
> NOTE: Make sure you are in the folder `SauceAppiumSample` when you execute this command
