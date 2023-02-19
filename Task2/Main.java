package serializable_task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        GameProgress gameProgress1 = new GameProgress(0, 0, 0, 0, 0);
        GameProgress gameProgress2 = new GameProgress(3, 2, 2, 2, 2.9);
        GameProgress gameProgress3 = new GameProgress(10, 8, 7, 8, 10.4);

        saveGame("D:\\Games\\savegames\\save1.dat", gameProgress1);
        saveGame("D:\\Games\\savegames\\save2.dat", gameProgress2);
        saveGame("D:\\Games\\savegames\\save3.dat", gameProgress3);

        List<String> files = new ArrayList<>();
        files.add("D:\\Games\\savegames\\save1.dat");
        files.add("D:\\Games\\savegames\\save2.dat");
        files.add("D:\\Games\\savegames\\save3.dat");
        zipFiles("D:\\Games\\savegames\\zip.zip", files);

    }

    private static void saveGame(String path, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            // запишем экземпляр класса в файл
            oos.writeObject(gameProgress);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void zipFiles(String distinationPath, List <String> files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(distinationPath))) {
            for (String file: files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    File filewr = new File(file);
                    ZipEntry entry = new ZipEntry(filewr.getName());
                    zout.putNextEntry(entry);
                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    // добавляем содержимое к архиву
                    zout.write(buffer);

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }


            }
            // закрываем текущую запись для новой записи
            zout.closeEntry();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        //удаление файлов
        for (String file: files) {
            File filewr = new File(file);
            if (filewr.delete())
                System.out.println("Файл удалён");
            else System.err.println("Файл нельзя удалить");
        }
    }
}
