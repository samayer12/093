package BattleShip;

public class Destroyer extends Ship
{
    public Destroyer(String n)
    {
        super(n);
    }
    public char drawShipStatusAtCell(boolean isDamaged)
    {
        if(isDamaged){
            return 'D';
        }
        else if (!isDamaged){
            return 'd';
        }
    }
    public int getLength()
    {
        return position.size();
    }
}