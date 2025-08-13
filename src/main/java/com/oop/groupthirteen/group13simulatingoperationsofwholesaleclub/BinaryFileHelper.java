package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileHelper {

    public static <T> void writeAllObjects(File file, List<T> objects) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            for (T obj : objects) {
                oos.writeObject(obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> readAllObjects(File file) {
        List<T> list = new ArrayList<>();
        if (!file.exists()) return list;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    T obj = (T) ois.readObject();
                    list.add(obj);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }
}
