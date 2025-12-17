import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StudentDatabase {

    private final String filePath;

    public StudentDatabase(String filePath) {
        this.filePath = filePath;
    }

    public Student verifyLogin(String email, String password) {
        // Opens the CSV file located at `filePath` and searches for a matching
        // email/password pair. The CSV is expected to have a header row and
        // the following columns (in order): name,reg,email,password,program,level
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) continue;

                String name = data[0];
                String reg = data[1];
                String fileEmail = data[2];
                String filePassword = data[3];
                String program = data[4];
                String level = data[5];

                if (fileEmail.equalsIgnoreCase(email) && filePassword.equals(password)) {
                    return new Student(name, reg, fileEmail, filePassword, program, level);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading student database: " + e.getMessage());
        }
        return null;
    }
}
