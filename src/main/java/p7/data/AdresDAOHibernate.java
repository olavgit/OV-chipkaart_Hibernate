package p7.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import p7.domain.Adres;
import p7.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private Session session;
    private ReizigerDAOHibernate reizigerDAO;

    public AdresDAOHibernate(Session session, ReizigerDAOHibernate reizigerDAOPsql) throws SQLException {
        this.session = session;
        this.reizigerDAO = reizigerDAOPsql;
    }

    @Override
    public boolean save(Adres adres) {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(adres);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(adres);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(adres);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            return session.createQuery("from Adres where reiziger_id = :id", Adres.class)
                    .setParameter("id", reiziger.getId())
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {
        Transaction transaction = session.beginTransaction();
        try {
            List<Adres> adressen = session.createQuery("from Adres", Adres.class).list();
            transaction.commit();
            return adressen;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        }
    }
}
