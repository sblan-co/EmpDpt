/*
 * Author: Sara Blanco Muñoz
 * Connectors
 * E-mail: mublasa@gmail.com
 * Date: 19/01/2018
 */
package Objects;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.util.Scanner;

/**
 * Class which keeps all needed data from an EMPLOYEE and functions to 
 * handle his/her personal data.
 * In this case, NAME of the employee, his/her ID and the number of the 
 * department where he/she works (DPT).
 */
public class Employee
{
    private String name, id;
    private int dpt;

    public Employee()
    {
        name = id = null;
        dpt = 0;
    }

    public Employee(String name, String id, int dpt)
    {
        this.name = name;
        this.id = id;
        this.dpt = dpt;
    }

    // Basics get and set data functions
    
    public String getName()
    {
        return name;
    }

    public void setName(String nombre)
    {
        this.name = nombre;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String nif)
    {
        this.id = nif;
    }

    public int getDpt()
    {
        return dpt;
    }

    public void setDpt(int dpto)
    {
        this.dpt = dpto;
    }

    @Override
    public String toString(){
        return this.id + " - Dpt. " + this.dpt + " - " + this.name;
    }
    
    // Improved handling data functions
    
    public void insertData()
    {
        Scanner key = new Scanner(System.in);
        System.out.println("Insert Employee's personal information:\n");
        System.out.print("ID: ");
        this.setId(key.nextLine());
        // CHECK IF THE EMPLOYEE ALREADY EXIST
        
        System.out.print("Name: ");
        this.setName(key.nextLine());
        System.out.print("Dpt.: ");
        this.setDpt(key.nextInt());
    }

    public void deleteData(ObjectContainer db)
    {
        Scanner key = new Scanner(System.in);
        System.out.print("Insert Employee's ID: ");
        this.setId(key.nextLine());

        ObjectSet<Employee> result = db.queryByExample(this);
        if (result.isEmpty())
        {
            System.out.println("ID not found.");
        } else
        {
            Employee existe = (Employee) result.next();
            System.out.println("Rows to delete: " + result.size());
            if (result.size() > 1)
            {
                while (result.hasNext())
                {
                    Employee em = result.next();
                    db.delete(em);
                    System.out.println("Deleted...");
                }
            } else
            {
                db.delete(existe); //There's only one row
            }
        }
    }

    public void modifyData(ObjectContainer db)
    {
        Scanner key = new Scanner(System.in);
        System.out.print("Insert Employee's ID: ");
        this.setId(key.nextLine());

        ObjectSet<Employee> result = db.queryByExample(this);
        if (result.isEmpty())
        {
            System.out.println("ID not found.");
        } 
        else
        {
            Employee existe = (Employee) result.next();
            existe.insertData();
            
            db.store(existe); 
        }
    }
    
    private Employee checkId(ObjectContainer db, String n)
    {
        Employee e = new Employee();
        e.setId(n);
        
        ObjectSet<Employee> result = db.queryByExample(e);
        if (!result.isEmpty())
            e = (Employee) result.next();
               
        return e;
    }
}
