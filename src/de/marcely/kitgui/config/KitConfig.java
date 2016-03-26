package de.marcely.kitgui.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.marcely.kitgui.Kit;

public class KitConfig implements Serializable {
	private static final long serialVersionUID = -1266449831520034396L;
	private ArrayList<Kit> kits = new ArrayList<Kit>();
	
	public KitConfig(){ }
	
	public int addLore(String kitname, String lore){
		return getKit(kitname).addLore(lore);
	}
	
	public void setIcon(String kitname, Material icon, int id){
		if(contains(kitname)){
			getKit(kitname).setIcon(new ItemStack(icon, 1, (short) id));
		}else{
			Kit kit = new Kit(kitname, new ItemStack(icon, 1, (short) id));
			kits.add(kit);
		}
	}
	
	public void setPrefix(String kitname, String prefix){
		if(contains(kitname)){
			getKit(kitname).setPrefix(prefix);
		}else{
			Kit kit = new Kit(kitname, new ItemStack(Material.CLAY_BALL), prefix);
			kits.add(kit);
		}
	}
	
	public Kit getKit(String name){
		for(Kit kit:kits){
			if(kit.getName().equalsIgnoreCase(name))
				return kit;
		}
		return null;
	}
	
	public ItemStack getIcon(String kitname){
		return getKit(kitname).getIcon();
	}
	
	public String getPrefix(String kitname){
		return getKit(kitname).getPrefix();
	}
	
	public String getLore(String kitname, int line){
		return getKit(kitname).getLore(line);
	}
	
	public boolean removeLore(String kitname, int line){
		return getKit(kitname).removeLore(line);
	}
	
	public boolean contains(String kitname){
		return getKit(kitname) != null;
	}
	
	public static boolean exists(){
		return new File("plugins/Essentials_KitGUI/kits.cfg").exists();
	}
	
	public static KitConfig load(){
		FileInputStream file = null;
		try {
			file = new FileInputStream("plugins/Essentials_KitGUI/kits.cfg");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ObjectInputStream load = null;
		try {
			load = new ObjectInputStream(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		KitConfig config = null;
		try {
			config = (KitConfig)load.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			load.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}
	
	public static void save(KitConfig config){
		FileOutputStream file = null;
		try {
			file = new FileOutputStream("plugins/Essentials_KitGUI/kits.cfg");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ObjectOutputStream save = null;
		try {
			save = new ObjectOutputStream(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			save.writeObject(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			save.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}