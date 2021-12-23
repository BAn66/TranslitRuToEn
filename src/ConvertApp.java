import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConvertApp extends JFrame { //Наследуя от JFrame мы получаем всю функциональность окна
    String path = "ВЫБЕРЕТЕ ПУТЬ К ПАПКЕ";
    private JLabel pathToFolder;
    private JButton browse;
    private JButton convert;
    private  JFileChooser fileChooser;

    public ConvertApp() {

        super("Транслитерация из киррилицы в латиницу"); //Заголовок окна
        //setBounds(100, 100, 400, 100); //Если не выставить размер и положени то окно будет мелкое и незаметное
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //это нужно для того чтобы при закрытии окна закрывалась и программа,иначе она останется висеть в процессах
        setSize(250,250);
        // Панель содержимого
        Container container = getContentPane();
        /*
         *  Размещаем в панели компоненты
         *  В качестве параметров можно использовать
         *  строки и константы класса BorderLayout
         */

        pathToFolder = new JLabel("Путь: " + path);
        browse = new JButton("Обзор");
        convert = new JButton("Транслитерация");

        container.add(pathToFolder, BorderLayout.NORTH);
        container.add(browse, BorderLayout.WEST);
        container.add(convert, BorderLayout.EAST);
        
//        //Подготавливаем временные компоненты
//        JPanel buttonsPanel = new JPanel(new FlowLayout());
//        //Расставляем компоненты по местам
//        // ПОКА НЕ РАССТАВЛЕНЫ РАЗОБРАТЬСЯ
//        buttonsPanel.add(pathToFolder, BorderLayout.SOUTH);
//        buttonsPanel.add(browse, BorderLayout.NORTH);
//        buttonsPanel.add(convert, BorderLayout.NORTH);
//        add(buttonsPanel, BorderLayout.NORTH);

        // Создание экземпляра JFileChooser для открытия окна
        fileChooser = new JFileChooser();

        /**Листенер для кнопки Обзор**/
        browse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Выбор директории");
                // Определение режима - только каталог
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(ConvertApp.this);
                // Если директория выбрана, покажем ее в сообщении
                if (result == JFileChooser.APPROVE_OPTION )
                    JOptionPane.showMessageDialog(ConvertApp.this,
                            fileChooser.getSelectedFile()); // всплывашка о выбранной папке
                     path = fileChooser.getSelectedFile().getPath(); //присваеваем путь переменной
                     updatePath(); //обновляем сведения в окне
            }
        });

        /**Листенер для кнопки Конвертирования**/
        convert.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Convertation convertation = new Convertation(path);
                convertation.setPath(path);
                convertation.convert();
                JOptionPane.showMessageDialog(ConvertApp.this,
                        "Транслитирация в выбранной папке закончена");
            }
        });

    }
    /** Метод обновление отображения пути в окне**/
    private void updatePath(){ //
        pathToFolder.setText("Путь: " + path);
    }

    public static void main(String[] args) {
        ConvertApp app = new ConvertApp(); //Создаем экземпляр нашего приложения
        app.setVisible(true); //С этого момента приложение запущено!
        //app.pack(); //Эта команда подбирает оптимальный размер в зависимости от содержимого окна
    }

}
