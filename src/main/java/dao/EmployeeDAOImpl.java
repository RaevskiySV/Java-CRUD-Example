package dao;

import models.Employee;
import models.Animal;

import org.hibernate.Session;
import utils.HibernateSessionFactoryUtil;
import org.hibernate.query.Query;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public Employee getById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Employee employee = session.get(Employee.class, id);
        session.close();
        return employee;
    }

    @Override
    public List<Employee> getByName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Employee WHERE name = :param");
        query.setParameter("param", name);
        List<Employee> employees = (List<Employee>) query.list();
        session.close();
        return employees;
    }

    @Override
    public List<Employee> getAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Employee> employees = (List<Employee>) session.createQuery("FROM Employee").list();
        session.close();
        return employees;
    }

    @Override
    public Animal getAnimalById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Animal animal = session.get(Animal.class, id);
        session.close();
        return animal;
    }

    @Override
    public List<Animal> getAllAnimals() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Animal> animals = (List<Animal>) session.createQuery("FROM Animal").list();
        session.close();
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
