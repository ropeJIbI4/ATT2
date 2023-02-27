package model;

import java.util.List;
import java.util.Queue;

public interface Repository {
    List<Toys> getAllToys();
    String CreateToy(Toys toy);
    Toys updateToy(Toys toy) throws Exception;
    void deleteToy(String deleteId) throws Exception;
    Queue<Toys> putToy();
    void getToy(Queue<Toys> prizes) throws Exception;
}