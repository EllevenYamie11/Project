import java.awt.*;
import java.net.URI;
import javax.swing.*;

public class SuccessPage extends JFrame {

    private final Student student;

    public SuccessPage(Student student) {

        this.student = student;

        setTitle("Fee Clearance Successful");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(230, 240, 255));
        add(panel);

        // University Logo
        // Load a logo shown on the success page. Change the filename below
        // to use a different image in the `assets` folder (e.g. assets/logo.png).
        ImageIcon logoIcon = new ImageIcon("assets/logo.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setBounds(180, 20, 100, 80);
        panel.add(logoLabel);

        // Title
        JLabel title = new JLabel("Fee Cleared Successfully!", SwingConstants.CENTER);
        title.setBounds(50, 110, 350, 30);
        title.setForeground(new Color(0, 120, 60));
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title);

        // Subtitle
        JLabel subtitle = new JLabel("You may now proceed to registration.", SwingConstants.CENTER);
        subtitle.setBounds(50, 150, 350, 25);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(new Color(0, 70, 160));
        panel.add(subtitle);

        // Logout Button to return to the first page (login)
        // Clicking this disposes the success window and opens `LoginPage`.
        JButton logoutBtn = new JButton("Log Out") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        logoutBtn.setBounds(75, 220, 140, 45);
        logoutBtn.setBackground(new Color(0, 90, 200));
        logoutBtn.setForeground(Color.white);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setOpaque(false);

        // Hover effect
        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(new Color(0, 120, 230));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(new Color(0, 90, 200));
            }
        });

        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Thank you, " + student.getName() + "!");
            dispose();
            new LoginPage().setVisible(true); // Redirect back to the first page
        });

        panel.add(logoutBtn);

        // Proceed to Registration button
        // This button opens the external registration site in the default
        // browser. Change the URI string below to point to a different site.
        JButton proceedBtn = new JButton("Proceed to Registration") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        proceedBtn.setBounds(235, 220, 140, 45);
        proceedBtn.setBackground(new Color(0, 150, 100));
        proceedBtn.setForeground(Color.white);
        proceedBtn.setFont(new Font("Arial", Font.BOLD, 13));
        proceedBtn.setFocusPainted(false);
        proceedBtn.setBorderPainted(false);
        proceedBtn.setContentAreaFilled(false);
        proceedBtn.setOpaque(false);

        proceedBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                proceedBtn.setBackground(new Color(0, 180, 120));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                proceedBtn.setBackground(new Color(0, 150, 100));
            }
        });

        proceedBtn.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://sms.mzuni.ac.mw"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Unable to open registration page.");
            }
        });

        panel.add(proceedBtn);
    }
}
