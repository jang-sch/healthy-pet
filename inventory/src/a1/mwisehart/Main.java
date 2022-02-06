
package a1.mwisehart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        Random ran = new Random();
        List<Item> items = new ArrayList<>();
        FoodItems[] foodItems = FoodItems.values();
        Tools[] tools = Tools.values();
        ToolUses[] toolUses = ToolUses.values();
        LordranItemList[] lordranItemList = LordranItemList.values();
        LordranItemUses[] lordranItemUses = LordranItemUses.values();
        System.out.print("How many items do you want?");
        int itemCnt = Integer.parseInt(scan.nextLine());

        for(int i = 0; i < itemCnt; i++)
        {
            int type = ran.nextInt(3);
            switch (type) {
                case 0 -> {
                    int foodIndex = ran.nextInt(foodItems.length);
                    String foodName = foodItems[foodIndex].toString();
                    float foodPrice = ran.nextFloat(10);
                    int foodQty = ran.nextInt(30);
                    int foodUses = ran.nextInt(6);
                    float healthGain = ran.nextInt(20);
                    Food tmpFood = new Food(foodName, foodPrice, foodQty, foodUses, healthGain);
                    items.add(tmpFood);
                    break;
                }
                case 1 -> {
                    int toolIndex = ran.nextInt(tools.length);
                    String toolName = tools[toolIndex].toString();
                    float toolPrice = ran.nextFloat(200);
                    int toolQty = ran.nextInt(15);
                    String use = toolUses[toolIndex].toString();
                    Tool tmpTool = new Tool(toolName, toolPrice, toolQty, use);
                    items.add(tmpTool);
                    break;

                }
                case 2 -> {
                    int lordranItemIndex = ran.nextInt(lordranItemList.length);
                    String lordranItemName = lordranItemList[lordranItemIndex].toString();
                    double itemPrice = ran.nextInt(5000);
                    int itemQty = ran.nextInt(5);
                    String uses = lordranItemUses[lordranItemIndex].toString();
                    LordranItems tmpItems = new LordranItems(lordranItemName, itemPrice, itemQty, uses);
                    items.add(tmpItems);
                    break;

                }
            }
        }

        for(Item i : items )
        {
            System.out.println(i);

        }
    }
}
