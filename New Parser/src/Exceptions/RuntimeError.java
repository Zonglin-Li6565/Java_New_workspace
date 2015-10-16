package Exceptions;

/**
 * Created by Zonglin Li on 8/2/2015.
 */

public class RuntimeError extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    String location;

    public RuntimeError(String location){
        this.location = location;
    }

    public RuntimeError(){

    }

    public String toString(){
        if(location != null){
            return "Run time error at: " + location;
        }else {
            return "Run time error";
        }
    }
}
