package JavaTest;

import java.io.Serializable;

public class Person implements Serializable {
	
	Person(String name,int age,int salary)
	{
		this.name = name;this.age = age;this.salary = salary;
	}
	public String name;
	public int age;
	public int salary;
	
	
	@Override
	public String toString()
	{
		return "Name:"+name +" Age:"+age + " Salary:"+salary;
	}
}
