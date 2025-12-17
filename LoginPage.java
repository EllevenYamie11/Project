import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;

public class LoginPage extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    private StudentDatabase database;

    // Add small serial UID
    private static final long serialVersionUID = 1L;

    /*
     * NOTES / QUICK CHANGES
     * - To change where the app looks for images, edit `assetsPath` in the constructor.
     * - To change where CSV data is read from, edit `dataPath` in the constructor.
     * - Background image file: assets/background.png (change filename here or replace file).
     * - Logo file: assets/mzuzu-university-logo.jpg (change filename here or replace file).
     * - The UI is created using GridBagLayout for responsive centering; change sizes on
     *   the setSize(...) call near the top of the constructor.
     */
    public LoginPage() {
        // ---------- Configuration: paths and resources ----------
        // `assetsPath` points to the folder where images (background, logos)
        // and other static files are expected. Change the folder name or
        // filenames below to use different images.
        // Example: to change the background, replace assets/background.png
        // or edit the string passed to `safeLoadImage` below.
        String assetsPath = System.getProperty("user.dir") + File.separator + "assets" + File.separator;

        // `dataPath` points to the folder where CSV data files (student list,
        // clearance records) are stored. Change this path if you keep data
        // somewhere else (e.g. a server or a different folder).
        String dataPath = System.getProperty("user.dir") + File.separator + "Data" + File.separator;

        // Path to your CSV file (use dataPath)
        database = new StudentDatabase(dataPath + "students.csv");

        setTitle("Student Fee Clearance System");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centered on screen
        setResizable(true);

        // ---------- Background / content pane ----------
        // The content pane paints a background image if present. To change
        // the background image, replace the file at assets/background.png
        // or change the filename passed to `safeLoadImage` below.
        // If the image is missing the panel simply uses a plain background.
        // This panel uses GridBagLayout so the central form stays centered
        // when the window is resized.
        // Create content pane with background image
        JPanel contentPane = new JPanel() {
            private BufferedImage backgroundImage = safeLoadImage(assetsPath + "background.png");

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        contentPane.setLayout(new GridBagLayout());
        setContentPane(contentPane);

        // Use GridBagLayout on the frame to keep content centered when resizing
        getContentPane().setLayout(new GridBagLayout());
        JPanel formPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(230, 240, 255));
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2d.setColor(new Color(0, 90, 200));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Add margin around formPanel
        GridBagConstraints gbcOuter = new GridBagConstraints();
        gbcOuter.gridx = 0;
        gbcOuter.gridy = 0;
        gbcOuter.fill = GridBagConstraints.NONE;
        gbcOuter.anchor = GridBagConstraints.CENTER;
        gbcOuter.weightx = 1.0;
        gbcOuter.weighty = 1.0;
        getContentPane().add(formPanel, gbcOuter);

        // ---------- Logo / Title ----------
        // Load the university logo from `assetsPath`. Replace this image
        // file if you want a different logo or change the filename here.
        Image logoImage = safeLoadAndScaleImage(assetsPath + "mzuzu-university-logo.jpg", 100, 100);
        JLabel logoLabel;
        if (logoImage != null) {
            logoLabel = new JLabel(new ImageIcon(logoImage));
        } else {
            logoLabel = new JLabel("University");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 14));
            logoLabel.setForeground(new Color(0, 70, 160));
        }
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(logoLabel, gbc);

        // System Title
        JLabel titleLabel = new JLabel ("Student Fee Clearance System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 70, 160));
        gbc.gridy++;
        formPanel.add(titleLabel, gbc);

        // Email Label + Field (stacked vertically)
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(emailLabel, gbc);
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(260, 28));
        gbc.gridy++;
        formPanel.add(emailField, gbc);

        // Password Label + Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(passwordLabel, gbc);
        
        // Password Field with Show/Hide Button inside
        JPanel passwordPanel = new JPanel(new BorderLayout(0, 0)) {
            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(new Color(200, 200, 200));
                g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };
        passwordPanel.setOpaque(true);
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 2));
        
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(220, 24));
        passwordField.setEchoChar('•'); // start hidden
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        JButton toggleButton = new JButton();
        toggleButton.setPreferredSize(new Dimension(35, 24));
        toggleButton.setFocusPainted(false);
        toggleButton.setBorderPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setMargin(new Insets(0, 0, 0, 0));
        
        // ---------- Password toggle icons ----------
        // Small icon images used in the password toggle button. If these
        // files do not exist the toggle falls back to a simple text marker.
        // To change the icons, replace these files in the `assets` folder
        // or update the filenames below.
        Image hideImage = safeLoadAndScaleImage(assetsPath + "hide.jpeg", 20, 20);
        Image showImage = safeLoadAndScaleImage(assetsPath + "show.jpeg", 20, 20);
        if (hideImage != null) {
            toggleButton.setIcon(new ImageIcon(hideImage)); // start with hide icon
        } else {
            toggleButton.setText("•");
        }

        toggleButton.addActionListener(e -> {
            if (passwordField.getEchoChar() == '•') {
                // Show password
                passwordField.setEchoChar((char) 0);
                if (showImage != null) toggleButton.setIcon(new ImageIcon(showImage));
            } else {
                // Hide password
                passwordField.setEchoChar('•');
                if (hideImage != null) toggleButton.setIcon(new ImageIcon(hideImage));
            }
        });

        // place toggle button at the end, inside the passwordPanel
        passwordPanel.add(toggleButton, BorderLayout.EAST);
        
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(passwordPanel, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 90, 200));
        loginButton.setForeground(Color.white);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setPreferredSize(new Dimension(260, 36));
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(loginButton, gbc);

        // Hover Effect
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(0, 120, 230));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(0, 90, 200));
            }
        });

        // Login Action
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim().toLowerCase();
            String password = new String(passwordField.getPassword()).trim().toLowerCase();

            Student student = database.verifyLogin(email, password);

            if (student != null) {
                JOptionPane.showMessageDialog(this, "Login successful!\n"+" Welcome "+student.getName());
                dispose();
                new UploadPage (student).setVisible(true);
            
            } 
            else if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both email and password.");
            }
            else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.");
            }

        });

        // show the window
        setVisible(true);
    }
    
    // Helper: safely load an image from disk and scale it.
    // Returns `null` when the file is missing or cannot be read so callers
    // can fall back to text labels or default styling.
    private Image safeLoadAndScaleImage(String path, int w, int h) {
        try {
            File f = new File(path);
            if (!f.exists()) return null;
            BufferedImage img = javax.imageio.ImageIO.read(f);
            if (img == null) return null;
            return img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        } catch (IOException ex) {
            System.err.println("Image load failed: " + path + " -> " + ex.getMessage());
            return null;
        }
    }

    // Helper: load a raw BufferedImage from disk (used for full-size
    // background painting). Returns null if the file is not available.
    private BufferedImage safeLoadImage(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) return null;
            return javax.imageio.ImageIO.read(f);
        } catch (IOException ex) {
            System.err.println("Image load failed: " + path + " -> " + ex.getMessage());
            return null;
        }
    }

    // Add main to run on EDT
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginPage();
        });
    }

}
