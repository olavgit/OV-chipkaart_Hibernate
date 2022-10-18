package p7.data;

import p7.domain.Reiziger;

import java.util.List;

public interface ReizigerDAO {
    public boolean save(Reiziger reiziger);
    public boolean update(Reiziger reiziger);
    public boolean delete(Reiziger reiziger);
    public Reiziger findById(int id);
    public List<Reiziger> findByGbdatum(String date);
    public List<Reiziger> findAll();
}
