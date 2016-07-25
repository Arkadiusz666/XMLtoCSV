package workpackage;

/**
 * Created by AKrzos on 2016-07-25.
 */

import javax.xml.bind.annotation.XmlRootElement;


        import java.util.ArrayList;
        import java.util.List;

        import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Manager {
    private Integer managerId;
    private String firstName;
    private String lastName;
    private List<Employee> employees = new ArrayList<Employee>();

    public Integer getManagerId() {
        return managerId;
    }
    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public List<Employee> getEmployees() {
        return employees;
    }
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Manager Name: " + firstName + "\n");
        buffer.append("employees: \n");
        buffer.append(employees);
        return buffer.toString();
    }
}
