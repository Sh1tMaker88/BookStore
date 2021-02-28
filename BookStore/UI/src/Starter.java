package src;

import src.menu.MenuController;

import java.io.*;

public class Starter {
    public static void main(String[] args) throws IOException {

        Facade facade = Facade.getInstance();
        new Initializer();
//        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("obj.bin"))){
////            outputStream.writeObject(orderedBooks1);
//            outputStream.writeObject(book1);
//            outputStream.writeObject(book2);
//            outputStream.writeObject(order1);
//        } catch (FileNotFoundException e){
//            e.printStackTrace();
//        }

//        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("obj.bin"))){
//            while(true){
//                Object obj = inputStream.readObject();
//                if (obj instanceof Order){
//                    System.out.println(obj);
//                } else if (obj instanceof Book){
//                    facade.getBookService().addBookToStock((Book)obj);
//                    System.out.println(obj);
//                } else if (obj instanceof Request){
//                    System.out.println(obj);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            System.out.println("Reach end of deserialization");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        System.out.println(facade.getOrderService().getOrderDao().getAll());
        System.out.println("----------------");


        MenuController.getInstance().run();
    }
}
