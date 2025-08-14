package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileHelper {

    public static void saveObject(File file, Object obj) {
        try {
            createDirIfNotExists(file);

            boolean fileExists = file.exists();
            FileOutputStream fos = new FileOutputStream(file, true); // append mode
            ObjectOutputStream oos = fileExists
                    ? new AppendableObjectOutputStream(fos)
                    : new ObjectOutputStream(fos);

            oos.writeObject(obj);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> readAllObjects(File file) {
        List<T> objects = new ArrayList<>();

        if (!file.exists()) return objects;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    @SuppressWarnings("unchecked")
                    T obj = (T) ois.readObject();
                    objects.add(obj);
                } catch (EOFException eof) {
                    break; // reached end of file
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return objects;
    }
    public static <T> void writeAllObjects(File file, List<T> objects) {
        try {
            createDirIfNotExists(file);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            for (T obj : objects) {
                oos.writeObject(obj);
            }
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDirIfNotExists(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    private static class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
        }
    }
}
