package com.luv2code.jsf.jdbc;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Employee {
	private int id;
	private String name;
	private int age;
	private double salary;
	private int department_id;
	 
    public int getDepartment_id() {
        return department_id;
    }
 
    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
       
    }	
	public Employee() {
	}
	
	public Employee(int id, String name,int age,double salary,int department_id) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.salary=salary;
		this.department_id=department_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", Name=" + name + ", Age="
				+ age + ", Salary=" + salary + ", department_id=" + department_id+ "]";
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}


}
