import java.sql.*;
import java.util.Scanner;
public class MySQLCrudApp {
static final String URL = "jdbc:mysql://localhost:3306/cjits";
static final String USER = "root";
static final String PASSWORD = "";
static Connection connection = null;
public static void main(String[] args) {
try {
connection = DriverManager.getConnection(URL, USER, PASSWORD);
System.out.println("Connected to the database successfully!");
Scanner scanner = new Scanner(System.in);
while (true) {
System.out.println("\nSelect an operation:");
System.out.println("1. Create a new user");
System.out.println("2. Read all users");
System.out.println("3. Update user");
System.out.println("4. Delete user");
System.out.println("5. Exit");
int choice = scanner.nextInt();
scanner.nextLine(); // Consume the newline character
switch (choice) {
case 1:
createUser(scanner);
break;
case 2:
readUsers();
break;
case 3:
updateUser(scanner);
break;
case 4:
deleteUser(scanner);
break;
case 5:
System.out.println("Exiting...");
connection.close();
System.exit(0);
default:
System.out.println("Invalid choice. Please try again.");
}
}
} catch (SQLException e) {
e.printStackTrace();
}
}
// Create a new user in the database
private static void createUser(Scanner scanner) {
try {
System.out.println("Enter name:");
String name = scanner.nextLine();
System.out.println("Enter email:");
String email = scanner.nextLine();
System.out.println("Enter age:");
int age = scanner.nextInt();
scanner.nextLine(); // Consume the newline character
String sql = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";
PreparedStatement statement = connection.prepareStatement(sql);
statement.setString(1, name);
statement.setString(2, email);
statement.setInt(3, age);
int rowsInserted = statement.executeUpdate();
if (rowsInserted > 0) {
System.out.println("A new user was inserted successfully!");
}
} catch (SQLException e) {
e.printStackTrace();
}
}
// Read all users from the database
private static void readUsers() {
try {
String sql = "SELECT * FROM users";
Statement statement = connection.createStatement();
ResultSet resultSet = statement.executeQuery(sql);
while (resultSet.next()) {
int id = resultSet.getInt("id");
String name = resultSet.getString("name");
String email = resultSet.getString("email");
int age = resultSet.getInt("age");
System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Age: " + age);
}
} catch (SQLException e) {
e.printStackTrace();
}
}
// Update a user in the database
private static void updateUser(Scanner scanner) {
try {
System.out.println("Enter user ID to update:");
int userId = scanner.nextInt();
scanner.nextLine(); // Consume the newline character
System.out.println("Enter new name:");
String name = scanner.nextLine();
System.out.println("Enter new email:");
String email = scanner.nextLine();
System.out.println("Enter new age:");
int age = scanner.nextInt();
scanner.nextLine(); // Consume the newline character
String sql = "UPDATE users SET name = ?, email = ?, age = ? WHERE id = ?";
PreparedStatement statement = connection.prepareStatement(sql);
statement.setString(1, name);
statement.setString(2, email);
statement.setInt(3, age);
statement.setInt(4, userId);
int rowsUpdated = statement.executeUpdate();
if (rowsUpdated > 0) {
System.out.println("An existing user was updated successfully!");
}
} catch (SQLException e) {
e.printStackTrace();
}
}
// Delete a user from the database
private static void deleteUser(Scanner scanner) {
try {
System.out.println("Enter user ID to delete:");
int userId = scanner.nextInt();
scanner.nextLine(); // Consume the newline character
String sql = "DELETE FROM users WHERE id = ?";
PreparedStatement statement = connection.prepareStatement(sql);
statement.setInt(1, userId);
int rowsDeleted = statement.executeUpdate();
if (rowsDeleted > 0) {
System.out.println("A user was deleted successfully!");
}
} catch (SQLException e) {
e.printStackTrace();
}
}
}