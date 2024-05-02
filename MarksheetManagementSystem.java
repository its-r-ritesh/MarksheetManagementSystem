package in.sterling.Main;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


import in.sterling.Connection.ConnectionProvider;
import in.sterling.Dao.MarksheetDao;
import in.sterling.TableData.MarksheetTableData;

public class MarksheetManagementSystem {
	static Scanner sc = new Scanner(System.in);
	static MarksheetDao md = new MarksheetDao(ConnectionProvider.getConnection());
	static MarksheetTableData mtd ;
	
	
	/**
     * Displays the home view of the application.
     */
	public static void homeView() {

		System.out.println("\n" + "\t\t\t\t\t\t\t\t\t" + "MODEL HIGHER SECONDARY SCHOOL");
		System.out.println("\t\t\t\t\t\t\t\t\t   " + "Civil Lines, Rewa (M.P.)");
		System.out.println("\t\t\t\t\t\t\t\t" + "(Run by Board Of Secondary Education, M.P. Bhopal)");
		System.out.println("\n"
				+ "**----------*----------**----------*----------**----------*----------**----------*----------***----------*----------**----------*----------**----------*----------**----------*----------**"
				+ "\n\n\n\n");
		System.out.println("\t"
				+ "+----------------------+-----------------------------------+-------------------------------------+---------------------------------+------------------------------------+");
		System.out.println("\t"
				+ "| 1. Add MarkSheet     | 2. Delete MarkSheet(by Roll No.)  | 3. Delete MarkSheet (by Email ID)   | 4. Update MarkSheet (any One)   | 5. Update all data from MarkSheet  |");
		System.out.println("\t"
				+ "+----------------------+-----------------------------------+-------------------------------------+---------------------------------+------------------------------------+");
		System.out.println("\t"
				+ "| 6. Marit List        | 7. Delete All Data                | 8. All Students Marksheets          | 9. Get MarkSheet (any One)      | 10. Failed Students List           |");
		System.out.println("\t"
				+ "+----------------------+-----------------------------------+-------------------------------------+---------------------------------+------------------------------------+");
		System.out.println("\t"
				+ "| 11. Toppers List     | 12. Lowest Mark Students List     | 13. Passed Students List            | 14. Number Of Students          | 15. Average Result of Class        |");
		System.out.println("\t"
				+ "+----------------------+-----------------------------------+-------------------------------------+---------------------------------+------------------------------------+");
		System.out.println("\t"
				+ "| 16. Cut Off          | 17. ATKT Students List            | 18. Number of Boys Passed           | 19. Number of Girls Passed      | 20. Grade of Student               |");
		System.out.println("\t"
				+ "+----------------------+-----------------------------------+-------------------------------------+---------------------------------+------------------------------------+");
		System.out.println("\t"
				+ "| 21. Absentees List   | 22. Total Number of Girls         | 23. Total Number of Boys            | 24. Average Result of Girls     | 25. Average Result of Boys         |");
		System.out.println("\t"
				+ "+----------------------+-----------------------------------+-------------------------------------+---------------------------------+------------------------------------+");
		System.out.println("\t"
				+ "                                                           |              0. Exit                |");
		System.out.println("\t"
				+ "                                                           +-------------------------------------+");

	}

	 /**
     * Generates a unique roll number based on the given parameters.
     * 
     * @param year       The current year.
     * @param name       The student's name.
     * @param userNumber The user number.
     * @return The generated roll number.
     */
	public static String generateRollNumber(int year, String name, int userNumber) {
		StringBuilder rollNumber = new StringBuilder();
		/** Append the 4-digit year */
		rollNumber.append(String.format("%04d", year));

		/** Append the first 4 characters of the name (or less if name is shorter) */
		int charsToAppend = Math.min(4, name.length());
		rollNumber.append(name.substring(0, charsToAppend).toUpperCase());

		/** Append the 4-digit user number */
		rollNumber.append(String.format("%04d", userNumber));

		return rollNumber.toString();
	}

