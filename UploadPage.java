import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class UploadPage extends JFrame {

    private Student student;
    private JLabel fileNameLabel;
    private File selectedFile = null;

    public UploadPage(Student student) {

        this.student = student;

        setTitle("Upload Deposit Slip");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(230, 240, 255));
        add(panel);

        // University Logo
        // Logo image used on this page. To change the image, replace
        // `assets/slip.jpg` or update the string below to point to another file.
        ImageIcon logoIcon = new ImageIcon("assets/slip.jpg");
        Image logoImage = logoIcon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setBounds(200, 20, 100, 90);
        panel.add(logoLabel);

        // Title
        JLabel title = new JLabel("Student Fee Clearance System", SwingConstants.CENTER);
        title.setBounds(80, 110, 350, 30);
        title.setForeground(new Color(0, 70, 160));
        title.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(title);

        // Upload Section Box
        JPanel box = new JPanel();
        box.setBackground(Color.white);
        box.setBounds(80, 160, 340, 230);
        box.setBorder(BorderFactory.createLineBorder(new Color(0, 90, 200), 1));
        box.setLayout(null);
        panel.add(box);

        JLabel uploadLabel = new JLabel("Upload Your Deposit Slip", SwingConstants.CENTER);
        uploadLabel.setBounds(20, 10, 300, 30);
        uploadLabel.setFont(new Font("Arial", Font.BOLD, 15));
        uploadLabel.setForeground(new Color(0, 90, 200));
        box.add(uploadLabel);

        // File name display
        JLabel selectLabel = new JLabel("Selected File:");
        selectLabel.setBounds(20, 60, 120, 20);
        box.add(selectLabel);

        fileNameLabel = new JLabel("No file chosen");
        fileNameLabel.setBounds(120, 60, 200, 20);
        box.add(fileNameLabel);

        // Choose File Button
        // Opens a JFileChooser so the student can select their deposit slip image/PDF.
        // The selected filename is displayed but the file itself is not copied anywhere
        // by this code (you can extend it to actually store the uploaded file).
        JButton chooseBtn = new JButton("Choose File") {
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
        chooseBtn.setBounds(90, 100, 160, 35);
        chooseBtn.setBackground(new Color(0, 90, 200));
        chooseBtn.setForeground(Color.white);
        chooseBtn.setFont(new Font("Arial", Font.BOLD, 14));
        chooseBtn.setFocusPainted(false);
        chooseBtn.setBorderPainted(false);
        chooseBtn.setContentAreaFilled(false);
        chooseBtn.setOpaque(false);

        chooseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                chooseBtn.setBackground(new Color(0, 120, 230));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                chooseBtn.setBackground(new Color(0, 90, 200));
            }
        });

        chooseBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            // Filter to show only PDF files
            javax.swing.filechooser.FileNameExtensionFilter pdfFilter = 
                new javax.swing.filechooser.FileNameExtensionFilter("PDF Files", "pdf");
            chooser.setFileFilter(pdfFilter);
            chooser.setAcceptAllFileFilterUsed(false);
            int result = chooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                fileNameLabel.setText(selectedFile.getName());
            }
        });

        box.add(chooseBtn);

        // Upload Button
        JButton uploadBtn = new JButton("Upload & Verify Slip") {
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
        uploadBtn.setBounds(90, 155, 160, 35);
        uploadBtn.setBackground(new Color(0, 150, 100));
        uploadBtn.setForeground(Color.white);
        uploadBtn.setFont(new Font("Arial", Font.BOLD, 14));
        uploadBtn.setFocusPainted(false);
        uploadBtn.setBorderPainted(false);
        uploadBtn.setContentAreaFilled(false);
        uploadBtn.setOpaque(false);

        uploadBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                uploadBtn.setBackground(new Color(0, 180, 120));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                uploadBtn.setBackground(new Color(0, 150, 100));
            }
        });

        uploadBtn.addActionListener(e -> {
            handleUpload();
        });

        box.add(uploadBtn);
    }

    /**
     * Handle the upload process: validate file, check if already cleared,
     * store the PDF in clearance folder, and record in clearance.csv
     */
    private void handleUpload() {
        // Validate file selection
        if (selectedFile == null || fileNameLabel.getText().equals("No file chosen")) {
            JOptionPane.showMessageDialog(this, "Please upload a file.");
            return;
        }

        // Validate file is PDF
        if (!selectedFile.getName().toLowerCase().endsWith(".pdf")) {
            JOptionPane.showMessageDialog(this, "Please upload a PDF file.");
            return;
        }

        // Validate file size (50KB to 10MB)
        long fileSize = selectedFile.length();
        long minSize = 50 * 1024;    // 50KB
        long maxSize = 10 * 1024 * 1024;  // 10MB

        if (fileSize < minSize) {
            JOptionPane.showMessageDialog(this, "File is too small. Minimum size is 50KB.");
            return;
        }
        if (fileSize > maxSize) {
            JOptionPane.showMessageDialog(this, "File is too large. Maximum size is 10MB.");
            return;
        }

        // Heuristic check: block if filename does not look like a deposit slip.
        // This reduces accidental uploads of unrelated PDFs (e.g., notes).
        if (!isLikelyDepositSlip(selectedFile.getName())) {
            JOptionPane.showMessageDialog(this,
                    "This does not look like a deposit slip. Please upload a valid deposit slip PDF.");
            return;
        }

        // Check if student is already in clearance list
        if (isStudentAlreadyCleared()) {
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "You have already cleared your fees. Proceed to registration?",
                    "Already Cleared",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Proceed", "Cancel"},
                    "Proceed");

            if (choice == JOptionPane.YES_OPTION) {
                // Open success screen so user can proceed to registration (or open directly)
                dispose();
                new SuccessPage(student).setVisible(true);
                openRegistrationLink();
            }
            return;
        }

        // Create clearance folder if it doesn't exist
        String clearanceDir = System.getProperty("user.dir") + File.separator + "Data" + 
                            File.separator + "Clearance";
        File clearanceFolderFile = new File(clearanceDir);
        if (!clearanceFolderFile.exists()) {
            clearanceFolderFile.mkdirs();
        }

        try {
            // Generate unique filename with student reg number and timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = student.getRegNumber() + "_" + timestamp + ".pdf";
            String destinationPath = clearanceDir + File.separator + fileName;

            // Copy the PDF file to clearance folder
            Files.copy(selectedFile.toPath(), Paths.get(destinationPath), 
                      StandardCopyOption.REPLACE_EXISTING);

            // Show pending message to student
            JOptionPane.showMessageDialog(this,
                    "Your deposit slip has been submitted.\nPlease wait while approval is underway...",
                    "Pending Approval",
                    JOptionPane.INFORMATION_MESSAGE);

            // Show admin verification dialog
            AdminVerificationDialog adminDialog = new AdminVerificationDialog(this, student, destinationPath);
            adminDialog.setVisible(true);
            
            // Check admin decision
            if (!adminDialog.isApproved()) {
                // Rejected - delete the uploaded file and don't record clearance
                new File(destinationPath).delete();
                String reason = adminDialog.getRejectionReason();
                String message = reason.isEmpty() 
                    ? "Your clearance request was rejected by admin." 
                    : "Your clearance request was rejected: " + reason;
                JOptionPane.showMessageDialog(this, message);
                return;
            }

            // Record in clearance CSV with Approved status
            recordClearance(fileName, destinationPath, "Approved");

            JOptionPane.showMessageDialog(this,
                    "Deposit slip uploaded and verified successfully!");

            dispose();
            new SuccessPage(student).setVisible(true);

        } catch (IOException ex) {
            System.err.println("Error saving deposit slip: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error saving deposit slip!");
        }
    }

    // Simple filename-based heuristic: looks for common deposit slip keywords
    // in the filename. Not a guarantee, but catches obvious non-slip uploads.
    private boolean isLikelyDepositSlip(String fileName) {
        String lower = fileName.toLowerCase();
        return lower.contains("slip") || lower.contains("deposit") || lower.contains("receipt") || lower.contains("bank");
    }

    /**
     * Check if student email already exists in clearance.csv
     */
    private boolean isStudentAlreadyCleared() {
        String clearanceFile = System.getProperty("user.dir") + "/Data/clearance.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(clearanceFile))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[0].equalsIgnoreCase(student.getEmail())) {
                    return true;
                }
            }
        } catch (IOException ex) {
            // File doesn't exist yet, so not cleared
            return false;
        }
        return false;
    }

    /**
     * Record clearance in CSV with deposit slip filename and path
     */
    private void recordClearance(String fileName, String filePath, String status) throws IOException {
        String clearanceFilePath = System.getProperty("user.dir") + "/Data/clearance.csv";
        File clearanceFile = new File(clearanceFilePath);
        boolean fileExists = clearanceFile.exists();

        try (FileWriter fw = new FileWriter(clearanceFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            // Add header if new file
            if (!fileExists) {
                out.println("email,reg,status,date,deposit_slip_file");
            }

            // Get today's date in ISO format
            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            // Write record: email, reg, status, date, deposit slip filename
            out.println(student.getEmail() + "," + student.getRegNumber() +
                       "," + status + "," + today + "," + fileName);
        }
    }

    // Open registration URL in default browser; safe no-op if Desktop not supported
    private void openRegistrationLink() {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new java.net.URI("https://sms.mzuni.ac.mw"));
            }
        } catch (Exception ex) {
            System.err.println("Unable to open registration page: " + ex.getMessage());
        }
    }
}
