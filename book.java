public class book extends Item implements configuration{
    public static int nextID=0;
    public String author;
    public int year;

    public book(String t, String a,int y,int c)
    {
        super(t,0,c);

        nextID++;
        author=a;
        year=y;

    }

    public void display()
    {
        super.display();
        System.out.println(" by "+author+" ("+year+")");
    }
    @Override
    public int calculateCost()
    {
        int c=(int) (0.2*cost);

        return cost+(c)+200;

    }

}