	 public static boolean isValidName(String name) {
	        // Simple validation: check if the name contains only alphabetic characters and spaces
	        return name.matches("[a-zA-Z ]+");
	    }
	/**
	  Adds marksheet data for a student.
	 */
	public static void addMarksheetData() {
		boolean b = false;
		String name = "";
		while(!b) {
			System.out.print("\nEnter student's name: ");
			 name = sc.nextLine().toUpperCase();
		if(!isValidName(name)||(name=="")) {
			System.out.println("\nPlease enter correct name..");
		}
		else {
			b=true;
		}
		}
//---------------------------------------------------------------------------------------------------------------------------------------		
		int year = LocalDate.now().getYear();
		int userNumber = 0;
		String rollNumber = null;
				boolean isValid = false;
		while (!isValid) {
			System.out.println("\nEnter a Roll number (4-digit and don't start from Zero): ");
			String rollNo = sc.nextLine();

			/** Alphanumeric Roll Number for Logic */
			try {
				userNumber = Integer.parseInt(rollNo);
				if (userNumber < 1000 || userNumber > 9999) {
					System.err.println("Warning: Please enter a 4-digit number and don't start from Zero.");
				} else {
					userNumber = Integer.parseInt(rollNo);
					isValid = true;
					rollNumber = generateRollNumber(year, name, userNumber);
				}
			} catch (NumberFormatException e) {
				System.err.println("Warning: Please enter a valid 4-digit Integer number.");

			}

		}

//---------------------------------------------------------------------------------------------------------------------------------------		
		int math = 0;
		int physics = 0;
		int chemistry = 0;
		boolean num = true;
		while (num) {
			System.out.println("Enter Math's Number: ");
			math = sc.nextInt();

			System.out.println("Enter Physics Number: ");
			physics = sc.nextInt();

			System.out.println("Enter Chemistry Number: ");
			chemistry = sc.nextInt();

			/** For Subjects Number Logic */

			if ((math < 0 || math > 100) || (physics < 0 || physics > 100) || (chemistry < 0 || chemistry > 100)) {
				System.err.println("Warning: Please enter a valid number.");
			} else {
				num = false;
			}
		}
//---------------------------------------------------------------------------------------------------------------------------------------		
		String emailID = null;
		boolean mail = false;
		while (!mail) {
			System.out.println("Enter Valid Email ID: ");
			emailID = sc.next().trim().toLowerCase();

			if (!(emailID.contains("@") && emailID.contains("."))) {
				System.err.println("Warning: Please enter a valid Email ID.");
			} else {
				mail = true;
			}
		}
//---------------------------------------------------------------------------------------------------------------------------------------		
		char gender = 0;
		boolean gndr=false;
		while (!gndr) {
		System.out.println("Enter Gender Male for(M)and Female(F): ");
		
//			byteInput = System.in.read();
//			gender = Character.toUpperCase((char) byteInput);
			gender = sc.next().toUpperCase().charAt(0);
			
			if(!(gender=='M'||gender=='F')) {
				System.err.println("Warning: Please enter a valid Gendar.");
			
			}
			else {
				gndr=true;
			}
		
		}
//---------------------------------------------------------------------------------------------------------------------------------------		

		System.out.println("Enter your date of birth (yyyy-MM-dd): ");
		String dobString = sc.next();

		java.sql.Date dob = null;
		try {
			java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dobString);
			dob = new java.sql.Date(utilDate.getTime());
		} catch (ParseException e) {
			System.err.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
		}
//---------------------------------------------------------------------------------------------------------------------------------------		

