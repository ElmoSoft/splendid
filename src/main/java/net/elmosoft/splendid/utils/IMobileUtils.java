package net.elmosoft.splendid.utils;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import java.time.Duration;

import net.elmosoft.splendid.browser.DriverManager;
import net.elmosoft.splendid.driver.element.BrowserElement;
import net.elmosoft.splendid.driver.seleniumdriver.SeleniumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public interface IMobileUtils {
    public static final Logger LOGGER = LogManager.getLogger(IMobileUtils.class);
    
    long EXPLICIT_TIMEOUT = 5;
    int MINIMUM_TIMEOUT = 2;
    int DEFAULT_TOUCH_ACTION_DURATION = 1000;
    int DEFAULT_MAX_SWIPE_COUNT = 50;
    int DEFAULT_MIN_SWIPE_COUNT = 1;

    default void tap(BrowserElement element) {
        Point point = element.getLocation();
        Dimension size = element.getSize();
        this.tap(point.getX() + size.getWidth() / 2, point.getY() + size.getHeight() / 2);
    }

    default void tap(int startx, int starty) {
        this.tap(startx, starty, 1000);
    }

    /**
     * @param element browser element
     * @param capacity use capacity from 0.1 to 1
     * */
    default void setHorizontalSlider(BrowserElement element, float capacity) {
        Dimension dimension = element.getSize();
        Point location = element.getLocation();
        int x = location.getX() + (int)(dimension.getWidth() * capacity);
        this.tap(x, location.getY(), 0);
    }

    default void longTap(BrowserElement elem) {
        SeleniumDriver helper = DriverManager.getMobileDriver();
        Dimension size = (Dimension)helper.performIgnoreException(() -> {
            return elem.getSize();
        });
        int width = size.getWidth();
        int height = size.getHeight();
        int x = elem.getLocation().getX() + width / 2;
        int y = elem.getLocation().getY() + height / 2;

        try {
            this.swipe(x, y, x, y, 2500);
        } catch (Exception var8) {
            LOGGER.error("Exception: " + var8);
        }

    }

    default boolean longPress(BrowserElement element) {
        try {
            SeleniumDriver helper = DriverManager.getMobileDriver();
            TouchAction<?> action = new TouchAction((MobileDriver)helper.getWebDriver());
            LongPressOptions options = (LongPressOptions)LongPressOptions.longPressOptions().withElement(ElementOption.element(element.getWebElement()));
            action.longPress(options).release().perform();
            return true;
        } catch (Exception var5) {
            LOGGER.info("Error occurs during longPress: " + var5, var5);
            return false;
        }
    }

    default void tap(int startx, int starty, int duration) {
        try {
            SeleniumDriver helper = DriverManager.getMobileDriver();
            TouchAction<?> touchAction = new TouchAction((MobileDriver)helper.getWebDriver());
            PointOption<?> startPoint = PointOption.point(startx, starty);
            WaitOptions waitOptions = WaitOptions.waitOptions(Duration.ofMillis((long)duration));
            // do not perform waiter as using 6.0.0. appium java client we do longpress instead of simple tap even with 0 wait duration
            if (duration == 0) {
                touchAction.press(startPoint).release().perform();
            } else {
                touchAction.press(startPoint).waitAction(waitOptions).release().perform();
            }

           LOGGER.info("Tap executed: "+ new String[]{String.valueOf(startx), String.valueOf(starty)});
        } catch (Exception e) {
            throw new RuntimeException("Tap failed: "+ e.getMessage());
        }
    }

    default boolean swipe(BrowserElement element) {
        return this.swipe(element, (BrowserElement)null, IMobileUtils.Direction.UP, 50, 1000);
    }

    default boolean swipe(BrowserElement element, int count) {
        return this.swipe(element, (BrowserElement)null, IMobileUtils.Direction.UP, count, 1000);
    }

    default boolean swipe(BrowserElement element, IMobileUtils.Direction direction) {
        return this.swipe(element, (BrowserElement)null, direction, 50, 1000);
    }

    default boolean swipe(BrowserElement element, int count, int duration) {
        return this.swipe(element, (BrowserElement)null, IMobileUtils.Direction.UP, count, duration);
    }

    default boolean swipe(BrowserElement element, IMobileUtils.Direction direction, int count,
        int duration) {
        return this.swipe(element, (BrowserElement)null, direction, count, duration);
    }

    default boolean swipe(BrowserElement element, BrowserElement container, int count) {
        return this.swipe(element, container, IMobileUtils.Direction.UP, count, 1000);
    }

    default boolean swipe(BrowserElement element, BrowserElement container) {
        return this.swipe(element, container, IMobileUtils.Direction.UP, 50, 1000);
    }

    default boolean swipe(BrowserElement element, BrowserElement container,
        IMobileUtils.Direction direction) {
        return this.swipe(element, container, direction, 50, 1000);
    }

    default boolean swipe(BrowserElement element, BrowserElement container,
        IMobileUtils.Direction direction, int count) {
        return this.swipe(element, container, direction, count, 1000);
    }

    default boolean swipe(BrowserElement element, BrowserElement container,
        IMobileUtils.Direction direction, int count, int duration) {
        boolean isVisible = element.isVisible();
        if (isVisible) {
            LOGGER.info("element already present before swipe: " + element.getFoundBy().toString());
            return true;
        } else {
            LOGGER.info("swiping to element: " + element.getFoundBy().toString());
            IMobileUtils.Direction oppositeDirection = IMobileUtils.Direction.DOWN;
            boolean bothDirections = false;
            switch(direction) {
            case UP:
                oppositeDirection = IMobileUtils.Direction.DOWN;
                break;
            case DOWN:
                oppositeDirection = IMobileUtils.Direction.UP;
                break;
            case LEFT:
                oppositeDirection = IMobileUtils.Direction.RIGHT;
                break;
            case RIGHT:
                oppositeDirection = IMobileUtils.Direction.LEFT;
                break;
            case HORIZONTAL:
                direction = IMobileUtils.Direction.LEFT;
                oppositeDirection = IMobileUtils.Direction.RIGHT;
                bothDirections = true;
                break;
            case HORIZONTAL_RIGHT_FIRST:
                direction = IMobileUtils.Direction.RIGHT;
                oppositeDirection = IMobileUtils.Direction.LEFT;
                bothDirections = true;
                break;
            case VERTICAL:
                direction = IMobileUtils.Direction.UP;
                oppositeDirection = IMobileUtils.Direction.DOWN;
                bothDirections = true;
                break;
            case VERTICAL_DOWN_FIRST:
                direction = IMobileUtils.Direction.DOWN;
                oppositeDirection = IMobileUtils.Direction.UP;
                bothDirections = true;
                break;
            default:
                throw new RuntimeException("Unsupported direction for swipeInContainerTillElement: " + direction);
            }

            int currentCount;
            for(currentCount = count; !isVisible && currentCount-- > 0; isVisible = element.isVisible()) {
                LOGGER.debug("Element not present! Swipe " + direction + " will be executed to element: " + element.getFoundBy().toString());
                this.swipeInContainer(container, direction, duration);
                LOGGER.info("Swipe was executed. Attempts remain: " + currentCount);
            }

            for(currentCount = count; bothDirections && !isVisible && currentCount-- > 0; isVisible = element.isVisible()) {
                LOGGER.debug("Element not present! Swipe " + oppositeDirection + " will be executed to element: " + element.getFoundBy().toString());
                this.swipeInContainer(container, oppositeDirection, duration);
                LOGGER.info("Swipe was executed. Attempts remain: " + currentCount);
            }

            LOGGER.info("Result: " + isVisible);
            return isVisible;
        }
    }

    default void swipe(int startx, int starty, int endx, int endy, int duration) {
        LOGGER.debug("Starting swipe...");
        LOGGER.debug("Getting driver dimension size...");
        SeleniumDriver helper = DriverManager.getMobileDriver();
        Dimension scrSize = (Dimension)helper.performIgnoreException(() -> {
            return helper.manage().window().getSize();
        });
        LOGGER.debug("Finished driver dimension size...");
        if (endx >= scrSize.width) {
            LOGGER.warn("endx coordinate is bigger then device width! It will be limited!");
            endx = scrSize.width - 1;
        } else {
            endx = Math.max(1, endx);
        }

        if (endy >= scrSize.height) {
            LOGGER.warn("endy coordinate is bigger then device height! It will be limited!");
            endy = scrSize.height - 1;
        } else {
            endy = Math.max(1, endy);
        }

        LOGGER.debug("startx: " + startx + "; starty: " + starty + "; endx: " + endx + "; endy: " + endy + "; duration: " + duration);
        PointOption<?> startPoint = PointOption.point(startx, starty);
        PointOption<?> endPoint = PointOption.point(endx, endy);
        WaitOptions waitOptions = WaitOptions.waitOptions(Duration.ofMillis((long)duration));
        (new TouchAction((MobileDriver)helper.getWebDriver())).press(startPoint).waitAction(waitOptions).moveTo(endPoint).release().perform();
        LOGGER.debug("Finished swipe...");
    }

    default boolean swipeInContainer(BrowserElement container, IMobileUtils.Direction direction,
        int duration) {
        return this.swipeInContainer(container, direction, 1, duration);
    }

    /**
     * swipeInContainer
     *
     * @param container ExtendedWebElement
     * @param direction Direction
     * @param count int
     * @param duration int
     * @return boolean
     */
    default public boolean swipeInContainer(BrowserElement container, Direction direction,
        int count, int duration) {

        int startx = 0;
        int starty = 0;
        int endx = 0;
        int endy = 0;

        Point elementLocation = null;
        Dimension elementDimensions = null;
        SeleniumDriver helper = DriverManager.getMobileDriver();

        if (container == null) {
            // whole screen/driver is a container!
            WebDriver driver = helper.getWebDriver();
            elementLocation = new Point(0, 0); // initial left corner for that case

            elementDimensions = helper.performIgnoreException(() -> driver.manage().window().getSize());
        } else {
            if (!container.isVisible()) {
                Assert.fail("Cannot swipe! Impossible to find element " + container.getFoundBy());
            }
            elementLocation = container.getLocation();
            elementDimensions = helper.performIgnoreException(() -> container.getSize());
        }

        double minCoefficient = 0.3;
        double maxCoefficient = 0.6;

        // calculate default coefficient based on OS type
//        String os = IDriverPool.getDefaultDevice().getOs();
//        if (os.equalsIgnoreCase(SpecialKeywords.ANDROID)) {
//            minCoefficient = 0.25;
//            maxCoefficient = 0.5;
//        } else if (os.equalsIgnoreCase(SpecialKeywords.IOS) || os.equalsIgnoreCase(SpecialKeywords.MAC)) {
//            minCoefficient = 0.25;
//            maxCoefficient = 0.8;
//        }
        minCoefficient = 0.25;
        maxCoefficient = 0.5;

        switch (direction) {
            case LEFT:
                starty = endy = elementLocation.getY() + Math.round(elementDimensions.getHeight() / 2);

                startx = (int) (elementLocation.getX() + Math.round(maxCoefficient * elementDimensions.getWidth()));
                endx = (int) (elementLocation.getX() + Math.round(minCoefficient * elementDimensions.getWidth()));
                break;
            case RIGHT:
                starty = endy = elementLocation.getY() + Math.round(elementDimensions.getHeight() / 2);

                startx = (int) (elementLocation.getX() + Math.round(minCoefficient * elementDimensions.getWidth()));
                endx = (int) (elementLocation.getX() + Math.round(maxCoefficient * elementDimensions.getWidth()));
                break;
            case UP:
                startx = endx = elementLocation.getX() + Math.round(elementDimensions.getWidth() / 2);

                starty = (int) (elementLocation.getY() + Math.round(maxCoefficient * elementDimensions.getHeight()));
                endy = (int) (elementLocation.getY() + Math.round(minCoefficient * elementDimensions.getHeight()));
                break;
            case DOWN:
                startx = endx = elementLocation.getX() + Math.round(elementDimensions.getWidth() / 2);

                starty = (int) (elementLocation.getY() + Math.round(minCoefficient * elementDimensions.getHeight()));
                endy = (int) (elementLocation.getY() + Math.round(maxCoefficient * elementDimensions.getHeight()));
                break;
            default:
                throw new RuntimeException("Unsupported direction: " + direction);
        }

        LOGGER.debug(String
            .format("Swipe from (X = %d; Y = %d) to (X = %d; Y = %d)", startx, starty, endx, endy));

        try {
            for (int i = 0; i < count; ++i) {
                swipe(startx, starty, endx, endy, duration);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error(String
                .format("Error during Swipe from (X = %d; Y = %d) to (X = %d; Y = %d): %s", startx, starty, endx, endy, e));
        }
        return false;
    }

    default void swipeUp(int times, int duration) {
        for(int i = 0; i < times; ++i) {
            this.swipeUp(duration);
        }

    }

    default void swipeUp(int duration) {
        LOGGER.info("Swipe up will be executed.");
        this.swipeInContainer((BrowserElement)null, IMobileUtils.Direction.UP, duration);
    }

    default void swipeDown(int times, int duration) {
        for(int i = 0; i < times; ++i) {
            this.swipeDown(duration);
        }

    }

    default void swipeDown(int duration) {
        LOGGER.info("Swipe down will be executed.");
        this.swipeInContainer((BrowserElement)null, IMobileUtils.Direction.DOWN, duration);
    }

    default void swipeLeft(int times, int duration) {
        for(int i = 0; i < times; ++i) {
            this.swipeLeft(duration);
        }

    }

    default void swipeLeft(int duration) {
        LOGGER.info("Swipe left will be executed.");
        this.swipeLeft((BrowserElement)null, duration);
    }

    default void swipeLeft(BrowserElement container, int duration) {
        LOGGER.info("Swipe left will be executed.");
        this.swipeInContainer(container, IMobileUtils.Direction.LEFT, duration);
    }

    default void swipeRight(int times, int duration) {
        for(int i = 0; i < times; ++i) {
            this.swipeRight(duration);
        }

    }

    default void swipeRight(int duration) {
        LOGGER.info("Swipe right will be executed.");
        this.swipeRight((BrowserElement)null, duration);
    }

    default void swipeRight(BrowserElement container, int duration) {
        LOGGER.info("Swipe right will be executed.");
        this.swipeInContainer(container, IMobileUtils.Direction.RIGHT, duration);
    }

    default void setDeviceDefaultTimeZoneAndLanguage() {
        this.setDeviceDefaultTimeZoneAndLanguage(false);
    }

    default void setDeviceDefaultTimeZoneAndLanguage(boolean returnAppFocus) {
//        try {
//            String baseApp = "";
//            String os = IDriverPool.getDefaultDevice().getOs();
//            if (os.equalsIgnoreCase("Android")) {
//                AndroidService androidService = AndroidService.getInstance();
//                if (returnAppFocus) {
//                    baseApp = androidService.getCurrentFocusedApkDetails();
//                }
//
//                String deviceTimezone = Configuration.get(Parameter.DEFAULT_DEVICE_TIMEZONE);
//                String deviceTimeFormat = Configuration.get(Parameter.DEFAULT_DEVICE_TIME_FORMAT);
//                String deviceLanguage = Configuration.get(Parameter.DEFAULT_DEVICE_LANGUAGE);
//                TimeFormat timeFormat = TimeFormat.parse(deviceTimeFormat);
//                TimeZoneFormat timeZone = TimeZoneFormat.parse(deviceTimezone);
//                LOGGER.info("Set device timezone to " + timeZone.toString());
//                LOGGER.info("Set device time format to " + timeFormat.toString());
//                LOGGER.info("Set device language to " + deviceLanguage);
//                boolean timeZoneChanged = androidService.setDeviceTimeZone(timeZone.getTimeZone(), timeZone.getSettingsTZ(), timeFormat);
//                boolean languageChanged = androidService.setDeviceLanguage(deviceLanguage);
//                LOGGER.info(String.format("Device TimeZone was changed to timeZone '%s' : %s. Device Language was changed to language '%s': %s", deviceTimezone, timeZoneChanged, deviceLanguage, languageChanged));
//                if (returnAppFocus) {
//                    androidService.openApp(baseApp);
//                }
//            } else {
//                LOGGER.info(String.format("Current OS is %s. But we can set default TimeZone and Language only for Android.", os));
//            }
//        } catch (Exception var12) {
//            LOGGER.error(var12);
//        }

    }

    default void hideKeyboard() {
        try {
            ((MobileDriver)this.castDriver()).hideKeyboard();
        } catch (Exception var2) {
            if (!var2.getMessage().contains("Soft keyboard not present, cannot hide keyboard")) {
                LOGGER.error("Exception appears during hideKeyboard: " + var2);
            }
        }

    }

    default void zoom(IMobileUtils.Zoom type) {
        LOGGER.info("Zoom will be performed :" + type);
        MobileDriver<?> driver = (MobileDriver)this.castDriver();
        SeleniumDriver helper = DriverManager.getMobileDriver();
        Dimension scrSize = (Dimension)helper.performIgnoreException(() -> {
            return driver.manage().window().getSize();
        });
        int height = scrSize.getHeight();
        int width = scrSize.getWidth();
        LOGGER.debug("Screen height : " + height);
        LOGGER.debug("Screen width : " + width);
        Point point1 = new Point(width / 2, height / 2 - 30);
        Point point2 = new Point(width / 2, height / 10 * 3);
        Point point3 = new Point(width / 2, height / 2 + 30);
        Point point4 = new Point(width / 2, 7 * height / 10);
        switch(type) {
        case OUT:
            this.zoom(point1.getX(), point1.getY(), point2.getX(), point2.getY(), point3.getX(), point3.getY(), point4.getX(), point4.getY(), 1000);
            break;
        case IN:
            this.zoom(point2.getX(), point2.getY(), point1.getX(), point1.getY(), point4.getX(), point4.getY(), point3.getX(), point3.getY(), 1000);
        }

    }

    default void zoom(int startx1, int starty1, int endx1, int endy1, int startx2, int starty2,
        int endx2, int endy2, int duration) {
        LOGGER.debug(String.format("Zoom action will be performed with parameters : startX1 : %s ;  startY1: %s ; endX1: %s ; endY1: %s; startX2 : %s ;  startY2: %s ; endX2: %s ; endY2: %s", startx1, starty1, endx1, endy1, startx2, starty2, endx2, endy2));
        MobileDriver driver = (MobileDriver)this.castDriver();

        try {
            MultiTouchAction multiTouch = new MultiTouchAction(driver);
            TouchAction<?> tAction0 = new TouchAction(driver);
            TouchAction<?> tAction1 = new TouchAction(driver);
            PointOption<?> startPoint1 = PointOption.point(startx1, starty1);
            PointOption<?> endPoint1 = PointOption.point(endx1, endy1);
            PointOption<?> startPoint2 = PointOption.point(startx2, starty2);
            PointOption<?> endPoint2 = PointOption.point(endx2, endy2);
            WaitOptions waitOptions = WaitOptions.waitOptions(Duration.ofMillis((long)duration));
            tAction0.press(startPoint1).waitAction(waitOptions).moveTo(endPoint1).release();
            tAction1.press(startPoint2).waitAction(waitOptions).moveTo(endPoint2).release();
            multiTouch.add(tAction0).add(tAction1);
            multiTouch.perform();
            LOGGER.info("Zoom has been performed");
        } catch (Exception var19) {
            LOGGER.error("Error during zooming", var19);
        }

    }

    default WebDriver castDriver() {
        SeleniumDriver helper = DriverManager.getMobileDriver();
        return helper.getWebDriver();
    }

    public static enum Zoom {
        IN,
        OUT;

        private Zoom() {
        }
    }

    public static enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        VERTICAL,
        HORIZONTAL,
        VERTICAL_DOWN_FIRST,
        HORIZONTAL_RIGHT_FIRST;

        private Direction() {
        }
    }
}
