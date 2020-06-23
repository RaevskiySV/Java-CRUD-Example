package dao;

import models.Employee;
import models.Animal;

import org.hibernate.Session;
import utils.HibernateSessionFactoryUtil;
import org.hibernate.query.Query;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private Session session;

    public void openSession() {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    }

    public void closeSession() {
        session.close();
    }

    @Override
    public Employee getById(int id) {
        openSession();
        Employee employee = session.get(Employee.class, id);
        return employee;
    }

    @Override
    public List<Employee> getByName(String name) {
        openSession();
        Query query = session.createQuery("FROM Employee WHERE name = :param");
        query.setParameter("param", name);
        List<Employee> employees = (List<Employee>) query.list();
        return employees;
    }

    @Override
    public List<Employee> getAll() {
        openSession();
        List<Employee> employees = (List<Employee>) session.createQuery("FROM Employee").list();
        return employees;
    }

    @Override
    public Animal getAnimalById(int id) {
        openSession();
        Animal animal = session.get(Animal.class, id);
        return animal;
    }

    @Override
    public List<Animal> getAllAnimals() {
        openSession();
        List<Animal> animals = (List<Animal>) session.createQuery("FROM Animal").list();
        return animals;
    }

    @Override
    public void save(Employee employee) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(employee);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Employee employee) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(employee);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Employee employee) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(employee);
        transaction.commit();
        session.close();
    }

}
