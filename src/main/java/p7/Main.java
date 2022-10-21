package p7;

import p7.data.*;
import p7.domain.Adres;
import p7.domain.OVChipkaart;
import p7.domain.Product;
import p7.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.Date;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    private static void closeConnection() {
        factory.close();
    }

    private static void testReizigerDAO(ReizigerDAO rdao) {
        System.out.println("\n░░░░░░░░░░░░░░░ Test RDAO ░░░░░░░░░░░░░░░");

        System.out.println("\nrdao.findAll() vindt de volgende resultaten:");
        List<Reiziger> reizigers = rdao.findAll();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        System.out.println("\nrdao.findById() vindt de volgende reiziger:");
        Reiziger findReiziger = rdao.findById(1);
        System.out.println(findReiziger);

        System.out.println("\nrdao.findByGbdatum() vindt de volgende reiziger(s):");
        List<Reiziger> reizigers1 = rdao.findByGbdatum("2002-10-22");
        for (Reiziger r : reizigers1) {
            System.out.println(r);
        }

        System.out.print("\nVoor rdao.delete() zijn er " + reizigers.size() + " reizigers, na rdao.delete() ");
        rdao.delete(findReiziger);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers");

        String gbdatum = "2002-10-22";
        Reiziger os = new Reiziger(1, "O", "", "Sajtos", java.sql.Date.valueOf(gbdatum));
        System.out.print("\nVoor rdao.save() zijn er " + reizigers.size() + " reizigers, na rdao.save() ");
        rdao.save(os);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers");

        String gbdatumNieuw = "2006-10-22";
        Reiziger updateos = new Reiziger(1, "O", "de", "Lange", java.sql.Date.valueOf(gbdatumNieuw));
        System.out.print("\nVoor rdao.update() was de reiziger: " + rdao.findById(1));
        rdao.update(updateos);
        System.out.println("\nNa rdao.update() is de reiziger: " + rdao.findById(1));
    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) {
        System.out.println("\n░░░░░░░░░░░░░░░ Test ADAO ░░░░░░░░░░░░░░░");

        System.out.println("\nadao.findAll() vindt de volgende resultaten:");
        List<Adres> adressen = adao.findAll();
        for (Adres a : adressen) {
            System.out.println(a);
        }

        Adres adres = adao.findByReiziger(rdao.findById(2));
        System.out.println("\nadao.findByReiziger() vindt het volgende resultaat: " + adres);

        Adres nieuwAdres = new Adres(1, "4212CX", "63", "Spijkse Kweldijk", "Utrecht", rdao.findById(1));
        System.out.print("\nVoor adao.save() zijn er " + adressen.size() + " adressen, na adao.save() ");
        adao.save(nieuwAdres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen");

        String huisnummerupdate = "182";
        Adres updateAdres = new Adres(1, "4212CX", huisnummerupdate , "Spijkse Kweldijk", "Utrecht", rdao.findById(1));
        System.out.print("\nVoor adao.update() was het adres: " + adao.findByReiziger(rdao.findById(1)));
        adao.update(updateAdres);
        System.out.println("\nNa adao.update() is het adres: " + adao.findByReiziger(rdao.findById(1)));

        System.out.print("\nVoor adao.delete() zijn er " + adressen.size() + " adressen, na adao.delete() ");
        adao.delete(adao.findByReiziger(rdao.findById(1)));
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen");
        String gbdatumReset = "2006-10-22";
        Reiziger resetos = new Reiziger(1, "O", "de", "Lange", java.sql.Date.valueOf(gbdatumReset));
        rdao.save(resetos);
    }

    private static void testOVChipkaartDAO(OVChipkaartDAO ovcdao, ReizigerDAO rdao) {
        System.out.println("\n░░░░░░░░░░░░░░░ Test ODAO ░░░░░░░░░░░░░░░");

        System.out.println("\nodao.findAll() vindt de volgende resultaten:");
        List<OVChipkaart> ovChipkaartList = ovcdao.findAll();
        for (OVChipkaart o : ovChipkaartList) {
            System.out.println(o);
        }

        System.out.println("\nodao.findByReiziger() vindt de volgende resultaten:");
        List<OVChipkaart> ovChipkaartListReiziger = ovcdao.findByReiziger(rdao.findById(2));
        for (OVChipkaart o : ovChipkaartListReiziger) {
            System.out.println(o);
        }

        String deleteadres = "2023-05-30";
        OVChipkaart deleteOV = new OVChipkaart(99999, java.sql.Date.valueOf(deleteadres), 2, 10, rdao.findById(1));
        System.out.print("\nVoor odao.save() zijn er " + ovChipkaartList.size() + " kaarten, na odao.save() ");
        ovcdao.save(deleteOV);
        ovChipkaartList = ovcdao.findAll();
        System.out.println(ovChipkaartList.size() + " kaarten");

        OVChipkaart updateOV = new OVChipkaart(99999, java.sql.Date.valueOf(deleteadres), 1, 40, rdao.findById(1));
        System.out.print("\nVoor odao.update() was de kaart: " + ovcdao.findById(99999));
        ovcdao.update(updateOV);
        System.out.println("\nNa odao.update() is de kaart: " + ovcdao.findById(99999));

        System.out.print("\nVoor odao.delete() " + ovChipkaartList.size() + " kaarten, na odao.delete() ");
        ovcdao.delete(ovcdao.findById(99999));
        ovChipkaartList = ovcdao.findAll();
        System.out.println(ovChipkaartList.size() + " kaarten\n");
    }

    private static void testProduct(ProductDAO pdao, OVChipkaartDAO ovcdao, ReizigerDAO rdao) {
        System.out.println("\n░░░░░░░░░░░░░░░ Test PDAO ░░░░░░░░░░░░░░░");

        System.out.println("\npdao.findAll() vindt de volgende resultaten:");
        List<Product> productList = pdao.findAll();
        for (Product p : productList) {
            System.out.println(p);
        }

        System.out.println("\npdao.findById() vindt de volgende resultaten:");
        System.out.println(pdao.findById(1));

        Product nieuwproduct = new Product(7, "Kinderkaart", "Treinkaartje voor kinderen jonger dan 15.", 10.00);
        System.out.print("\nVoor pdao.save() zijn er " + productList.size() + " producten, na pdao.save() ");
        pdao.save(nieuwproduct);
        productList = pdao.findAll();
        System.out.println(productList.size() + " producten");

        Product updateProduct = new Product(7, "Testkaartje", "Treinkaartje voor kinderen jonger dan 15.", 20.00);
        System.out.print("\nVoor pdao.update() was het product: " + pdao.findById(7));
        pdao.update(updateProduct);
        System.out.println("\nNa pdao.update() is het product: " + "Product {7, Kinderkaart, Treinkaartje voor kinderen jonger dan 15., €20.0}");

        System.out.print("\nVoor pdao.delete() zijn er " + productList.size() + " producten, na pdao.delete() ");
        pdao.delete(pdao.findById(7));
        productList = pdao.findAll();
        System.out.println(productList.size() + " producten");
    }

    public static void main(String[] args) {
        try {
            Session session = getSession();
            ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(session);
            AdresDAOHibernate adao = new AdresDAOHibernate(session, rdao);
            OVChipkaartDAOHibernate ovcdao = new OVChipkaartDAOHibernate(session, rdao);
            ProductDAOHibernate pdao = new ProductDAOHibernate(session, ovcdao);
            testReizigerDAO(rdao);
            testAdresDAO(adao, rdao);
            testOVChipkaartDAO(ovcdao, rdao);
            testProduct(pdao, ovcdao, rdao);
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
