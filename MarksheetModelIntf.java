package in.sterling.interfaces;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.sterling.TableData.MarksheetTableData;
/**
 * This interface defines methods for managing MarksheetTableData objects in a data store.
 * Implementing classes should provide concrete implementations for these methods.
 */
public interface MarksheetModelIntf {
	
	public boolean add(MarksheetTableData mtd);
	public boolean deleteByRollNo(String rollNo);
	public boolean deleteByEmailID(String emailID);
	public boolean update(String columnName);
	public boolean updateAll(MarksheetTableData mtd);
	public boolean deleteAll();
	public ArrayList<?> get(String columnName);
	public Set<?> getAll();
	public LinkedHashSet<?> getMaritList();
	public int numberOfStudents();
	public LinkedHashSet<?> getFailedStudentsList();
	public ArrayList<?> absenties();
	public ArrayList<?> getToppers();
	public String[][] getLowestMarkStudents();
	public String[][] getPassedStudents();
	public double getAverageResultOfclass();
	public List<?> getATKTStudents();
	public double getCutOff();
	public int getNumberOfBoysPass();
	public int getNumberOfGirlsPass();
	public char getGradeOfStudent(String rollNo);
	public int getTotalNumberOfGirls();
	public int getTotalNumberOfBoys();
	public double getAverageResultOfGirls();
	public double getAverageResultOfBoys();
	}
