package BattleShip;

public class Destroyer extends Ship
{
    public Destroyer(String n)
    {
        super(n);
    }
    public char drawShipStatusAtCell(boolean isDamaged)
    {
        if(isDamaged == true){ return 'd'; }
        else{ return 'D'; }
    }
    public int getLength()
    {
        return 3;
    }
}