		 mtd = new MarksheetTableData(rollNumber, name, math, chemistry, physics, emailID, gender,
				dob);
		
	}
	/**
	  Updates a single column in the database.
	 */
	public static void updateSingleColumn() {
		boolean b = false;
		 while (!b) {
			 System.out.println("\n\n");
				System.out.println("\t"+ "+-----------------------------------+-------------------------------------+---------------------------------+------------------------------------+");
				System.out.println("\t"+ "| 1. Update Name                    | 2. Update Email ID                  | 3.Update Date Of Birth          | 4. Update Gender                   |");
				System.out.println("\t"+ "+-----------------------------------+-------------------------------------+---------------------------------+------------------------------------+");
				System.out.println("\t"+ "| 5.Update Math's Number            | 6.Update Physics Number             | 7.Update Chemistry Number       | 0. Exit                            |");
				System.out.println("\t"+ "+-----------------------------------+--------------------+----------------+----------------+----------------+------------------------------------+");
				System.out.println("\n\n");
	            
				System.out.println("Choose opction as per your requirement.....");
				int choice = sc.nextInt();
	            sc.nextLine(); // Consume newline

				switch (choice) {
				case 1:
					if (md.update("name")) {
						System.out.println("Record Updated successfully.");
					} else {
						System.err.println("Failed to update record.");
					}
					break;
				case 2:
					if (md.update("emailID")) {
						System.out.println("Record Updated successfully.");
					} else {
						System.err.println("Failed to update record.");
					}
					break;
				case 3:
					if (md.update("dob")) {
						System.out.println("Record Updated successfully.");
					} else {
						System.err.println("Failed to update record.");
					}
					break;
				case 4:
					if (md.update("gender")) {
						System.out.println("Record Updated successfully.");
					} else {
						System.err.println("Failed to update record.");
					}
					break;
				case 5:
					if (md.update("math")) {
						System.out.println("Record Updated successfully.");
					} else {
						System.err.println("Failed to update record.");
					}
					break;
				case 6:
					if (md.update("physics")) {
						System.out.println("Record Updated successfully.");
					} else {
						System.err.println("Failed to update record.");
					}
					break;
				case 7:
					if (md.update("chemistry")) {
						System.out.println("Record Updated successfully.");
					} else {
						System.err.println("Failed to update record.");
					}
					break;
				case 0:
					b= true;
					break;
				default:
					System.err.println("Invalid choice. Please enter a valid option.");
					break;
				}

		 }
	}
