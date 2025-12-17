import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SplashScreen extends JWindow {

    public SplashScreen() {
        // The splash screen displays a logo while the app starts. Change the
        // filename below to use a different splash image from the `assets`
        // folder. Size controls the splash window dimension.
        String assetsPath = System.getProperty("user.dir") + File.separator + "assets" + File.separator;
        Image logoImage = safeLoadAndScaleImage(assetsPath + "mzuzu-university-logo.jpg", 200, 200);
        JLabel label = logoImage != null
            ? new JLabel(new ImageIcon(logoImage))
            : new JLabel("Student Fee Clearance", SwingConstants.CENTER);
        add(label);
        setSize(200, 200); // change this to make the splash larger/smaller
        setLocationRelativeTo(null);
    }

    private Image safeLoadAndScaleImage(String path, int w, int h) {
        try {
            File f = new File(path);
            if (!f.exists()) return null;
            BufferedImage img = ImageIO.read(f);
            if (img == null) return null;
            return img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        } catch (Exception ex) {
            System.err.println("Splash image load failed: " + path + " -> " + ex.getMessage());
            return null;
        }
    }
}
