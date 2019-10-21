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
 * This class has all needed data from a DEPARTMENT and functions to 
 * handle its main information.
 * In this case, name of the department (DPTNAME) and its number (DPTN).
 */
public class Department
{
    private int dptN;
    private String dptName;

    public Department()
    {
        dptN = 0;
        dptName = null;
    }
    
    public Department(int dptoNo, String dptoNombre)
    {
        this.dptN = dptoNo;
        this.dptName = dptoNombre;
    }
    
    // Basics get and set data functions

    public int getDptN()
    {
        return dptN;
    }

    public void setDptN(int dptoNo)
    {
        this.dptN = dptoNo;
    }

    public String getDptName()
    {
        return dptName;
    }

    public void setDptName(String dptName)
    {
        this.dptName = dptName;
    }
    
    // Improved handling data functions
    
    public void insertData()
    {
        Scanner key = new Scanner(System.in);
        System.out.println("Please add department's details:\n");
        System.out.print("Name: ");
        this.setDptName(key.nextLine());
        System.out.print("Nº dpt.: ");
        this.setDptN(key.nextInt());
    }
    
    public void deleteRow(ObjectContainer db)
    {
        Scanner key = new Scanner(System.in);
        System.out.print("Insert Department's Number: ");
        this.setDptN(key.nextInt());
        
        ObjectSet <Department>result = db.queryByExample(this); 
        if (result.isEmpty())
        {
            System.out.println("ERROR. Number not found.");
        } else
        {
            Department existe = (Department) result.next();
            System.out.println("Rows to delete: " + result.size());
            if (result.size() > 1)
            {
                while (result.hasNext())
                {
                    Department dp = result.next();
                    db.delete(dp);
                    System.out.println("Deleted...");
                }
            } else
            {
                db.delete(existe); //sólo hay un registro
            }
        }
    }
    
    public void modificarDatos(ObjectContainer db)
    {
        Scanner key = new Scanner(System.in);
        System.out.print("Insert Department's Number: ");
        this.setDptN(key.nextInt());

        ObjectSet<Department> result = db.queryByExample(this);
        if (result.isEmpty())
        {
            System.out.println("ERROR. Number not found.");
        } 
        else
        {
            Department existe = (Department) result.next();
            existe.insertData();
            
            db.store(existe); 
        }
    }
    
    @Override
    public String toString(){
        return this.dptN + " - " + this.dptName;
    }
}
