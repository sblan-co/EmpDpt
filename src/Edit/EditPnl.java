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
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTextField;

// Interface to modify or add any information to the database
public class EditPnl {
    private JTextField txtId, txtName;
    private JComboBox cbDpt;

    public EditPnl() {
        txtId = new JTextField("e.g.: 0001");
        txtName = new JTextField("e.g.: Logistics");
        cbDpt = new JComboBox();        
        
        txtId.setForeground(Color.gray);
        txtId.setToolTipText("Numeric Id");
        
        txtName.setForeground(Color.gray);
        txtName.setToolTipText("It must include alphabetic characters");
    }

    // Getters
    
    public JTextField getTxtId() {
        return txtId;
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public JComboBox getCbDpt() {
        return cbDpt;
    }   

    // Setters
    
    public void setCbDpt(ArrayList<Department> data) {  
        for (Department d : data){
            this.cbDpt.addItem(d);
        }
    }      

    public void setDepartment(Department dpt) {
        this.txtId.setForeground(Color.black);
        this.txtName.setForeground(Color.black);
        this.txtId.setText(""+dpt.getDptN());
        this.txtName.setText(dpt.getDptName());
    }

    public void setEmployee(Employee emp) {
        this.txtId.setForeground(Color.black);
        this.txtName.setForeground(Color.black);
        this.txtId.setText(""+emp.getId());
        this.txtName.setText(emp.getName());
        this.cbDpt.setSelectedItem(emp.getDpt());
    }
}
