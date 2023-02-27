package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class RepozitoryFileNew implements Repository {

    ToysMap mapper = new ToysMap();
    FileOperation fileOperation;

    public RepozitoryFileNew(FileOperation fileOperation) {
        this.fileOperation = fileOperation;
    }

    @Override
    public List<Toys> getAllToys() {
        List<String> lines = fileOperation.readAllLines();
        List<Toys> toys = new ArrayList<>();
        for (String line : lines) {
            toys.add(mapper.map(line));
        }
        return toys;
    }

    @Override
    public String CreateToy(Toys toy) {
        List<Toys> toys = getAllToys();
        int max = 0;
        for (Toys item : toys) {
            int id = Integer.parseInt(item.getId());
            if (max < id) {
                max = id;
            }
        }
        int newId = max + 1;
        String id = String.format("%d", newId);
        toy.setId(id);
        toys.add(toy);
        saveUsers(toys);
        return id;
    }

    void saveUsers(List<Toys> toys) {
        List<String> lines = new ArrayList<>();
        for (Toys item : toys) {
            lines.add(mapper.map(item));
        }
        fileOperation.saveAllLines(lines);
    }

    @Override
    public Toys updateToy(Toys toy) throws Exception {
        List<Toys> toys = getAllToys();
        Toys foundedToy = findToyById(toys, toy.getId());
        foundedToy.setWeight(toy.getWeight());
        saveUsers(toys);
        return foundedToy;
    }

    public void updateCount(Toys toy) throws Exception {
        List<Toys> toys = getAllToys();
        Toys foundedToy = findToyById(toys, toy.getId());
        foundedToy.setCount(Integer. toString(Integer.parseInt(foundedToy.getCount()) - 1));
        saveUsers(toys);
    }

    private Toys findToyById(List<Toys> toys, String toyId) throws Exception {
        for (Toys toy : toys) {
            if (toy.getId().equals(toyId)) {
                return toy;
            }
        }
        throw new Exception("Игрушка не найдена");
    }

    @Override
    public void deleteToy(String deleteId) throws Exception {
        List<Toys> toys = getAllToys();
        Toys deleteToy = findToyById(toys, deleteId);
        toys.remove(deleteToy);
        saveUsers(toys);
    }

    @Override
    public Queue<Toys> putToy() {
        List<Toys> toys = getAllToys();
        List<Toys> temp = new ArrayList<>();
        Queue<Toys> prizes = new LinkedList<>();
        for (Toys toy : toys) {
            int count = Integer.parseInt(toy.getWeight())/10; 
                for (int i = 0; i < count; i++) {
                    temp.add(toy);
                }
            }
        if (temp.size() < 10) {
            int correct = 10 - temp.size();
            Random random = new Random();
            for (int i = 0; i < correct; i++){
                int randomIndex = random.nextInt(temp.size());
                temp.add(temp.get(randomIndex));
            }
        }
        Collections.shuffle(temp);
        for (Toys toy : temp) {
            prizes.offer(toy);
        }
        return prizes;
    }

    @Override
    public void getToy(Queue<Toys> prizes) throws Exception {
        Toys toysPriz = prizes.poll();
        System.out.println(prizes);
        String line = mapper.map(toysPriz);
        fileOperation.savePriz(line);
        List<Toys> toys = getAllToys();
        Toys updateToy = findToyById(toys, toysPriz.getId());
        updateCount(updateToy);
    }

    


}