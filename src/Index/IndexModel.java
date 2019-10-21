/*
 * Author: Sara Blanco Muñoz
 * Connectors
 * E-mail: mublasa@gmail.com
 * Date: 19/01/2018
 * Updated: 21/10/2019
 */

package Index;

import Objects.Department;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import Objects.Employee;
import java.util.ArrayList;

// Keeps track of the current information we are handling
public class IndexModel
{
    // DATABASE LOCATION
    final static String BDEmpDep = "C:/DDBB/db4o/EmpleDep.yap";
    
    private int dptN;
    private ArrayList<Department> dptList;
    private ArrayList<Employee> empList;
    private ObjectContainer db;   

    public IndexModel()
    {
        // OPEN DATABASE
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDEmpDep);
        
        // Initialize everything to null values, except the Departments List
        dptN = -1;
        this.refreshDpt();     
        this.refreshEmp();    
    }

    // Getters
    
    public ArrayList<Department> getDptList() {
        return dptList;
    }

    public ArrayList<Employee> getEmpList() {
        return empList;
    } 

    public ObjectContainer getDb() {
        return db;
    }
         
    // Get All Departments info from database
    
    private ArrayList<Department> getAllDpt() {        
        ObjectSet<Department> result = db.queryByExample(new Department());
        ArrayList<Department> allDpt = new ArrayList<>();
        
        // If there are no departments show "No departments yet." and deactivate modify and delete buttons
        if (result.isEmpty())
        {
            allDpt.add(new Department(-1, "No departments yet."));
        } 
        // Otherwise, get all departments and set a pleasant String structure to show on a List
        else
        {
            Department dptAux = (Department) result.next();
            
            for (int i = 0; i < result.size(); i++){
                allDpt.add(dptAux);
                
                if (i < result.size()-1)
                    dptAux = (Department) result.next();
            }
        }
        
        return allDpt;        
    }
    
    // Get All Employees info from database
    
    private ArrayList<Employee> getAllEmp() {        
        ObjectSet<Employee> result = db.queryByExample(new Employee());
        ArrayList<Employee> allEmp = new ArrayList<>();
        
        // If there are no employees show "No employees yet." and deactivate modify and delete buttons
        if (result.isEmpty())
        {
            allEmp.add(new Employee("No employees yet.", "-1", -1));
        } 
        // Otherwise, get all employees and set a pleasant String structure to show on a List
        else
        {
            Employee empAux = (Employee) result.next();
            
            for (int i = 0; i < result.size(); i++){
                allEmp.add(empAux);
                
                if (i < result.size()-1)
                    empAux = (Employee) result.next();
            }
        }
        
        return allEmp;        
    }

    public void refreshDpt() {
        dptList = getAllDpt();
    }

    public void refreshEmp() {    
        empList = getAllEmp();
    }
    
    
    
}
