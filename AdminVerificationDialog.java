import java.awt.*;
import java.io.File;
import javax.swing.*;

/**
 * Admin verification dialog shown immediately after student uploads deposit slip.
 * Admin can verify and approve or reject the submission.
 */
public class AdminVerificationDialog extends JDialog {
    
    private boolean approved = false;
    private String rejectionReason = "";
    
    public AdminVerificationDialog(JFrame parent, Student student, String depositSlipPath) {
        super(parent, "Admin Verification Required", true); // modal dialog
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 245, 250));
        add(mainPanel);
        
        // Title
        JLabel titleLabel = new JLabel("Admin Verification", SwingConstants.CENTER);
        titleLabel.setBounds(50, 20, 400, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 70, 160));
        mainPanel.add(titleLabel);
        
        // Student details panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(null);
        detailsPanel.setBounds(50, 60, 400, 180);
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 90, 200), 1));
        mainPanel.add(detailsPanel);
        
        JLabel infoLabel = new JLabel("Student Clearance Request:");
        infoLabel.setBounds(10, 10, 380, 20);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        detailsPanel.add(infoLabel);
        
        JLabel nameLabel = new JLabel("Name: " + student.getName());
        nameLabel.setBounds(20, 40, 360, 20);
        detailsPanel.add(nameLabel);
        
        JLabel regLabel = new JLabel("Reg Number: " + student.getRegNumber());
        regLabel.setBounds(20, 65, 360, 20);
        detailsPanel.add(regLabel);
        
        JLabel emailLabel = new JLabel("Email: " + student.getEmail());
        emailLabel.setBounds(20, 90, 360, 20);
        detailsPanel.add(emailLabel);
        
        JLabel programLabel = new JLabel("Program: " + student.getProgram() + " - " + student.getLevel());
        programLabel.setBounds(20, 115, 360, 20);
        detailsPanel.add(programLabel);
        
        // Open deposit slip button
        JButton openSlipBtn = new JButton("Open Deposit Slip");
        openSlipBtn.setBounds(100, 145, 200, 30);
        openSlipBtn.setBackground(new Color(0, 120, 200));
        openSlipBtn.setForeground(Color.WHITE);
        openSlipBtn.setFont(new Font("Arial", Font.BOLD, 12));
        openSlipBtn.setFocusPainted(false);
        
        openSlipBtn.addActionListener(e -> {
            try {
                File pdfFile = new File(depositSlipPath);
                if (pdfFile.exists() && Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    JOptionPane.showMessageDialog(this, "Cannot open file: " + depositSlipPath);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage());
            }
        });
        detailsPanel.add(openSlipBtn);
        
        // Action buttons
        JButton verifyBtn = new JButton("Verify and Proceed") {
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
        verifyBtn.setBounds(80, 270, 160, 40);
        verifyBtn.setBackground(new Color(0, 150, 80));
        verifyBtn.setForeground(Color.WHITE);
        verifyBtn.setFont(new Font("Arial", Font.BOLD, 14));
        verifyBtn.setFocusPainted(false);
        verifyBtn.setBorderPainted(false);
        verifyBtn.setContentAreaFilled(false);
        verifyBtn.setOpaque(false);
        
        verifyBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verifyBtn.setBackground(new Color(0, 180, 100));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verifyBtn.setBackground(new Color(0, 150, 80));
            }
        });
        
        verifyBtn.addActionListener(e -> {
            approved = true;
            dispose();
        });
        mainPanel.add(verifyBtn);
        
        JButton rejectBtn = new JButton("Reject") {
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
        rejectBtn.setBounds(260, 270, 160, 40);
        rejectBtn.setBackground(new Color(200, 50, 50));
        rejectBtn.setForeground(Color.WHITE);
        rejectBtn.setFont(new Font("Arial", Font.BOLD, 14));
        rejectBtn.setFocusPainted(false);
        rejectBtn.setBorderPainted(false);
        rejectBtn.setContentAreaFilled(false);
        rejectBtn.setOpaque(false);
        
        rejectBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                rejectBtn.setBackground(new Color(230, 70, 70));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                rejectBtn.setBackground(new Color(200, 50, 50));
            }
        });
        
        rejectBtn.addActionListener(e -> {
            String reason = JOptionPane.showInputDialog(this, 
                "Enter rejection reason (optional):", 
                "Reject Clearance", 
                JOptionPane.QUESTION_MESSAGE);
            if (reason != null) { // User didn't cancel
                rejectionReason = reason.trim();
                approved = false;
                dispose();
            }
        });
        mainPanel.add(rejectBtn);
    }
    
    public boolean isApproved() {
        return approved;
    }
    
    public String getRejectionReason() {
        return rejectionReason;
    }
}
