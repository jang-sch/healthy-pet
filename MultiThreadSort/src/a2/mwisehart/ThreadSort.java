package a2.mwisehart;

import a1.mwisehart.Item;

import java.util.Random;

public class ThreadSort extends Thread
{
    private Item[] tItems;

    public ThreadSort(Item[] items, int lowBounds, int upperBounds)
    {
        this.tItems = new Item[upperBounds - lowBounds];

        System.arraycopy(items, lowBounds, this.tItems, 0, (upperBounds - lowBounds));
    }

    @Override
    public void run()
    {
        System.out.println("Thread started");
        int n = this.tItems.length;
        Item tmp;
        for(int i = 0; i< n; i++)
        {
            for(int j = 1; j < n; j++)
            {
                if(this.tItems[j-1].getPrice() > this.tItems[j].getPrice())
                {
                    tmp = this.tItems[j-1];
                    this.tItems[j-1] = this.tItems[j];
                    this.tItems[j] = tmp;
                }
            }
        }
        System.out.println("Thread is complete");
    }

    public Item[] gettItems()
    {
        return tItems;
    }
}
