package lab;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class Wardrobe extends ArrayList<Hat> implements Comparator<Hat> {

    private Date createdDate =new Date();

    /**
     * Сравнивает две шляпы по размеру
     * @param a Шляпа которую нужно сравнить
     * @param b Шляпа с которой нужно сравнить
     * @return положительное число, если первая шляпа больше; отрицательное если меньше; ноль, если шляпы равны
     */
    public int compare(Hat a, Hat b){
    return a.size-b.size;
    }

    /**
     * Находит наименьшую шляпу в гардеробе
     * @return индекс наименьшей шляпы
     */
    private int findmin(){
        int min=0;
        for (int i=1; i < this.size()-1; ++i){
            if (compare(this.get(min), this.get(i))<0){min=i;}
        }
        return min;
    }

    /**
     * Добавляет шляпу в гардероб
     * @param a Шляпа, которую нужно добавить
     * @return true, если шляпа успешно добавлена
     */
    public boolean add(Hat a){
        if (!(a.color.equals(""))){
        super.add(new Hat(a.size, a.color, a.content));
        return true;}
        else{return false;}
    }

    /**
     * Выводит весь гардероб.
     * Цвет и размер каждой из шляп и их содержимое
     */
    void show(){
        for (int i = 0; i < this.size(); i++){
            (this.get(i)).showHat();
        }
        if (this==null){System.out.println("гардероб пуст");}
    }

    /**
     * Сохраняет гардероб в файл
     * @param filename имя файла, в котрый нужно сохранить
     */
    void save(String filename){
        try{
            File file = new File(filename);
            FileWriter filewriter = new FileWriter(file);
            BufferedWriter bufwriter = new BufferedWriter(filewriter);

            for (int i=0; i<this.size(); ++i) {
                bufwriter.write(this.get(i).size + "," + this.get(i).color + "," + this.get(i).contentlist()+"\n");
            }
            bufwriter.close();
        } catch (Exception e){System.out.println("при записи в файл произошла ошибка");}

    }

    /**
     * Выводит информацию о гардеробе
     */
    void info(){
        System.out.println( "Гардероб - коллекция типа ArrayList содержит " + this.size()+" шляп \n"+
                "создан "+ createdDate);
    }

    /**
     * Загружает гардероб из файла
     * @param filename имя файла из котрого производится загрузка
     */
    void load(String filename){
        int s=this.size();
        int i =0;
        while (i<s){this.remove(this.get(0));
        ++i;}
        try{
            Scanner fileScanner = new Scanner(new FileReader(filename));
            while(fileScanner.hasNextLine()){
              Hat hat = parseCSVLine(fileScanner.nextLine());
              if (!(hat.color.equals(""))){
              this.add(hat);}}
        } catch (IOException e) {
        System.out.println("При загрузке гардероба произошла ошибка");
    }
    }

    /**
     * Удаляет шляпу из гардероба
     * @param a Шляпа, которую нужно удалить
     * @return true если удаление прошло успешно
     */
    boolean remove(Hat a) {
       for (int index = 0; index < this.size(); index++)
                if (((a.size)==(this.get(index).size)) & ((a.color).equals(this.get(index).color))){
                    super.remove(index);
                    return true;
                }
        return false;
    }

    /**
     * Добавляет шляпу в коллекцию, если она меньше каждой из уже имеющихся
     * @param a Шляпа, которую нужно добавить
     * @return true, если успешно добавлена
     */
    boolean addIfMin(Hat a){
        if(compare(a, this.get(findmin()))<0){
            add(a);
            return true;}
        else return false;}

    /**
     * Добавляет предмет в каждую шляпу, подходящую под введенные параметры
     * @param hat Шляпа, в которую нужно добавить предмет
     * @param obj Предмет, который нужно добавить
     */
    void addtohat(Hat hat, String obj){
        if (obj.indexOf(' ') != -1)
            obj = obj.substring(0, obj.indexOf(' '));
        obj=obj.replaceAll("\n", "");
        hat.color = hat.color.replaceAll("\"", "");
        for(int i=0; i<this.size(); ++i){
            if((this.get(i).size==hat.size)&((this.get(i).color).equals(hat.color))){
                try {
                    this.get(i).addthing(new Thing(Item.valueOf(obj)));
                } catch (IllegalArgumentException e) {
                    System.out.println("Введен предмет не из списка/ Название предмета введено с ошибкой(содержит кавычки или другие символы)");
                break;}}
        }
    }

    /**
     * Добавляет предмет во все шляпы
     * @param obj Предмет, который нужно добавить
     */
    void addtoallhats(String obj){
        if (obj.indexOf(' ') != -1)
            obj = obj.substring(0, obj.indexOf(' '));
        obj=obj.replaceAll("\n", "");
        for(int i=0; i<this.size(); ++i){
                try {
                    this.get(i).addthing(new Thing(Item.valueOf(obj)));
                } catch (IllegalArgumentException e) {
                    System.out.println("Введен предмет не из списка/ Название предмета введено с ошибкой(содержит кавычки или другие символы)");
                break;}
        }
    }

    /**
     * Удаляет предмет из всех шляп с заданными параметрами
     * @param hat Шляпа, из которой нужно удалить предмет
     * @param obj Предмет, который нужно удалить
     */
    void deletefromhat(Hat hat, String obj){
        if (obj.indexOf(' ') != -1)
            obj = obj.substring(0, obj.indexOf(' '));
        obj=obj.replaceAll("\n", "");
        hat.color = hat.color.replaceAll("\"", "");
        for(int i=0; i<this.size(); ++i){
            if((this.get(i).size==hat.size)&((this.get(i).color).equals(hat.color))){
                try {
                    this.get(i).deletething(new Thing(Item.valueOf(obj)));
                } catch (IllegalArgumentException e) {
                    System.out.println("Введен предмет не из списка/ Название предмета введено с ошибкой(содержит кавычки или другие символы)");
                break;}}
        }
    }

    /**
     * Удаляет предмет из всех шляп
     * @param obj Предмет, который нужно удалить
     */
    void deletefromallhats(String obj){
        if (obj.indexOf(' ') != -1)
            obj = obj.substring(0, obj.indexOf(' '));
        obj=obj.replaceAll("\n", "");
        for(int i=0; i<this.size(); ++i){
            try {
                    this.get(i).deletething(new Thing(Item.valueOf(obj)));
                } catch (IllegalArgumentException e) {
                    System.out.println("Введен предмет не из списка/ Название предмета введено с ошибкой(содержит кавычки или другие символы)");
            break;}
        }
    }

    /**
     * Выводит список всехдоступных комманд
     */
    void help() {
        System.out.println("Вы можете сделать следующее: \n" +
                "добавить новую шляпу в гардероб(add); \n" +
                "удалить шляпу из гардероба(remove);\n" +
                "загрузить гардероб из файла(load); \n" +
                "сохранить текущий гардероб в файл(save);\n " +
                "посмотреть содержимое всего гардероба(show);\n" +
                "посмотреть информацию о гардеробе(info);\n" +
                "добавить шляпу если она меньше уже имеющихся(addhat);\n" +
                "добавить предмет в шляпу с заданными параметрами(addthing)\n" +
                "добавить предмет во все шляпы(addthingtoall)\n" +
                "удалить предмет из шляпы с заданнымии параметрами(deletething)\n" +
                "удалить предмет из всех шляп(deletethingfromall)\n" +
                "включить/выключить многострочный ввод(multiline)\n"+
                "Вы также увидите подсказку по выбранной команде.\n" +
                "Если вы хотите завершить выполнение программы, введите exit.\n" +
                "Гардероб будет автоматически сохранен в файл.");
    }

    private static Hat parseCSVLine(String textline) {
        String line = textline.replaceAll("\"", "");
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter("\\s*,\\s*");
        if (scanner.hasNextInt()) {
            int size = scanner.nextInt();
            if (scanner.hasNext()) {
                String color = scanner.next();
                Hat hat = new Hat(size, color);
                if (scanner.hasNextLine()) {
                    String content = scanner.nextLine();
                    if (content != null) {
                        Scanner stscanner = new Scanner(content);
                        while (hat.checkspace() != -1) {
                            if (stscanner.hasNext()) {
                                String item = stscanner.next();
                                try {
                                    hat.addthing(new Thing(Item.valueOf(item.replaceAll(",", ""))));
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Введен предмет не из списка/ Название предмета введено с ошибкой(содержит кавычки или другие символы)");
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
                return hat;
            }
         else {
            System.out.println("неверно введены данные. шляпа не может быть создана, т.к. отсутствует параметр color");
        }
    }else {System.out.println("неверно введены данные. шляпа не может быть создана, т.к. отсутствует параметр size"); }
    return new Hat(0,"");}
}
