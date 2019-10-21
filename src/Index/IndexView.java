/*
 * Author: Sara Blanco Muñoz
 * Connectors
 * E-mail: mublasa@gmail.com
 * Date: 19/01/2018
 * Updated: 21/10/2019
 */

package Index;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

// Main frame with all our visual elements

public class IndexView extends JFrame
{
    private ManagementPnl dptPnl, empPnl;
    
    public IndexView()
    {                  
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JTabbedPane tabs = new JTabbedPane();
        
        dptPnl = new ManagementPnl(0);
        empPnl = new ManagementPnl(1);
                
        tabs.addTab("Departments", null, dptPnl, "Departments");
        tabs.addTab("Employees", null, empPnl, "Employees");
        
        this.add(tabs);
        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }    

    public ManagementPnl getDptPnl() {
        return dptPnl;
    }

    public ManagementPnl getEmpPnl() {
        return empPnl;
    }
    
}