//-------------------------------------------------Main Method----------------------------------------------------------------------------
	/**
	  The main method of the application.
	 */
	public static void main(String[] args) {

		boolean exit = false;

		while (!exit) {
			homeView();
			System.out.println("Enter your choice: ");

			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				addMarksheetData();
				if (md.add(mtd)) {
					System.out.print("Done");
					int i = 5;
					while (i != 0) {
						System.out.print(".");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) { // TODO Auto-generated catch block
							e.printStackTrace();
						}
						i--;
					}
				} else {
					System.err.println("Some Error!!");
				}
				break;
			case 2:
				System.out.println("Enter your valid Roll Number: ");
				String rollNo=sc.next().trim();
				 if (md.deleteByRollNo(rollNo)){
			            System.out.println("Record deleted successfully.");
			        } else {
			            System.err.println("Failed to delete record.");
			        }
				 break;
			case 3:
				System.out.println("Enter your valid email ID: ");
				String emailID=sc.next().trim();
				 if (md.deleteByemailID(emailID)){
			            System.out.println("Record deleted successfully.");
			        } else {
			            System.err.println("Failed to delete record.");
			        }
				 break;
			case 4:
				updateSingleColumn();
				break;
			case 5:
				System.out.println("Please enter New Data.....\n");
				addMarksheetData();
				if (md.updateAll(mtd)) {
					System.out.print("Updating");
					int i = 5;
					while (i != 0) {
						System.out.print(".");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) { // TODO Auto-generated catch block
							e.printStackTrace();
						}
						i--;
					}
					System.out.println("Done.");
				} else {
					System.err.println("Some Error!!");
				}
				break;
			case 6:
					LinkedHashSet<MarksheetTableData> maritList = md.getMeritList();
					 System.out.println("\nMarit List:");
			            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+");
			            System.out.println("| Rank    |    Roll No     |          Name           | Mathamatics   |         Physics      |    Chemistry    |");
			            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+");
			            int j=1;
					for(MarksheetTableData mtd:maritList) {
						
						if((mtd.getMaths()>=33)&&(mtd.getPhysics()>=33)&&(mtd.getChemistry()>=33)) {
						System.out.printf("| %-6d  | %-14s | %-23s | %-13d | %-20d | %-13d   |\n",
								j++,mtd.getRollNo(), mtd.getName(), mtd.getMaths(), mtd.getPhysics(), mtd.getChemistry());
			            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+");
					}
					}
				break;
			case 7: 
				System.out.println("Are you sure you want to delete all data? (yes/no)");
				String confirm = sc.nextLine();
				switch (confirm.toLowerCase()) {
				case "yes":
					if (md.deleteAll()) {
						System.out.print("Deleting");
						int i = 5;
						while (i != 0) {
							System.out.print(".");
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) { // TODO Auto-generated catch block
								e.printStackTrace();
							}
							i--;
						}
						System.out.println("Done.");
					} else {
						System.err.println("Some Error!!");
					}
					break;
				case "no":
					break; // Continue the loop
				default:
					System.err.println("Invalid input. Please enter 'yes' or 'no'.");
					break;
				}
				break;
			case 8:
				Set<MarksheetTableData> allRecords = md.getAll();
				 System.out.println("\nAll Students Marksheets List:");
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+--------------+-------------------------------+--------------+--------------------+");
		            System.out.println("|   No.   |    Roll No.    |          Name           | Mathamatics   |         Physics      |    Chemistry    |  Total       |         Email ID              |    Gender    |   Date OF Birth    |");
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+--------------+-------------------------------+--------------+--------------------+");
		            int k=1;
				for(MarksheetTableData mtd:allRecords) {
					
					System.out.printf("| %-6d  | %-14s | %-23s | %-13d | %-20d | %-13d   |  %-10d  |  %-27s  |  %-10s  |   %-14s   |\n",
							k++,mtd.getRollNo(), mtd.getName(), mtd.getMaths(), mtd.getPhysics(), mtd.getChemistry(),mtd.getMaths()+mtd.getPhysics()+mtd.getChemistry(),mtd.getEmailID(),mtd.getGendar(),mtd.getDob());
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+--------------+-------------------------------+--------------+--------------------+");
				
				}
				break;
			case 9:
				System.out.println("\n\nPlease enter Roll Number:- ");
				String userNo = sc.next().trim();
				
				ArrayList<MarksheetTableData> record = md.get(userNo);
				 System.out.println("\n Student Marksheets List:");
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+--------------+-------------------------------+--------------+--------------------+");
		            System.out.println("|   No.   |    Roll No.    |          Name           | Mathamatics   |         Physics      |    Chemistry    |  Total       |         Email ID              |    Gender    |   Date OF Birth    |");
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+--------------+-------------------------------+--------------+--------------------+");
		            int l=1;
				for(MarksheetTableData mtd:record) {
					
					System.out.printf("| %-6d  | %-14s | %-23s | %-13d | %-20d | %-13d   |  %-10d  |  %-27s  |  %-10s  |   %-14s   |\n",
							l++,mtd.getRollNo(), mtd.getName(), mtd.getMaths(), mtd.getPhysics(), mtd.getChemistry(),mtd.getMaths()+mtd.getPhysics()+mtd.getChemistry(),mtd.getEmailID(),mtd.getGendar(),mtd.getDob());
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+--------------+-------------------------------+--------------+--------------------+");
				
				}
			break;
			case 10:
				LinkedHashSet<MarksheetTableData> failedstudents = md.getFailedStudentsList();
				 System.out.println("\nFailed Students List:");
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+--------------+-------------------------------+--------------+--------------------+");
		            System.out.println("|   No.   |    Roll No.    |          Name           | Mathamatics   |         Physics      |    Chemistry    |  Total       |         Email ID              |    Gender    |   Date OF Birth    |");
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+--------------+-------------------------------+--------------+--------------------+");
		            int m=1;
		            
				for(MarksheetTableData mtd:failedstudents) {
					
					int  math = mtd.getMaths();
					int physics= mtd.getPhysics();
					int chemistry= mtd.getChemistry();
					
					if((math < 33 && physics < 33)||(math < 33 && chemistry < 33) ||(physics < 33 && chemistry < 33)) {
						
					System.out.printf("| %-6d  | %-14s | %-23s | %-13d | %-20d | %-13d   |  %-10d  |  %-27s  |  %-10s  |   %-14s   |\n",
							m++,mtd.getRollNo(), mtd.getName(), mtd.getMaths(), mtd.getPhysics(), mtd.getChemistry(),mtd.getMaths()+mtd.getPhysics()+mtd.getChemistry(),mtd.getEmailID(),mtd.getGendar(),mtd.getDob());
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+--------------+-------------------------------+--------------+--------------------+");

					}	
				}
				break;
			case 11:
				ArrayList<MarksheetTableData> topperList = md.getToppers();
				 System.out.println("\nToppers List:");
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+");
		            System.out.println("| Rank    |    Roll No     |          Name           | Mathamatics   |         Physics      |    Chemistry    |");
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+");
		            int o=1;
					for (MarksheetTableData mtd : topperList) {
						int i = 0;
						if ((mtd.getMaths() >= 33) && (mtd.getPhysics() >= 33) && (mtd.getChemistry() >= 33)) {
							if (i <= 5) {
								System.out.printf("| %-6d  | %-14s | %-23s | %-13d | %-20d | %-13d   |\n", o++,
										mtd.getRollNo(), mtd.getName(), mtd.getMaths(), mtd.getPhysics(),
										mtd.getChemistry());
								System.out.println(
										"+---------+----------------+-------------------------+---------------+----------------------+-----------------+");
							}
						}
					}
				
					break;
			case 12:
				String[][] lowestMarkStudents = md.getLowestMarkStudents();
				System.out.println("\nLowest Mark Students List:");
					System.out.println("+---------+----------------+-------------------------+---------------+");
		            System.out.println("| No.     |    Roll No     |          Name           |      Total    |");
		            System.out.println("+---------+----------------+-------------------------+---------------+");
		    
				 if (lowestMarkStudents != null) {
					 int p =1;
					 
			            for (String[] student : lowestMarkStudents) {
			                System.out.printf("| %-6d  | %-14s | %-23s | %-13s |\n",
									p++,student[0],student[1], student[2]);
				            System.out.println("+---------+----------------+-------------------------+---------------+");
							
			            }
			        } else {
			            System.err.println("No students found.");
			        }
				
				
				break;
			case 13:
				String[][] passedStudents = md.getPassedStudents();
				System.out.println("\nPassed Students List:");
					System.out.println("+---------+----------------+-------------------------+---------------+");
		            System.out.println("| No.     |    Roll No     |          Name           |      Total    |");
		            System.out.println("+---------+----------------+-------------------------+---------------+");
		    
				 if (passedStudents != null) {
					 int p =1;
					 
			            for (String[] student : passedStudents) {
			                System.out.printf("| %-6d  | %-14s | %-23s | %-13s |\n",
									p++,student[0],student[1], student[2]);
				            System.out.println("+---------+----------------+-------------------------+---------------+");
							
			            }
			        } else {
			            System.err.println("No students found.");
			        }
				
				
				break;
			case 14:
				int totalStudents = md.numberOfStudents();
		        System.out.println("Total number of students: " + totalStudents);
				break;
				
			case 15:
				double average = md.getAverageResultOfClass();
		        System.out.println("Average result of class: " + average);
				break;
			case 16:
				double cutOff = md.getCutOff();
		        System.out.println("CutOff for ScholerShip: " + cutOff);
				break;
			case 17:
				List<MarksheetTableData> ATKTstudent = md.getATKTStudents();
				 System.out.println("\nATKT Students List:");
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+--------------+-------------------------------+--------------+--------------------+");
		            System.out.println("|   No.   |    Roll No.    |          Name           | Mathamatics   |         Physics      |    Chemistry    |  Total       |         Email ID              |    Gender    |   Date OF Birth    |");
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+--------------+-------------------------------+--------------+--------------------+");
		            int q=1;
		           
				for(MarksheetTableData mtd:ATKTstudent) {
					
					int  mth = mtd.getMaths();
					int phy= mtd.getPhysics();
					int chem= mtd.getChemistry();
					
					if((mth < 33 && phy >= 33 && chem >= 33)|| (mth >= 33 && phy < 33 && chem >= 33)|| (mth >= 33 && phy >= 33 && chem < 33)) {
						
					System.out.printf("| %-6d  | %-14s | %-23s | %-13d | %-20d | %-13d   |  %-10d  |  %-27s  |  %-10s  |   %-14s   |\n",
							q++,mtd.getRollNo(), mtd.getName(), mtd.getMaths(), mtd.getPhysics(), mtd.getChemistry(),mtd.getMaths()+mtd.getPhysics()+mtd.getChemistry(),mtd.getEmailID(),mtd.getGendar(),mtd.getDob());
		            System.out.println("+---------+----------------+-------------------------+---------------+----------------------+-----------------+--------------+-------------------------------+--------------+--------------------+");
					}
						
				}
				break;
			case 18:
				int boysPass = md.getNumberOfBoysPass();
		        System.out.println("Number of boys pass: " + boysPass);
				break;
			case 19:
				int girlsPass = md.getNumberOfGirlsPass();
		        System.out.println("Number of girls pass: " + girlsPass);
				break;
			case 20:
				System.out.println("\n\nPlease enter Roll Number:- ");
				String userRollNo = sc.next().trim().toUpperCase();
				char grade = md.getGradeOfStudent(userRollNo);
				if(grade!=0) {
				System.out.println("Roll Number :- "+userRollNo);
				System.out.println("Grade :- "+grade);
				}
				break;
			case 21:
				 ArrayList<String> absentStudents = md.absenties();

				    System.out.println("\nAbsent Students:\n");
				    for (String student : absentStudents) {
				        System.out.println(student);
				    }
				
				
				break;
			case 22:
				int totalGirls = md.getTotalNumberOfGirls();
		        System.out.println("Number of girls : " + totalGirls);
				break;
			case 23:
				int totalBoys = md.getTotalNumberOfBoys();
		        System.out.println("Number of boys : " + totalBoys);
				break;
			case 24:
				double averageResultGirls = md.getAverageResultOfGirls();
		        System.out.println("Average result of Girls: " + averageResultGirls);
				break;
			case 25:
				double averageResultBoys = md.getAverageResultOfBoys();
		        System.out.println("Average result of Boys: " + averageResultBoys);
				break;
			case 0:
				System.out.println("Are you sure you want to exit? (yes/no)");
				String confirmation = sc.nextLine();
				switch (confirmation.toLowerCase()) {
				case "yes":
					System.out.print("Exiting program");
					int i = 5;
					while (i != 0) {
						System.out.print(".");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) { // TODO Auto-generated catch block
							e.printStackTrace();
						}
						i--;
					}
					System.out.println();
					 System.out.println("ThankYou For Using Marksheet Management System!!!");
					exit = true;
				case "no":
					break; // Continue the loop
				default:
					System.err.println("Invalid input. Please enter 'yes' or 'no'.");
					break;
				}
				break;
			default:
				System.err.println("Invalid choice. Please try again.");
			}
		}
	}

}
