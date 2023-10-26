import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {
    private ArrayList<Item> ItemList=new ArrayList<>();

    public Library(){
        }

    public String[][] loadFromFile(){
        try {
            String fileName = "libinput.txt"; // Make sure the file name is correct
            List<String[]> lines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        lines.add(parts);
                    } else {
                        // Handle lines with incorrect format, log or skip them
                    }
                }
            }

            int noOfLines = lines.size();
            String[][] Sdata = lines.toArray(new String[noOfLines][4]);
            return Sdata;

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;}
    public void additem(String title,String author,int year){

            Item nn=new book(title,author,year,0);
            ItemList.add(nn);
        }

    public void editItem(){
        Scanner myobj=new Scanner(System.in);
        System.out.println("To edit item enter ID : ");
        int id=myobj.nextInt();
        boolean check1=false;
        for(int i=0;i<ItemList.size();i++)
        {
            if(ItemList.get(i).getID()==id)
            {
                System.out.println("Item information  : ");
                ItemList.get(i).display();
                Scanner myobj1=new Scanner(System.in);
                String nn=myobj1.nextLine();
                ItemList.get(i).setTitle(nn);
                System.out.println("UPDATED ");
                check1=true;

            }
        }
        if(check1==false)
        {
            System.out.println("ITEM NOT FOUND ID:"+id);
        }}

    public void deleteitem(){
        Scanner myobj=new Scanner(System.in);
        System.out.println("To delete item enter ID : ");
        int id=myobj.nextInt();
        boolean check=false;
        for(int i=0;i<ItemList.size();i++)
        {
            if(ItemList.get(i).getID()==id)
            {
                if(ItemList.get(i).getBorrow()==false){
                    ItemList.remove(i);
                    System.out.println("ITEM DELETED ");
                    check=true;
                }
                else
                {
                    System.out.println("ITEM is currently borrowed \n Return itme to delete ");
                }
            }
        }
        if(check==false)
        {
            System.out.println("ITEM NOT FOUND ID:"+id);
        }
    }

    public void viewAll()
    {
        for(int i=0;i<ItemList.size();i++)
        {
            ItemList.get(i).display();
        }
    }
    public void viewById()
    {
        Scanner myobj=new Scanner(System.in);
        System.out.print("Enter ID to view Item : ");
        int id=myobj.nextInt();
        boolean check=false;
        for(int i=0;i<ItemList.size();i++)
        {
            if(ItemList.get(i).getID()==id)
            {
                ItemList.get(i).display();
                check=true;
            }
        }
        if(check==false)
        {
            System.out.println("ITEM NOT FOUND ID:"+id);
        }

    }
    public void viewByitem(Item item){
        item.display();}


    public void hotpics(){
        int n = ItemList.size();
        System.out.println("\nHOT PICS");
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ItemList.get(j).popularityCount < ItemList.get(j + 1).popularityCount) {
                    // Swap elements if they are out of order
                    Item temp = ItemList.get(j);
                    ItemList.set(j, ItemList.get(j + 1));
                    ItemList.set(j + 1, temp);
                }
            }
        }

        viewAll();
    }



}

