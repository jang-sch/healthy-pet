package a7.mwisehart.mobiletodo;

public class Item {
    private final String desc;

    public String getDesc() {
        return desc;
    }

    public Item(String desc){
        this.desc = desc;
    }
    @Override
    public String toString(){ return this.getDesc();}
}
