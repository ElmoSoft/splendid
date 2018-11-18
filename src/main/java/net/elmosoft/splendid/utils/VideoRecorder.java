package net.elmosoft.splendid.utils;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;

import net.elmosoft.splendid.driver.seleniumdriver.SeleniumDriver;
import org.apache.log4j.Logger;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public class VideoRecorder {
 
	private static String fileSeparator = System.getProperty("file.separator");
	
	private static final String VIDEOS = "videos";
 
    private ScreenRecorder screenRecorder;
    
    final File dir = new File("");
    private static final Logger LOGGER = Logger
            .getLogger(VideoRecorder.class);
 
    public void startRecording(SeleniumDriver driver, final String name) {
 
        try {
            GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment().getDefaultScreenDevice()
                .getDefaultConfiguration();
            File videoDirectory = new File("test-output"+fileSeparator+"html");;
            File directory = new File(  videoDirectory,VIDEOS + fileSeparator);
            Point point = driver.getWebDriver().manage().window().getPosition();
            Dimension dimension = driver.getWebDriver().manage().window().getSize();
 
            Rectangle rectangle = new Rectangle(point.x, point.y,
                dimension.width, dimension.height);
 
            this.screenRecorder = new SpecializedScreenRecorder(gc, rectangle,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, 
                    MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
                    ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                    CompressorNameKey,
                    ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey,
                    24, FrameRateKey, Rational.valueOf(15), QualityKey,
                    1.0f, KeyFrameIntervalKey, 15 * 60), new Format(
                    MediaTypeKey, MediaType.VIDEO, EncodingKey,
                    "black", FrameRateKey, Rational.valueOf(30)), null,
                    directory,name);
 
            this.screenRecorder.start();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
 
        try {
            this.screenRecorder.stop();



            //            if (recordName != null) {
//                SimpleDateFormat dateFormat = new SimpleDateFormat(
//                    "yyyy-MM-dd HH.mm.ss");
//                File newFileName = new File(String.format("%s%s %s.avi",
//                		getFileDir(), recordName,
//                    dateFormat.format(new Date())));
// 
//                this.screenRecorder.getCreatedMovieFiles().get(0)
//                    .renameTo(newFileName);
//            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
 
}