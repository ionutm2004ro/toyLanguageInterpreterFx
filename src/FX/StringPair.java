package FX;

import javafx.beans.property.SimpleStringProperty;

public class StringPair {
    private final SimpleStringProperty string1;
    private final SimpleStringProperty string2;

    public StringPair(String s1,String s2){
        super();
        this.string1 = new SimpleStringProperty(s1);
        this.string2 = new SimpleStringProperty(s2);
    }

    public String getString1(){
        return string1.get();
    }
    public String getString2(){
        return string2.get();
    }
}
