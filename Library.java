import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private ArrayList<Item> ItemList=new ArrayList<>();

    public Library(){
        }

    public String[][] loadFromFile(){
        try {
            File myObj = new File("libinput.txt");
            Scanner myReader = new Scanner(myObj);

            String[][] Sdata=new String[3][3];
            int i=0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts=data.split(",");
                for(int j=0;j<3;j++)
                {
                    Sdata[i][j]=parts[j];
                }
                i++;


                    Item nn=new book(parts[0],parts[1],Integer.parseInt(parts[2]),0,0);
                    ItemList.add(nn);
            }
            myReader.close();
            return Sdata;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    return null;}

    public void displayMenu(){
        Scanner myobj=new Scanner(System.in);
        int choice=-1;
        while(choice!=0){
            System.out.println("\n LIBRARY MANAGEMNT SYSTEM MENU :\n1. Hot picks ! \n2. Borrow an item\n3. Add Item");
            System.out.println("4. Edit Item\n5. Delete item \n6. View all items\n7. View item by id \n8. view borrowers list\n9. return item \n 0. Exit \n Enter Choice: ");
            choice=myobj.nextInt();
            if(choice==1)
            {
                hotpics();

            }
            else if(choice==2)
            {

            }
            else if(choice==3)
            {
                additem();

            }
            else if(choice==4)
            {
                editItem();
                //  choice=0;
            }
            else if(choice==5)
            {
                deleteitem();
                //  choice=0;
            }
            else if(choice==6)
            {
                viewAll();
                // choice=0;
            }
            else if(choice==7)
            {
                viewById();
                // choice=0;
            }
            else if(choice>9||choice<0)
            {
                System.out.println("INVALID RENTER");
                choice=0;
            }
        }}

    public void additem(){
        Scanner myobj=new Scanner(System.in);

            Scanner myobj1=new Scanner(System.in);
            System.out.print("\n Enter book title : ");
            String title=myobj1.nextLine();
            System.out.print("\n Enter Author name : ");
            String author=myobj1.nextLine();
            System.out.print("\nEnter publishing year : ");
            int year=myobj.nextInt();
            System.out.print("\nEnter popularity count : ");
            int popc=myobj.nextInt();
            System.out.print("\nEnter Cost : ");
            int c=myobj.nextInt();
            Item nn=new book(title,author,year,popc,c);
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

