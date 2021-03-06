import java.io.File;
import java.nio.file.*;
import java.util.*;

public class Convertation {
    private String pathStr;
    private final HashMap<String, String> alphabit;


    public Convertation(String pathStr) {
        this.pathStr = pathStr;
        this.alphabit = new AlphabitRuEnTranslit().getAlphabit();
    }

    public String getPath() {
        return pathStr;
    }
    public void setPath(String path) { this.pathStr = path; }

    public void convert() {//Основной алгоритм

        Path path = Paths.get(getPath());
        File myFolder = new File(getPath());
        File[] files = myFolder.listFiles();
        if (files != null) { //проверка на пустой массив
            File file;
            for (int f = 0; f < files.length; f++) {
                file = files[f];
                if (file.isFile()) { // Если файл это файл
                    String newNameFile = ""; //Временная переменная для нового имени
                    String nameWithoutFileExtension = "";//имя файла без расширения
                    String fileExtension = "";//расширение файла
                    //Этот кусок кода переделывает строку из имени файла с русского на транслит
                    fileExtension = file.getName().substring(file.getName().length() - 3, file.getName().length()); //вычленяем расширение РАБОТАЕТ ТОЛЬКО С РАСШИРЕНИЕМ ИЗ 3 БУКВ
                    nameWithoutFileExtension = file.getName().substring(0, file.getName().length() - 4); //вычленяем имя файла без расширения РАБОТАЕТ ТОЛЬКО С РАСШИРЕНИЕМ ИЗ 3 БУКВ
                    char[] symbolsNameFile = nameWithoutFileExtension.toCharArray();
                    for (int i = 0; i < symbolsNameFile.length; i++) {
                        boolean isUpper = Character.isUpperCase(symbolsNameFile[i]);//Проверка на заглавную букву
                        String OldSymbolInNameFile = String.valueOf(symbolsNameFile[i]);
                        String NewSymbolInNameFile = "";

                        for (Map.Entry<String, String> pair : alphabit.entrySet()) {
                            String key = pair.getKey();                      //ключ
                            String value = pair.getValue();                  //значение
                            if (OldSymbolInNameFile.toLowerCase().equals(key)) {
                                if (isUpper) { //Проверка на заглавную букву
                                    NewSymbolInNameFile = value.toUpperCase();
                                } else NewSymbolInNameFile = value;
                                break;
                            } else NewSymbolInNameFile = OldSymbolInNameFile;
                        }
                        newNameFile = newNameFile + NewSymbolInNameFile;
                    }

                    File newFile = new File(file.getParent(), newNameFile + "." + fileExtension);
                    file.renameTo(newFile);

                } else { // Если файл не файл, а папка
                    String newNameFile = "";
                    char[] symbolsNameFile = file.getName().toCharArray();
                    // fileToConvert
                    for (int i = 0; i < symbolsNameFile.length; i++) {
                        boolean isUpper = Character.isUpperCase(symbolsNameFile[i]);//Проверка на заглавную букву
                        String OldSymbolInNameFile = String.valueOf(symbolsNameFile[i]);
                        String NewSymbolInNameFile = "";
                        for (Map.Entry<String, String> pair : alphabit.entrySet()) {
                            String key = pair.getKey();                      //ключ
                            String value = pair.getValue();                  //значение
                            if (OldSymbolInNameFile.toLowerCase().equals(key)) {
                                if (isUpper) { //Проверка на заглавную букву
                                    NewSymbolInNameFile = value.toUpperCase();
                                } else NewSymbolInNameFile = value;
                                break;
                            } else NewSymbolInNameFile = OldSymbolInNameFile;
                        }
                        newNameFile = newNameFile + NewSymbolInNameFile;
                    }

                    File newFile = new File(file.getParent(), newNameFile);
                    file.renameTo(newFile);
                }

            }
        }

    }

}
