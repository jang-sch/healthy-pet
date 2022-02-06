package a1.mwisehart;

public class LordranItems extends Item
{
    private String usesRem;

    public LordranItems()
    {
        super();
        this.usesRem = "";
    }

    public LordranItems(String name, double price, int qty, String usesRem)
    {
        super(name, price, qty);
        this.usesRem = usesRem;

    }

    public String getUsesRem()
    {
        return usesRem;
    }

    public void setUsesRem(String usesRem)
    {
        this.usesRem = usesRem;
    }

    @Override
    public String toString()
    {
        return String.format("%s %7s %-20s |", super.toString(), "|", this.usesRem);
    }

}
