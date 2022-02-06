
package a1.mwisehart;

public class Item
{
    private double price;
    private int qty;
    private String name;

    public Item()
    {
        this.price = 0;
        this.qty = 0;
        this.name = "Item";
    }

    public Item(String name, double price, int qty)
    {
        this.price = price;
        this.qty = qty;
        this.name = name;
    }

    public double getPrice() //returns our price and can be called from object
    {
        return price;
    }

    public void setPrice(double price) //sets price and can be called from object
    {
        this.price = Math.max(price, 0);
        /*Math.max returns the largest of the zero or more numbers passed as input
        parameters or NaN if any param isn't a number and can't be converted.
        In our case as long as price is set bigger than 0 it will return price.
        */
    }

    public int getqty()
    {
        return qty;
    }

    public void setqty(int qty)
    {
        this.qty = Math.max(qty, 0);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return String.format("| %-20s | $%-10.2f | %-6d | ", this.name, this.price, this.qty);
    }
}
