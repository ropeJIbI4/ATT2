package controllers;

import java.util.List;
import java.util.Queue;

import model.Repository;
import model.Toys;

public class Controller {
    private final Repository repository;
    

    public Controller(Repository repository) {
        this.repository = repository;   
    }

    public void saveToy(Toys toy) throws Exception {
        validateToy(toy);
        repository.CreateToy(toy);
    }

    public List<Toys> readToyList() {
        return repository.getAllToys();
    }

    public Toys updateToy(Toys toy) throws Exception{
        validateToy(toy);
        return repository.updateToy(toy);
    }

    public void deleteToy(String deleteId) throws Exception {
        repository.deleteToy(deleteId);
    }

    public Queue<Toys> putToy() {
        return repository.putToy();
    }

    public void getToy(Queue<Toys> prizes) throws Exception {
        if(!prizes.isEmpty()){
            repository.getToy(prizes);
        }
        else {
            throw new Exception("Нет призов. Проведите розыгрыш.");
        }
    }

    private void validateToy(Toys toy) throws Exception{
        if (toy.getName().isEmpty()) {
            throw new Exception("Нет названия");
        }
        if (toy.getCount().isEmpty()) {
            throw new Exception("Нет количества");
        }
        if (toy.getWeight().isEmpty()) {
            throw new Exception("Нет шанса выпадения");
        }
    }
}
