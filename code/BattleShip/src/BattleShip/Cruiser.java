package BattleShip;

public class Cruiser extends Ship
{
    public Cruiser(String n)
    {
        super(n);
    }
    public char drawShipStatusAtCell(boolean isDamaged)
    {
        if(isDamaged){
            return 'C';
        }
        else if (!isDamaged){
            return 'c';
        }
    }
    public int getLength()
    {
        return position.size();
    }
}