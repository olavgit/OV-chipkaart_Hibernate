package p7.data;

import p7.domain.Adres;
import p7.domain.Reiziger;

import java.util.List;

public interface AdresDAO {
    public boolean save(Adres adres);
    public boolean update(Adres adres);
    public boolean delete(Adres adres);
    public Adres findByReiziger(Reiziger reiziger);
    public List<Adres> findAll();
}
