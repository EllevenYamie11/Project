/**
 * Simple `Student` model class used to pass user information between
 * screens (LoginPage -> UploadPage -> SuccessPage). Fields map to the
 * columns in Data/students.csv: name, reg, email, password, program, level.
 *
 * To add additional properties (e.g. phone number), add fields here and
 * update StudentDatabase.verifyLogin to populate them from the CSV.
 */
public class Student {

    // Basic student properties (match CSV column ordering)
    private String name;
    private String regNumber;
    private String email;
    private String password;
    private String program;
    private String level;

    public Student(String name, String regNumber, String email,
                   String password, String program, String level) {
        this.name = name;
        this.regNumber = regNumber;
        this.email = email;
        this.password = password;
        this.program = program;
        this.level = level;
    }

    public String getName() { return name; }
    public String getRegNumber() { return regNumber; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getProgram() { return program; }
    public String getLevel() { return level; }
}
