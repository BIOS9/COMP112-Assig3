import java.awt.*;

public class Flag
{
    public String Name;
    public Color[] Colors;
    public boolean Vertical;

    public Flag(String Name, boolean Vertical, Color... Colors)
    {
        this.Name = Name;
        this.Vertical = Vertical;
        this.Colors = Colors;
    }
}