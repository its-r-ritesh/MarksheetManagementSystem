package in.sterling.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import in.sterling.Main.MarksheetManagementSystem;
import in.sterling.TableData.MarksheetTableData;

/**
 * This class provides methods to perform CRUD operations on the 'marksheet'
 * table in the database.
 */
public class MarksheetDao {
	Scanner sc = new Scanner(System.in);
	private Connection con;

	/**
	 * Constructor to initialize the MarksheetDao with a database connection.
	 *
	 * @param con The database connection to be used by the DAO.
	 */
	public MarksheetDao(Connection con) {
		this.con = con;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Adds a new marksheet record to the database.
	 *
	 * @param mtd The MarksheetTableData object containing the marksheet data to be
	 *            added.
	 * @return true if the record is successfully added, false otherwise.
	 */
	public boolean add(MarksheetTableData mtd) {
		boolean f = false;
		String query = "INSERT INTO marksheet(rollno,name,math,physics,chemistry,emailID,gender,dob) values(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = this.con.prepareStatement(query);

			pstmt.setString(1, mtd.getRollNo());
			pstmt.setString(2, mtd.getName());
			pstmt.setInt(3, mtd.getMaths());
			pstmt.setInt(4, mtd.getPhysics());
			pstmt.setInt(5, mtd.getChemistry());
			pstmt.setString(6, mtd.getEmailID());
			pstmt.setString(7, String.valueOf(mtd.getGendar()));
			pstmt.setDate(8, mtd.getDob());

			pstmt.executeUpdate();
			f = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Please enter other input..");
		}
		return f;
	}

//---------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Deletes a marksheet record from the database by roll number.
	 *
	 * @param rollNo The roll number of the marksheet record to be deleted.
	 * @return true if the record is successfully deleted, false otherwise.
	 */
	public boolean deleteByRollNo(String rollNo) {
		boolean f = false;
		try {
			String sql = "DELETE FROM marksheet WHERE rollNo ='" + rollNo + "'";
			PreparedStatement pstmt = this.con.prepareStatement(sql);
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				f = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return f;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Deletes a marksheet record from the database by email ID.
	 *
	 * @param emailID The email ID of the marksheet record to be deleted.
	 * @return true if the record is successfully deleted, false otherwise.
	 */
	public boolean deleteByemailID(String emailID) {
		boolean f = false;
		try {
			String query = "DELETE FROM marksheet WHERE emailID ='" + emailID + "'";
			PreparedStatement pstmt = this.con.prepareStatement(query);
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				f = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return f;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Updates a specific column of a marksheet record in the database.
	 *
	 * @param columnName The name of the column to be updated.
	 * @return true if the record is successfully updated, false otherwise.
	 */
	public boolean update(String columnName) {
		boolean f = false;
		String rollNo = null;
		String name = "";
		boolean isValid = true;
		while (isValid) {
			System.out.println("Please enter Roll Number:- ");
			rollNo = sc.next().trim();
			if (rollNo.length() != 12) {
				System.out.println("Enter valid Roll Number...\n");
			} else {
				isValid = false;
			}
		}
		try {
			String query = "SELECT * FROM marksheet WHERE rollNo = '" + rollNo + "'";
			Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(query);

			if (columnName.equals("name")) {
				boolean b = false;

				while (!b) {
					System.out.print("\nEnter student's new name: ");
					name = sc.nextLine().toUpperCase();
					sc.nextLine();
					if (!MarksheetManagementSystem.isValidName(name) || (name == "")) {
						System.out.println("\nPlease enter correct name..");
					} else {
						rs.next();
						rs.updateString(columnName, name);
						rs.updateRow();
						b = true;
					}
				}

			} else if (columnName.equals("emailID")) {
				boolean b = false;
				while (!b) {
					System.out.println("Please new enter Email ID:- ");
					String emailID = sc.next().trim().toLowerCase();
					if ((emailID.contains("@") && emailID.contains("."))) {
						rs.next();
						rs.updateString(columnName, emailID);
						rs.updateRow();
						b = true;
						;
					} else {
						System.err.println("Warning: Please enter a valid Email ID.");
					}
				}
			} else if (columnName.equals("dob")) {
				System.out.println("Enter your new date of birth (yyyy-MM-dd): ");
				String dobString = sc.next();
				java.sql.Date dob = null;
				try {
					java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dobString);
					dob = new java.sql.Date(utilDate.getTime());
				} catch (ParseException e) {
					System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
				}
				rs.next();
				rs.updateDate(columnName, dob);
				rs.updateRow();
			} else if (columnName.equals("gender")) {
				char gender = 0;
				boolean gndr=false;
				while (!gndr) {
				System.out.println("Enter Gender Male for(M)and Female(F): ");
				
//					byteInput = System.in.read();
//					gender = Character.toUpperCase((char) byteInput);
					gender = sc.next().toUpperCase().charAt(0);
					
					if(!(gender=='M'||gender=='F')) {
						System.err.println("Warning: Please enter a valid Gendar.");
					
					}
					else {
						rs.next();
						rs.updateString(columnName, String.valueOf(gender));
						rs.updateRow();
						gndr=true;
					}
				
				}
				
			} else if (columnName.equals("math")) {
				boolean b = false;
				int math = 0;
				while (!b) {
					System.out.println("Enter new Math's Number: ");
					math = sc.nextInt();
					if (!(math < 0 || math > 100)) {
						rs.next();
						rs.updateInt(columnName, math);
						rs.updateRow();
						b = true;
						;
					} else {
						System.err.println("Warning: Please enter a valid number.");
					}
				}
			} else if (columnName.equals("physics")) {
				boolean b = false;
				int physics = 0;
				while (!b) {
					System.out.println("Enter new physics Number: ");
					physics = sc.nextInt();
					if (!(physics < 0 || physics > 100)) {
						rs.next();
						rs.updateInt(columnName, physics);
						rs.updateRow();
						b = true;
						;
					} else {
						System.err.println("Warning: Please enter a valid number .");
					}
				}
			} else if (columnName.equals("chemistry")) {
				boolean b = false;
				int chemistry = 0;
				while (!b) {
					System.out.println("Enter new chemistry Number: ");
					chemistry = sc.nextInt();
					if (!(chemistry < 0 || chemistry > 100)) {
						rs.next();
						rs.updateInt(columnName, chemistry);
						rs.updateRow();
						b = true;
						;
					} else {
						System.err.println("Warning: Please enter a valid number .");
					}
				}
			}

			f = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return f;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Updates all columns of a marksheet record in the database.
	 *
	 * @param mtd The MarksheetTableData object containing the updated marksheet
	 *            data.
	 * @return true if the record is successfully updated, false otherwise.
	 */
	public boolean updateAll(MarksheetTableData mtd) {
		System.out.println("\n\nPlease enter Old Roll Number:- ");
		String rollNo = sc.next().trim();
		boolean f = false;
		String query = "UPDATE marksheet SET rollNo=?, name = ?, math = ?, physics = ?, chemistry = ?, emailID = ?, gender = ?, dob = ? WHERE rollNo = '"
				+ rollNo + "'";
		try {
			PreparedStatement pstmt = this.con.prepareStatement(query);

			pstmt.setString(1, mtd.getRollNo());
			pstmt.setString(2, mtd.getName());
			pstmt.setInt(3, mtd.getMaths());
			pstmt.setInt(4, mtd.getPhysics());
			pstmt.setInt(5, mtd.getChemistry());
			pstmt.setString(6, mtd.getEmailID());
			pstmt.setString(7, String.valueOf(mtd.getGendar()));
			pstmt.setDate(8, mtd.getDob());

			pstmt.executeUpdate();
			f = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return f;
	}

//---------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves the merit list of students from the database.
	 *
	 * @return a LinkedHashSet containing the MarksheetTableData objects of students
	 *         in the merit list.
	 */
	public LinkedHashSet<MarksheetTableData> getMeritList() {
		LinkedHashSet<MarksheetTableData> meritList = new LinkedHashSet<>();
		String query = "SELECT * FROM marksheet ORDER BY (math+physics+chemistry) DESC";
		try {
			PreparedStatement pstmt = this.con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				MarksheetTableData mtd = new MarksheetTableData(rs.getString("rollNo"), rs.getString("name"),
						rs.getInt("math"), rs.getInt("physics"), rs.getInt("chemistry"), rs.getString("emailID"),
						(char) rs.getString("gender").charAt(0), rs.getDate("dob"));
				meritList.add(mtd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return meritList;
	}

//--------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Deletes all records from the marksheet table.
	 *
	 * @return true if all records were successfully deleted, false otherwise
	 */
	public boolean deleteAll() {
		boolean f = false;
		String query = "DELETE FROM marksheet";

		try {
			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.executeUpdate();
			f = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return f;
	}

//---------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves all records from the marksheet table.
	 *
	 * @return a set of MarksheetTableData objects representing all records in the
	 *         table
	 */
	public Set<MarksheetTableData> getAll() {
		Set<MarksheetTableData> records = new HashSet<>();
		String query = "SELECT * FROM marksheet";

		try {
			PreparedStatement stmt = this.con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				MarksheetTableData mtd = new MarksheetTableData(rs.getString("rollNo"), rs.getString("name"),
						rs.getInt("math"), rs.getInt("physics"), rs.getInt("chemistry"), rs.getString("emailID"),
						(char) rs.getString("gender").charAt(0), rs.getDate("dob"));
				records.add(mtd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

//------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves records from the marksheet table for a specific roll number.
	 *
	 * @param rollNo the roll number for which to retrieve records
	 * @return a list of MarksheetTableData objects representing the records for the
	 *         specified roll number
	 */
	public ArrayList<MarksheetTableData> get(String rollNo) {
		ArrayList<MarksheetTableData> records = new ArrayList<>();
		String query = "SELECT * FROM marksheet WHERE rollNo = '" + rollNo + "'";

		try {
			PreparedStatement stmt = this.con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			rs.next();
			MarksheetTableData mtd = new MarksheetTableData(rs.getString("rollNo"), rs.getString("name"),
					rs.getInt("math"), rs.getInt("physics"), rs.getInt("chemistry"), rs.getString("emailID"),
					(char) rs.getString("gender").charAt(0), rs.getDate("dob"));
			records.add(mtd);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves a list of students who have failed in at least two subject.
	 *
	 * @return a LinkedHashSet containing MarksheetTableData objects for students
	 *         who have failed
	 */
	public LinkedHashSet<MarksheetTableData> getFailedStudentsList() {
		LinkedHashSet<MarksheetTableData> records = new LinkedHashSet<>();
		String query = "SELECT * FROM marksheet";

		try {
			PreparedStatement stmt = this.con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				MarksheetTableData mtd = new MarksheetTableData(rs.getString("rollNo"), rs.getString("name"),
						rs.getInt("math"), rs.getInt("physics"), rs.getInt("chemistry"), rs.getString("emailID"),
						(char) rs.getString("gender").charAt(0), rs.getDate("dob"));
				records.add(mtd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves a list of students who are the top 10 toppers based on their total
	 * marks.
	 *
	 * @return an ArrayList containing MarksheetTableData objects for the toppers
	 */
	public ArrayList<MarksheetTableData> getToppers() {
		ArrayList<MarksheetTableData> records = new ArrayList<>();
		String query = "SELECT * FROM marksheet ORDER BY (math+physics+chemistry) DESC";

		try {
			PreparedStatement stmt = this.con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				MarksheetTableData mtd = new MarksheetTableData(rs.getString("rollNo"), rs.getString("name"),
						rs.getInt("math"), rs.getInt("physics"), rs.getInt("chemistry"), rs.getString("emailID"),
						(char) rs.getString("gender").charAt(0), rs.getDate("dob"));
				records.add(mtd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

//--------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves the details of students with the lowest total marks.
	 *
	 * @return a 2D array containing the roll number, name, and total marks of the
	 *         students with the lowest marks
	 */
	public String[][] getLowestMarkStudents() {
		String[][] result = null;
		String query = "SELECT rollNo,name, (math + physics + chemistry) AS totalMarks " + "FROM marksheet "
				+ "ORDER BY totalMarks ASC " + "LIMIT 5";

		try {
			PreparedStatement pstmt = this.con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			List<String[]> rows = new ArrayList<>();
			while (rs.next()) {

				rows.add(new String[] { rs.getString("rollNo"), rs.getString("name"), rs.getString("totalMarks") });
			}

			result = rows.toArray(new String[0][3]);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves the details of students who have passed all subjects with a minimum
	 * passing score.
	 *
	 * @return a 2D array containing the roll number, name, and total marks of the
	 *         students who have passed
	 */
	public String[][] getPassedStudents() {
		String[][] result = null;

		String query = "SELECT rollNo,name,(math + physics + chemistry) AS totalMarks  " + "FROM marksheet "
				+ "WHERE math >= 33 AND physics >= 33 AND chemistry >= 33";

		try {
			PreparedStatement pstmt = this.con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			List<String[]> rows = new ArrayList<>();
			while (rs.next()) {
				rows.add(new String[] { rs.getString("rollNo"), rs.getString("name"), rs.getString("totalMarks") });
			}

			result = rows.toArray(new String[0][3]);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves the total number of students in the marksheet.
	 *
	 * @return the total number of students
	 */
	public int numberOfStudents() {
		int count = 0;
		String query = "SELECT COUNT(*) AS count FROM marksheet";

		try {
			PreparedStatement pstmt = this.con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves the average result of the entire class.
	 *
	 * @return the average result of the entire class
	 */
	public double getAverageResultOfClass() {
		double average = 0;
		String query = "SELECT AVG(math + physics + chemistry) AS averageResult FROM marksheet";

		try {
			PreparedStatement pstmt = this.con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				average = rs.getDouble("averageResult");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return average;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves the cutoff of the entire class.
	 *
	 * @return the cutoff of the entire class
	 */
	public double getCutOff() {
		double cutOff = 0;

		String query = "SELECT AVG(totalMarks) AS cutoff " + "FROM (SELECT math + physics + chemistry AS totalMarks "
				+ "      FROM marksheet " + "      ORDER BY totalMarks DESC " + "      LIMIT 5) AS topStudents";

		try {
			PreparedStatement pstmt = this.con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				cutOff = rs.getDouble("cutoff");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cutOff;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves a list of students who have failed in one or more subjects (ATKT
	 * students).
	 *
	 * @return a list of ATKT students
	 */
	public List<MarksheetTableData> getATKTStudents() {
		List<MarksheetTableData> records = new ArrayList<>();
		String query = "SELECT * FROM marksheet";

		try {
			PreparedStatement stmt = this.con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				MarksheetTableData mtd = new MarksheetTableData(rs.getString("rollNo"), rs.getString("name"),
						rs.getInt("math"), rs.getInt("physics"), rs.getInt("chemistry"), rs.getString("emailID"),
						(char) rs.getString("gender").charAt(0), rs.getDate("dob"));
				records.add(mtd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves the number of boys who have passed (scored 33 or more in each
	 * subject).
	 *
	 * @return the number of boys who have passed
	 */
	public int getNumberOfBoysPass() {
		int count = 0;
		String query = "SELECT COUNT(*) AS count " + "FROM marksheet "
				+ "WHERE gender = 'M' AND math >= 33 AND physics >= 33 AND chemistry >= 33";

		try {
			PreparedStatement stmt = this.con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

//-------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves the number of girls who have passed (scored 33 or more in each
	 * subject).
	 *
	 * @return the number of girls who have passed
	 */
	public int getNumberOfGirlsPass() {
		int count = 0;
		String query = "SELECT COUNT(*) AS count " + "FROM marksheet "
				+ "WHERE gender = 'F' AND math >= 33 AND physics >= 33 AND chemistry >= 33";

		try {
			PreparedStatement stmt = this.con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

//-------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves the grade of a student based on their roll number.
	 *
	 * @param rollNo the roll number of the student
	 * @return the grade of the student ('S' for distinction, 'B' for first class,
	 *         'C' for second class, 'D' for third class, 'F' for fail)
	 */
	public char getGradeOfStudent(String rollNo) {
		char grade = 0; // Default grade is 'F' (Fail)

		String query = "SELECT (math + physics + chemistry)/3 AS percentage " + "FROM marksheet " + "WHERE rollNo = '"
				+ rollNo + "'";

		try {
			PreparedStatement stmt = this.con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				double percentage = rs.getDouble("percentage");
				if (percentage >= 90) {
					grade = 'A';
				} else if (percentage >= 60) {
					grade = 'B';
				} else if (percentage >= 45) {
					grade = 'C';
				} else if (percentage >= 33) {
					grade = 'D';
				} else if (percentage < 33) {
					grade = 'F';
				}
			} else {
				System.out.println("Please enter valid Roll Number");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return grade;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves a list of students who are marked as absent in any subject.
	 *
	 * @return a list of strings representing the absent students' roll numbers and
	 *         names
	 */
	public ArrayList<String> absenties() {

		ArrayList<String> absentStudents = new ArrayList<>();
		String query = "SELECT rollno, name FROM marksheet WHERE math = 0 OR physics = 0 OR chemistry = 0";

		try {
			PreparedStatement stmt = this.con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String rollno = rs.getString("rollno");
				String name = rs.getString("name");
				absentStudents.add("Roll No: " + rollno + " | Name: " + name + "\t => [Absent] ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle any errors
		}

		return absentStudents;
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves the total number of girls in the marksheet table.
	 *
	 * @return the total number of girls in the marksheet table
	 */
	public int getTotalNumberOfGirls() {
		int count = 0;
		String query = "SELECT COUNT(*) AS count " + "FROM marksheet " + "WHERE gender = 'F' ";

		try {
			PreparedStatement stmt = this.con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

//-------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves the total number of boys in the marksheet table.
	 *
	 * @return the total number of boys in the marksheet table
	 */
	public int getTotalNumberOfBoys() {
		int count = 0;
		String query = "SELECT COUNT(*) AS count " + "FROM marksheet " + "WHERE gender = 'M' ";

		try {
			PreparedStatement stmt = this.con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

//-------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves the average result of the girls in the marksheet table.
	 *
	 * @return the average result of the girls in the marksheet table
	 */
	public double getAverageResultOfGirls() {
		double averageGirlResult = 0;
		String query = "SELECT AVG(math + physics + chemistry) AS averageGirlResult FROM marksheet WHERE gender = 'F' ";

		try {
			PreparedStatement pstmt = this.con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				averageGirlResult = rs.getDouble("averageGirlResult");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return averageGirlResult;
	}

//-------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Retrieves the average result of the boys in the marksheet table.
	 *
	 * @return the average result of the boys in the marksheet table
	 */
	public double getAverageResultOfBoys() {
		double averageBoyResult = 0;
		String query = "SELECT AVG(math + physics + chemistry) AS averageBoyResult FROM marksheet WHERE gender = 'M' ";

		try {
			PreparedStatement pstmt = this.con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				averageBoyResult = rs.getDouble("averageBoyResult");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return averageBoyResult;
	}

}
