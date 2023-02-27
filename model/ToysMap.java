package model;

public class ToysMap {
    public String map(Toys toy) {
        return String.format("Id:%s;Название:%s;Количество:%s;Шанс выпадения:%s", toy.getId(), toy.getName(), toy.getCount(), toy.getWeight());
    }

    public Toys map(String line) {
        String[] lines = line.split(";");
        for(int i = 0; i<lines.length; i++){
            String[] temp = lines[i].split(":");
            lines[i] = temp[1];
        }
        return new Toys(lines[0], lines[1], lines[2], lines[3]);
    }
}