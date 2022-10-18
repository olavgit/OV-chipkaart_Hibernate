package p7.data;

import p7.domain.OVChipkaart;
import p7.domain.Product;
import p7.domain.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {
    public boolean save(OVChipkaart ovChipkaart);
    public boolean update(OVChipkaart ovChipkaart);
    public boolean delete(OVChipkaart ovChipkaart);
    public List<OVChipkaart> findByReiziger(Reiziger reiziger);
    public List<OVChipkaart> findByProduct(Product product);
    public List<OVChipkaart> findAll();
    public OVChipkaart findById(int id);
}
