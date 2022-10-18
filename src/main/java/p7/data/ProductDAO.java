package p7.data;

import p7.domain.OVChipkaart;
import p7.domain.Product;

import java.util.List;

public interface ProductDAO {
    boolean save(Product product);
    boolean update(Product product);
    boolean delete(Product product);
    List<Product> findAll();
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    Product findById(int id);

}
