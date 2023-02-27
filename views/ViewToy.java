package views;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import controllers.Controller;
import model.Toys;

public class ViewToy {

    Toys toy;
    private Controller controller;

    public ViewToy(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        Queue<Toys> prizes = new LinkedList<>();
        Commands com = Commands.NONE;

        while (true) {
            String command = prompt("\nВведите команду:\n" +
                                "LIST - игрушеки для розыгрыша\n" +
                                "ADD - добавление новой игрушки\n" +
                                "CHANGE - изменение шанса выпадания\n" +
                                "DELETE - удаление игрушки\n" +
                                "GO - розыгрыш призов\n" +
                                "GET - выдача приза\n" +
                                "EXIT - выход\n\n");
            try {
                com = Commands.valueOf(command);
            }
            catch (IllegalArgumentException e) {
                System.out.println("Неверная команда");
            }
            if (com == Commands.EXIT)
                return;
            try {
                switch (com) {

                    case ADD:
                        Toys toy = setToy(false);
                        controller.saveToy(toy);
                        break;
                    case LIST:
                        List<Toys> toyList = controller.readToyList();
                        for (Toys item : toyList) {
                            System.out.println(item);
                            System.out.println();
                        }
                        break;
                    case CHANGE:
                        // String updateId = prompt("ID игрушки: ");
                        Toys updateToy = setToy(true);
                        controller.updateToy(updateToy);
                        break;
                    case DELETE:
                        String deleteId = prompt("ID игрушки: ");
                        controller.deleteToy(deleteId);
                        break;
                    case GO:
                        prizes = controller.putToy();
                    break;
                    case GET:
                        controller.getToy(prizes);
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    private Toys setToy(boolean forUpdate) {
        String idString = null;
        if (forUpdate) {
            idString = prompt("ID игрушки: ");
        }
        String name = prompt("Название: ");
        String count = prompt("Количество: ");
        String weight = prompt("Шанс выпадения: ");
        if (forUpdate) {
            return new Toys(idString, name, count, weight);
        }
        return new Toys(name, count, weight);
    }
}