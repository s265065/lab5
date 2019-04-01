package lab;

import java.io.*;
import static lab.HatMaker.makeHatFromJSON;


public class Main {
    private static String autosave = "autosave.csv";
    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException ignored) {}
        boolean multiline = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String filein = "";
        String fileout = "";
        if (args.length>0){
            filein = args[0];
            if (args.length>1){fileout = args[1];}
            else {fileout=filein;
            System.out.println("Файл для сохранения не указан, команда save будет сохранять гардероб в исходный файл");}
        }else {fileout=autosave;
        filein=autosave;
        System.out.println("файлы для загрузки и сохраения не указаны. команды save и load будут работать с файлом автосохранения");}

        Wardrobe ward = new Wardrobe();
        ward.load(filein);
        ward.help();
        while (true) {
            try {
                System.out.print("> ");
                String query = multiline ? getMultilineCommand(reader) : reader.readLine();
                if (!check(query)){
                String command = extractCommand(query);
                switch(command.replaceAll("\n", "")){
                    case "add": {
                        System.out.println("Здесь вы можете добавить новую шляпу в гардероб. Чтобы сделать это пожалуйста введите текст в формате json так, как представленно на примере \n " +
                                "\"{\"size\": <положительное целое число>, \"color\": <строка>}\" \n " +
                                "Если вы хотите создать шляпу, в которой сразу будут лежать каки-либо предметы, вам следует набрать следующий текст:  \n " +
                                " \"{\"size\": <целое положительное число>, \"color\": <строка>, \"contents\": [{\"Itemname\": <строка>}, ... {\"Itemname\": <строка>}]}\" \n " +
                                "Будьте внимательны! Размер шляпы(size) показывает какое количество предметов может в неё поместиться. \n " +
                                "Так, если вы попытаетесь при создании шляпы положить в неё больше предметов, чем позволяет размер, будет создана шляпа в которой будут лежать первые несколько предметов по возможному количеству. \n " +
                                "Оставшиеся будут проигнорированны. \n " +
                                "Кроме того вы можете положить только предметы из этого списка: \n " +
                                "зубная щётка(TOOTHBRUSH), зубной порошок (DENTIFRIECE), мыло (SOAP), полотенце (TOWEL), носовой платок (CHIEF),\n " +
                                " носки (SOCKS), гвоздь (NAIL), проволока (COPPERWIRE).\n  " +
                                "Обязательно используйте английский язык и заглавные буквы при вводе названий предметов как указанно в скобках.\n " +
                                "Каждый из предметов может лежать в шляпе только в одном экземпляре. \n " +
                                "Если какой-то предмет будет введен несколько раз он будет добавлен только один раз.\n " +
                                "Если вы хотите вернуться к меню введите back." );
                        System.out.print("add> ");
                        String text = multiline ? getMultilineCommand(reader) : reader.readLine();
                        if (!check(text)){
                            if (text.equals("back")){
                                System.out.print("");}
                        else {
                            try {
                                ward.add(makeHatFromJSON(text));
                            } catch (Exception e) {
                                System.out.println("Не получилось создать шляпу: " + e.getMessage());}
                        }}
                        break;}
                    case "remove": {  System.out.println("Здесь вы можете удалить шляпу из гардероба. \n " +
                              "Это можно сделать по номеру шляпы или по ее цвету и размеру.\n " +
                            "Чтобы сделать это пожалуйста введите номер шляпы или задайте шляпу в формате json так, как представленно на примере  \n " +
                            "\"{\"size\": <положительное целое число>, \"color\": <строка>}\" \n " +
                            "Если в коллекции несколько шляп с такими характеристиками, будет удалена только первая найденная.\n "+
                            "Если вы хотите вернуться к меню введите back." );
                        System.out.print("remove> ");
                        String text = multiline ? getMultilineCommand(reader) : reader.readLine();
                        if (!check(text)){
                            if (text.equals("back")){
                                System.out.print("");}
                            else { try {
                                ward.remove(makeHatFromJSON(text));
                            } catch (Exception e) {
                                System.out.println("Не получилось удалить шляпу: " + e.getMessage());}
                            }
                        }
                        break;}
                    case "load": {ward.load(filein);
                        break;}
                    case "save": {ward.save(fileout);  break;}
                    case "show": {ward.show();break;}
                    case "info": {ward.info();  break;}
                    case "addifmin": { System.out.println("Здесь вы можете добавить новую шляпу в гардероб. \n" +
                            "Чтобы сделать это пожалуйста введите текст в формате json так, как представленно на примере  \n" +
                            "\"{\"size\": <положительное целое число>, \"color\": <строка>}\" \n" +
                            "Если вы хотите создать шляпу, в которой сразу будут лежать каки-либо предметы, вам следует набрать следующий текст:\n" +
                            " \"{\"size\": <целое положительное число>, \"color\": <строка>, \"contents\": [{\"Itemname\": <строка>}, ... {\"Itemname\": <строка>}]}\" \n" +
                            "Будьте внимательны! Размер шляпы(size) показывает какое количество предметов может в неё поместиться. \n" +
                            "Так, если вы попытаетесь при создании шляпы положить в неё больше предметов, чем позволяет размер, \n" +
                            "будет создана шляпа в которой будут лежать первые несколько предметов по возможному количеству. \n" +
                            "Оставшиеся будут проигнорированны.\n " +
                            "Кроме того вы можете положить только предметы из этого списка:\n " +
                            "зубная щётка(TOOTHBRUSH), зубной порошок (DENTIFRIECE), мыло (SOAP), \n" +
                            "полотенце (TOWEL), носовой платок (CHIEF), носки (SOCKS), гвоздь (NAIL), проволока (COPPERWIRE). \n" +
                            "Обязательно используйте английский язык и заглавные буквы при вводе названий предметов как указанно в скобках.\n" +
                            "Каждый из предметов может лежать в шляпе только в одном экземпляре. \n" +
                            "Если какой-то предмет будет введен несколько раз он будет добавлен только один раз.\n" +
                            "Если вы хотите вернуться к меню введите back.\n" +
                            "Шляпа будет добавлена только в том случа, если ее размер меньше каждой из уже имеющихся шляп" );
                        System.out.print("add if min> ");
                        String text = multiline ? getMultilineCommand(reader) : reader.readLine();
                        if (!check(text)) {
                            if (text.equals("back")) {
                                System.out.print("");
                            } else {
                                try {
                                    ward.addIfMin(makeHatFromJSON(text));
                                } catch (Exception e) {
                                    System.out.println("Не получилось создать шляпу: " + e.getMessage());
                                }
                            }
                        }
                        break;}
                    case "addthing":{System.out.println("Здесь вы можете добавить прдемет в шляпу с заданными характеристиками. \n" +
                            "Внимание! Должны быть указаны обе характеристики(цвет и размер). \n" +
                            "Кроме того можно добавить только один предмет \n" +
                            "Для того чтобы добавить предмет в шляпу вам нужно сначала указать в какую из шляп вы хотите добавить предмет \n" +
                            "Задайте шляпу в формате json: \"{\"size\": <положительное целое число>, \"color\": <строка>}\" " +
                            "После чего завершите ввод нажатием клавиши ENTER или символом \";\" в зависимости от режима ввода." +
                            "Далее введите предмет, который хотите добавить. Помните, что вы можете положить только предметы из этого списка: \n" +
                            "зубная щётка(TOOTHBRUSH), зубной порошок (DENTIFRIECE), мыло (SOAP), полотенце (TOWEL), носовой платок (CHIEF),\n" +
                            " носки (SOCKS), гвоздь (NAIL), проволока (COPPERWIRE). \n" +
                            "Обязательно используйте английский язык и заглавные буквы при вводе названий предметов как указанно в скобках.\n" +
                            "Каждый из предметов может лежать в шляпе только в одном экземпляре. \n" +
                            "Если такой предмет уже лежит в шляпе, он не будет добавлен.\n" +
                            "Также если в гардеробе есть несколько шляп с подходящими параметрами, то предмет будет по возможности добавлен во все шляпы" +
                            "Если вы хотите вернуться к меню введите back.");
                        System.out.print("add thing> ");
                        String text = multiline ? getMultilineCommand(reader) : reader.readLine();
                        String items = multiline ? getMultilineCommand(reader) : reader.readLine();
                        if (!check(text)){
                            if (text.equals("back")){
                                System.out.print("");}
                            else {
                                try {
                                    ward.addtohat(makeHatFromJSON(text), items);
                                    ward.save(autosave);
                                } catch (Exception e) {
                                    System.out.println("Не получилось добавить элемент в шляпу: " + e.getMessage());}
                            }
                        }
                        break;}
                    case "addthingtoall":{System.out.println("Здесь вы можете добавить предмет во все имеющиеся в гардеробе шляпы.\n" +
                            "Для этого введите предмет, который хотите добавить. Помните, что вы можете положить только предметы из этого списка: \n" +
                            "зубная щётка(TOOTHBRUSH), зубной порошок (DENTIFRIECE), мыло (SOAP), полотенце (TOWEL), носовой платок (CHIEF),\n" +
                            "носки (SOCKS), гвоздь (NAIL), проволока (COPPERWIRE). \n" +
                            "Обязательно используйте английский язык и заглавные буквы при вводе названий предметов как указанно в скобках.\n" +
                            "Каждый из предметов может лежать в шляпе только в одном экземпляре. \n" +
                            "Если такой предмет уже лежит в шляпе, он не будет добавлен.\n" +
                            "Если вы хотите вернуться к меню введите back.");
                        System.out.print("add thing to all> ");
                        String text = multiline ? getMultilineCommand(reader) : reader.readLine();
                        if (!check(text)){
                            if (text.equals("back")){
                                System.out.print("");}
                            else {
                                try {
                                    ward.addtoallhats(text);
                                    ward.save(autosave);
                                } catch (Exception e) {
                                    System.out.println("Не получилось добавить элемент в шляпы: " + e.getMessage());}
                            }
                        }
                        break;}
                    case "deletething":{System.out.println("Здесь вы можете удалить предемет из шляпы с заданными характеристиками. \n" +
                            "Внимание! Должны быть указаны обе характеристики(цвет и размер). \n" +
                            "Кроме того можно удалить только один предмет \n" +
                            "Для того чтобы удалить предмет вам нужно сначала указать из какой шляпы вы хотите удалить предмет \n" +
                            "Задайте шляпу в формате json: \"{\"size\": <положительное целое число>, \"color\": <строка>}\" " +
                            "После чего завершите ввод нажатием клавиши ENTER или символом \";\" в зависимости от режима ввода." +
                            "Далее введите предмет, который хотите удалить. Помните, что вы можете удалить только предметы из этого списка: \n" +
                            "зубная щётка(TOOTHBRUSH), зубной порошок (DENTIFRIECE), мыло (SOAP), полотенце (TOWEL), носовой платок (CHIEF),\n" +
                            " носки (SOCKS), гвоздь (NAIL), проволока (COPPERWIRE). \n" +
                            "Обязательно используйте английский язык и заглавные буквы при вводе названий предметов как указанно в скобках.\n" +
                            "Также если в гардеробе есть несколько шляп с подходящими параметрами, то предмет будет удален из всех" +
                            "Если вы хотите вернуться к меню введите back.");
                        System.out.print("delete thing> ");
                        String text = multiline ? getMultilineCommand(reader) : reader.readLine();
                        String items = multiline ? getMultilineCommand(reader) : reader.readLine();
                        if (!check(text)){
                        if (text.equals("back")){
                            System.out.print("");}
                        else {
                            try {
                                ward.deletefromhat(makeHatFromJSON(text), items);
                                ward.save(autosave);
                            } catch (Exception e) {
                                System.out.println("Не получилось удалить элемент из шляпы: " + e.getMessage());}
                        }}
                        break;}
                    case "deletethingfromall":{System.out.println("Здесь вы можете удалить предемет из всех шляп, которые есть в гардеробе. \n" +
                            "Для этого введите предмет, который хотите удалить. Помните, что вы можете удалить только предметы из этого списка: \n" +
                            "зубная щётка(TOOTHBRUSH), зубной порошок (DENTIFRIECE), мыло (SOAP), полотенце (TOWEL), носовой платок (CHIEF),\n" +
                            " носки (SOCKS), гвоздь (NAIL), проволока (COPPERWIRE). \n" +
                            "Обязательно используйте английский язык и заглавные буквы при вводе названий предметов как указанно в скобках.\n" +
                            "Если вы хотите вернуться к меню введите back.");
                        System.out.print("delete thing from all> ");
                        String text = multiline ? getMultilineCommand(reader) : reader.readLine();
                        if (!check(text)){
                        if (text.equals("back")){
                            System.out.print("");}
                        else {
                            try {
                                ward.deletefromallhats(text);
                                ward.save(autosave);
                            } catch (Exception e) {
                                System.out.println("Не получилось удалить элемент из шляп: " + e.getMessage());}
                        }}
                        break;}
                    case "exit": {ward.save(autosave); System.exit(0);break;}
                    case "help": {ward.help(); break;}
                    case "multiline": {
                        if(multiline){
                            multiline=false;
                        System.out.println("многострочный ввод выключен");}
                        else {
                            multiline = true;
                        System.out.println("многострочный ввод включен. Для завершения ввода используйте \";\"");
                        }
                        break;
                    }
                    default: System.out.println("Неверный ввод. Пожалуйста, введите одну из доступных команд.");
                }}
                else {ward.save(autosave);
                    System.exit(0);}
            } catch (IOException e) {
                System.out.println("Не удалось прочитать стандартный поток вввода: " + e.getMessage());
            }
        }
    }

    private static String getMultilineCommand(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        char current;
        boolean inString = false;
        do {
            current = (char)reader.read();
            if (current != ';' || inString)
                builder.append(current);
            if (current == '"')
                inString = !inString;
        } while (current != ';' || inString);
        return builder.toString();
    }

    private static boolean check(String text) {
        if (text==null) {
            return true;
        }else return false;
    }

    private static String extractCommand(String text) {
        int spaceIndex = text.indexOf(' ');
        if (spaceIndex == -1)
            return text;
        else
            return text.substring(0, spaceIndex);
    }
}
