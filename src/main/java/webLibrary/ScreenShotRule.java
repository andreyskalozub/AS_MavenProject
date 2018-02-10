/*package webLibrary;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;



import org.apache.commons.io.FileUtils;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class ScreenShotRule extends TestWatcher {
	
     WebDriver driver;
     public final TestName name = new TestName();


    @Override
    protected void failed(Throwable e, Description description) {
    	
    	
    	
    	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    	
    	BufferedImage image;
		try {
			image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File("C:\\tmp" + "\\screenshots\\" + timeStamp + "-- " + name.getMethodName()));
		} catch (HeadlessException | AWTException | IOException e1) {
			
			e1.printStackTrace();
		}
    	

        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String currentDir = "C:\\tmp";
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	    
        try {
        	FileUtils.copyFile(scrFile,
					new File(currentDir + "\\screenshots\\" + timeStamp + "-- " + name.getMethodName() + ".png"));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

  

   
}*/