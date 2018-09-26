package Takenoko.Util;

import java.io.IOException;
import java.io.OutputStreamWriter;

public enum Console {
    Log;

    private Mode mode = Mode.TEST;
    private OutputStreamWriter out;

    public enum Mode{
        PROD, DEBUG, TEST;
    }

    public Mode getModeFromLabel(String str){
        switch (str){
            case "release" : return Mode.PROD;
            case "debug" : return Mode.DEBUG;
            case "test" : return Mode.TEST;
            default: return Mode.PROD;
        }
    }

    public void init(Mode mode){
        this.mode = mode;
        this.out = new OutputStreamWriter(System.out);
    }

    public Boolean IS_DEBUG_MODE(){
        return (this.mode == Mode.DEBUG);
    }

    public void print(String str){
        if (this.mode != Mode.TEST) {
                try {
                    out.append(str);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    public void println(String str){
        print(str + "\n");
    }

    public void debugPrint(String str){
        if (IS_DEBUG_MODE()) {
            print(str);
        }
    }

    public void debugPrintln(String str){
        debugPrint(str + "\n");
    }

}
