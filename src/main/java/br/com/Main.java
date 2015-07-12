package br.com;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {

    public static void main(String[] args) {

        // Read
        System.out.println("******* READ *******");
        List totClientes = list();
        System.out.println("Total Clientes: " + totClientes.size());

        // Write
        System.out.println("******* WRITE *******");
        Cliente empl = new Cliente("Oneide", "oneide@oneide");
        empl = save(empl);
        empl = read(empl.getId());
        System.out.printf("%d %s %s \n", empl.getId(), empl.getNome(), empl.getEmail());

        // Update
        System.out.println("******* UPDATE *******");
        Cliente empl2 = read(1l);
        System.out.println("Nome antes de Atualizar Update:" + empl2.getNome());
        empl2.setNome("OneideLuiz");
        update(empl2);	

        empl2 = read(1l); 
        System.out.println("Nome depois de Atualizar:" + empl2.getNome());

        // Delete
        System.out.println("******* DELETE *******");
        delete(empl);
        Cliente empl3 = read(empl.getId());
        System.out.println("Object:" + empl3);
    }

    private static List list() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();

        List clientes = session.createQuery("from Cliente").list();
        session.close();
        
        return clientes;
    }

    private static Cliente read(Long id) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();

        Cliente cliente = (Cliente) session.get(Cliente.class, id);
        session.close();
        return cliente;
    }

    private static Cliente save(Cliente cliente) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        Long id = (Long) session.save(cliente);
        cliente.setId(id);

        session.getTransaction().commit();
        session.close();

        return cliente;
    }

    private static Cliente update(Cliente cliente) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        session.merge(cliente);

        session.getTransaction().commit();
        session.close();
        
        return cliente;
    }

    private static void delete(Cliente cliente) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        session.delete(cliente);

        session.getTransaction().commit();
        session.close();
    }

}
