package p7.data;

import p7.domain.OVChipkaart;
import p7.domain.Product;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private Session session;
    private OVChipkaartDAOHibernate ovcdao;

    public ProductDAOHibernate(Session session, OVChipkaartDAOHibernate ovcdao) {
        this.session = session;
        this.ovcdao = ovcdao;
    }

    @Override
    public boolean save(Product product) {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(product);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        Transaction transaction = session.beginTransaction();
        try {
            session.update(product);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(product);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    @Override
    public List<Product> findAll() {
        Transaction transaction = session.beginTransaction();
        try {
            List<Product> products = session.createQuery("from Product", Product.class).list();
            transaction.commit();
            return products;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        }
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        Transaction transaction = session.beginTransaction();
        try {
            List<Product> products = session.createQuery("from Product where id in (select id from ov_chipkaart_product where id = :kaart_nummer)", Product.class).setParameter("kaart_nummer", ovChipkaart.getKaart_nummer()).list();
            transaction.commit();
            return products;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        }
    }

    @Override
    public Product findById(int id) {
        Transaction transaction = session.beginTransaction();
        try {
            Product product = session.createQuery("from Product where product_nummer = :product_nummer", Product.class)
                    .setParameter("product_nummer", id)
                    .uniqueResult();
            transaction.commit();
            return product;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        }
    }
}
