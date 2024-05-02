package in.sterling.TableData;

import java.sql.Date;

public class MarksheetTableData {
	
	private String rollNo ;
	private String name;
	private int maths;
	private int chemistry;
	private int physics;
	private String emailID;
	private char gendar;
	private Date dob;
	
	

	/**
	 * @param rollNo
	 * @param name
	 * @param maths
	 * @param chemistry
	 * @param physics
	 * @param emailID
	 * @param gendar
	 * @param dob
	 */
	
	public MarksheetTableData(String rollNo, String name, int maths, int chemistry, int physics, String emailID,
			char gendar, Date dob) {
		super();
		this.rollNo = rollNo;
		this.name = name;
		this.maths = maths;
		this.chemistry = chemistry;
		this.physics = physics;
		this.emailID = emailID;
		this.gendar = gendar;
		this.dob = dob;
	}

	public MarksheetTableData() {
	}


	/**
	 * @return the rollNo
	 */
	public String getRollNo() {
		return rollNo;
	}

	/**
	 * @param rollNo the rollNo to set
	 */
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the maths
	 */
	public int getMaths() {
		return maths;
	}

	/**
	 * @param maths the maths to set
	 */
	public void setMaths(int maths) {
		this.maths = maths;
	}

	/**
	 * @return the chemistry
	 */
	public int getChemistry() {
		return chemistry;
	}

	/**
	 * @param chemistry the chemistry to set
	 */
	public void setChemistry(int chemistry) {
		this.chemistry = chemistry;
	}

	/**
	 * @return the physics
	 */
	public int getPhysics() {
		return physics;
	}

	/**
	 * @param physics the physics to set
	 */
	public void setPhysics(int physics) {
		this.physics = physics;
	}

	/**
	 * @return the emailID
	 */
	public String getEmailID() {
		return emailID;
	}

	/**
	 * @param emailID the emailID to set
	 */
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	/**
	 * @return the gendar
	 */
	public char getGendar() {
		return gendar;
	}

	/**
	 * @param gendar the gendar to set
	 */
	public void setGendar(char gendar) {
		this.gendar = gendar;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	

}
