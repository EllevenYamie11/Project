import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Start Swing on the Event Dispatch Thread (EDT). Create a small
        // splash screen and after a short delay show the Login page.
        // Change the `2000` value (milliseconds) to increase/decrease splash time.
        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen();
            splash.setVisible(true);

            Timer timer = new Timer(2000, e -> {
                splash.dispose();
                new LoginPage();
            });
            timer.setRepeats(false);
            timer.start();
        });
    }
}
