package Model.Containers;

import java.io.BufferedReader;
import java.io.IOException;

public class MyFileTable extends MyDictionary<Integer,Pair<String,BufferedReader>>{

    public boolean hasValue(String val) {
        for (Integer key: dict.keySet()) {
            Pair<String,BufferedReader> tmp = dict.get(key);
            if(tmp.left()==val)
                return true;
        }

        return false;
    }

    public void closeFile(Integer fid) {
        try {
            dict.get(fid).right().close();
        } catch (IOException e) {
            System.out.println("closing the file went wrong");
            e.printStackTrace();
        }
        dict.remove(fid);
    }
}
