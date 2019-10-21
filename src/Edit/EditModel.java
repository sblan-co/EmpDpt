/*
 * Author: Sara Blanco Muñoz
 * Connectors
 * E-mail: mublasa@gmail.com
 * Date: 19/01/2018
 * Updated: 21/10/2019
 */
package Edit;

import Objects.Department;
import Objects.Employee;

// Current data we are handling on the Edit Panel
public class EditModel {
    private Department dpt;
    private Employee emp;
    
    public EditModel() {
        dpt = null;
        emp = null;
    }

    // Getters and setters
    
    public Department getDpt() {
        return dpt;
    }

    public void setDpt(Department dpt) {
        this.dpt = dpt;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    } 
}
