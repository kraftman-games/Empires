package es.themin.empires.cores;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Schematic
{
 
    private byte[] blocks;
    private byte[] data;
    private short width;
    private short lenght;
    private short height;
 
    public Schematic(byte[] blocks, byte[] data, short width, short lenght, short height)
    {
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.lenght = lenght;
        this.height = height;
    }
 

    public byte[] getBlocks()
    {
        return blocks;
    }
 
    public byte[] getData()
    {
        return data;
    }
 
    public short getWidth()
    {
        return width;
    }
 
    public short getLenght()
    {
        return lenght;
    }
 
    public short getHeight()
    {
        return height;
    }
   
	public  void paste(Location loc) {
        byte[] blocks = getBlocks();
        byte[] blockData = getData();
 
        short length = getLenght();
        short width = getWidth();
        short height = getHeight();
        
        World world = loc.getWorld();
        
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < length; ++z) {
                    int index = y * width * length + z * width + x;
                    Block block = new Location(world, x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
                    block.setTypeIdAndData(blocks[index], blockData[index], true);
                }
            }
        }
    }
	public  void pasteWithoutAir(Location loc) {
        byte[] blocks = getBlocks();
        byte[] blockData = getData();
 
        short length = getLenght();
        short width = getWidth();
        short height = getHeight();
        
        World world = loc.getWorld();
        
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < length; ++z) {
                    int index = y * width * length + z * width + x;
                    Block block = new Location(world, x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
                    if (blocks[index] != 0)block.setTypeIdAndData(blocks[index], blockData[index], true);
                }
            }
        }
    }
}