package BattleShip;

public class Cruiser extends Ship
{
    public Cruiser(String n)
    {
        super(n);
    }
    public char drawShipStatusAtCell(boolean isDamaged)
    {
        if(isDamaged == true){ return 'c'; }
        else{ return 'C'; }
    }
    public int getLength()
    {
        return 4;
    }
}