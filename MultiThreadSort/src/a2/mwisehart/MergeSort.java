package a2.mwisehart;

import a1.mwisehart.Item;

public class MergeSort extends Thread
{
    private Item[] item1;
    private Item[] item2;
    private Item[] sortedItems;

    public MergeSort(Item[] i1, Item[] i2)
    {
        this.item1 = i1;
        this.item2 = i2;
        this.sortedItems = new Item[i1.length + i2.length];
    }

    @Override
    public void run()
    {
        System.out.println("Merge started");
        int i = 0; // current index of item1
        int j = 0; // current index of item2
        int k = 0; // current index of sorted items

        while(i < item1.length && j < item2.length)
        {
            if(this.item1[i].getPrice() < this.item2[j].getPrice())
            {
                this.sortedItems[k++] = this.item1[i++];
            }
            else
            {
                this.sortedItems[k++] = this.item2[j++];
            }
        }

        while(i < this.item1.length)
        {
            this.sortedItems[k++] = this.item1[i++];
        }
        while(j > this.item2.length)
        {
            this.sortedItems[k++] = this.item2[j++];
        }

        System.out.println("Merge complete");

    }

    public Item[] getSortedItems() {
        return sortedItems;
    }
}
