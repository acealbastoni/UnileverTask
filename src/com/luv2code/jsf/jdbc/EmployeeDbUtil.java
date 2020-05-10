package com.luv2code.jsf.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EmployeeDbUtil {

	private static EmployeeDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/unileverResource";
	
	public static EmployeeDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new EmployeeDbUtil();
		}
		
		return instance;
	}
	
	private EmployeeDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}
		
	public List<Employee> getEmployees() throws Exception {

		List<Employee> employees = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from employees order by emp_name";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("emp_id");
				String name = myRs.getString("emp_name");
				int age = myRs.getInt("emp_age");
				double salary = myRs.getDouble("salary");
				int department_id = myRs.getInt("department_id");

				// create new Employee object
				//(int id, String name,int age,double salary,int department_id)
				Employee tempEmployee = new Employee(id, name, age,salary,department_id);

				// add it to the list of employees
				employees.add(tempEmployee);
			}
			
			return employees;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addEmployee(Employee theEmployee) throws Exception {

		System.out.print("helllllllllllllo1");
		System.out.print(theEmployee);
		System.out.print("helllllllllllllo2");
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into employees (emp_name, emp_age, salary,department_id) values (?,?,?,?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theEmployee.getName());
			
			
			myStmt.setInt(2,theEmployee.getAge());
			myStmt.setDouble(3,theEmployee.getSalary());
			myStmt.setInt(4,theEmployee.getDepartment_id());
			
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public Employee getEmployee(int employeeId) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from employees where emp_id=?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, employeeId);
			
			myRs = myStmt.executeQuery();

			Employee theEmployee = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("emp_id");
				String name = myRs.getString("emp_name");
				int age = myRs.getInt("emp_age");
				double salary = myRs.getDouble("salary");
				int department_id = myRs.getInt("department_id");

				theEmployee = new Employee(id, name, age,salary,department_id);
			}
			else {
				throw new Exception("Could not find employee id: " + employeeId);
			}

			return theEmployee;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	
	public void updateEmployee(Employee theEmployee) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update employees "
						+ " set emp_name=?, emp_age=?, salary=?, department_id=?"
						+ " where emp_id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theEmployee.getName());
			myStmt.setInt(2, theEmployee.getAge());
			myStmt.setDouble(3, theEmployee.getSalary());
			myStmt.setInt(4, theEmployee.getDepartment_id());
			myStmt.setInt(5, theEmployee.getId());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public void deleteEmployee(int employeeId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from employees where emp_id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, employeeId);
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}		
	}	
	
	private Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();
		
		return theConn;
	}
	
	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}
	
	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}	
}
