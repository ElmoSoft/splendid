# splendid
Splendid -  is a Java-based test automation framework that unites all testing layers: Mobile applications (web, native, hybrid), WEB applications, REST services.
By ElmoSoft Team (http://elmosoft.net)

To add framework to your project specify in POM.xml

```
<repositories>
    <repository>
        <id>splendid-repo-release</id>
        <url>http://maven-repo-splendid.elmosoft.net.s3.amazonaws.com/release</url>
        <releases>
            <enabled>true</enabled>
        </releases>
    </repository>
</repositories>
```

And dependency:
```
<dependency>
    <groupId>net.elmosoft</groupId>
    <artifactId>splendid</artifactId>
    <version>1.1.0</version>
</dependency>
```
### Installing

1. Open a terminal, navigate to the project repository and type:
	```
	mvn install -DskipTests 
	```
	All the required dependencies will automatically be installed locally.

### Mobile Appium Gestures
Class src.main.java.net.elmosoft.splendid.utils.IMobileUtils - swipe, scroll, etc.

### Available Options List
 - Dbrowser ["CHROME", "ANDROID", "IOS", "FIREFOX"]
 - DremoteDriver [boolean] - Default: false. Used to identify where to find driver: locally or by remote address
 - DremoteWebdriverUrl [string] - By default 0.0.0.0, port default 4444
 - DautoScreenshot [boolean] - Default: false. Global switch for taking screenshots. When disabled, screenshots will be captured only after failures
 - Dheadless - Default: false. Run tests in headless browser mode. Enabled when headless=true. 

src/test/resources/local.properties - IOS/Android capability configuration
### How to use

1. Open a terminal, navigate to the project repository and type:
	```
	mvn verify
	```

2. All tests should triggered


### Reporting

After suite finish execute command `mvn allure:serve`
Chrome should open automatically with reporting page

To finish process click on `Control+C` in terminal