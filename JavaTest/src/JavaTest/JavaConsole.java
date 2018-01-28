package JavaTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Queue;


/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class JavaConsole {
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		FileOutputStream fos = new FileOutputStream("ser.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Person ps = new Person("zhangsan", 13, 2300);
		oos.writeObject(ps);
		oos.close();
		
		FileInputStream fis = new FileInputStream("ser.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Person pps = (Person)ois.readObject();
		System.out.println(pps.toString());
		
		
		
	}
}
