package Standard;

/**
 * Created by Robert on 08-Nov-15.
 */
public enum APP {
    TITLE ("Youtube sync client");

    private final String s;

    APP(String s){
        this.s = s;

    }

    public String getText(){
        return s;
    }

}
