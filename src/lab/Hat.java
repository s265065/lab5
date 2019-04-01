package lab;

import lab.json.*;

public class Hat  {
    String color;
    int size;
    Thing content[];

    /**
     * Добавляет предмет в шляпу
     * @param obj предмет, который нужно добавить
     */
    void addthing(Thing obj){
        if (checkspace()!=-1){
            if (checkitem(obj)==-1){
            System.out.println("Объект " + obj.rus(obj.name.toString()) + " был успешно добавлен в шляпу.");
            this.content[checkspace()]=obj;}
            else {System.out.println("Объект " + obj.rus(obj.name.toString()) +" уже есть в этой шляпе");}
        }
        else {
            System.out.println("В шляпе не осталось места. Пожалуйста удалите какой-нибудь предмет прежде чем добавлять новый.\n" +
                    "Объект" + obj.rus(obj.name.toString()) + "не был добавлен в шляпу.");
        }
    }

    /**
     * Проверяет есть ли в шляпе свободное место
     * @return индекс ближайшей свободной ячейки; -1, если свободного места не осталось
     */
    int checkspace(){
        for (int i=0; i < this.size; i++){
            if (this.content[i]==null){return i;}
        }
        return -1;
    }

    /**
     * Метод для того чтобы узнать только содержимое шляпы
     * @return строку в которой перечисленно все содержимое шляпы
     */
    String contentlist(){
        String result = "";
        for (int i=0; i < this.size; i++) {
            if (this.content[i]!=null)
                result = result + this.content[i].name.toString() + " ";
        }
        return result;
    }

    /**
     * Проверяет есть ли заданный предмет в шляпе
     * @param item предмет, наличие которого нужно проверить
     * @return индекс найденного предмета; -1, если предмета в шляпе нет
     */
    private int checkitem(Thing item){
        for (int i=0; i < this.size; i++){
            if (this.content[i]!=null)
                if ((this.content[i].name).equals(item.name)){return i;}
        }
        return -1;
    }

    /**
     * Удаляет предмет из шляпы
     * @param obj предмет, который нужно удалить
     */
    void deletething(Thing obj) {
        for (int i=0; i < this.size; i++){
            if (this.content[i]!=null)
                if ((this.content[i].name).equals(obj.name)){this.content[i]=null;}
        }
    }

    /**
     * Выводит информацию о шляпе: размер, цвет и содержимое
      */
    void showHat(){
        System.out.println("Размер шляпы "+this.size+"; Цвет шляпы "+ this.color+";");
        for (int i=0; i < this.size; i++){
            if (this.content[i]!=null)
                System.out.print("В шляпе лежит "+this.content[i].rus(this.content[i].name.toString())+"\n ");
        }
    }

    Hat(int a, String c){
        this.size=a;
        this.color=c;
        this.content= new Thing[a];
    }

    Hat(int a, String c, Thing arr[]){
        this.size=a;
        this.color=c;
        this.content=arr;
    }

    Hat(int a, JSONString c){
        this.size=a;
        this.color=c.toString();
        this.content= new Thing[a];
    }
}

