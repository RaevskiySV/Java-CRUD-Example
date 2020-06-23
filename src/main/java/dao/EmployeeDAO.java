package dao;

import models.Employee;
import models.Animal;
import java.util.List;

public interface EmployeeDAO {

    Employee getById(int id);

    List<Employee> getByName(String name);

    List<Employee> getAll();

    Animal getAnimalById(int id);

    List<Animal> getAllAnimals();

    void save(Employee employee);

    void update(Employee employee);

    void delete(Employee employee);

}
