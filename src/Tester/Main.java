package Tester;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;


public class Main {
	/**
	 * Visszaad egy list�t a folder k�nyvt�rban tal�lhat� tesztekr�l
	 * @param folder k�nyvt�r, amiben a teszteket keress�k 
	 **/
	public static ArrayList<String> getTests(File folder) {
		ArrayList<String> tests = new ArrayList<String>();
		
	    for (File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	            String ext = getFileExtension(fileEntry);
	            
	            if (ext != null && "in".compareToIgnoreCase(ext) == 0)
	            	tests.add(removeExtension(fileEntry));
	        }
	    }
	    
	    return tests;
	}
	
	/**
	 * @param file egy f�jl
	 * @return file neve stringk�nt, amib�l a kiterjeszt�s el lett t�vol�tva 
	 **/
	public static String removeExtension(File file){
		String fileName = file.getName();
		
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    return fileName.substring(0, i);
		}
		
		return null;
	}
	
	/**
	 * @return param�terk�nt kapott f�jl kiterjeszt�se
	 **/
	public static String getFileExtension(File file){
		String fileName = file.getName();
		
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    return fileName.substring(i+1);
		}
		
		return null;
	}
	
	/**
	 * Elv�gzi a futtat� mapp�ban tal�lhat� teszteket.
	 * El�sz�r lek�ri a mapp�ban l�v� .in kiterjeszt�s� teszt f�jlokat, 
	 * Majd elind�tja a main f�ggv�nyt, aminek a bemenet�re a teszt f�jlt adja
	 * A program kimenet�t egy temp.output f�jlba menti.
	 * Ha lefutott a teszt, akkor elind�tja a windowsos FC, f�jl kompar�l� programot
	 * �gy: FC <teszt>.in <teszt>.out
	 * Ha nem egyeznek a f�jlok, akkor az elt�r�st jelzi. 
	 **/
	public static void main(String[] args) {
		System.out.println("Tester");
		File folder = new File(System.getProperty("user.dir"));
		ArrayList<String> tests = getTests(folder);
		System.out.println(tests.size());
		int n = 0;
		int success = 0;
		int fail = 0;
		for (String f : tests){		
			try {
				System.out.println("Test #" + (++n) + ": " + f);
				
				String o = runTest(f + ".in");
				
				Runtime rt = Runtime.getRuntime();
				// platform independency ftw
				String ex = "FC " + f + ".out" + " " + o;
				System.out.println(ex);
				Process pr = rt.exec(ex);
				
				BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				 
                String line = null;
 
                while((line=input.readLine()) != null) {
                    System.out.println("\t" + line);
                }
 
                int exitVal = pr.waitFor();
                pr.destroy();
                if (exitVal != 0){
                	System.out.println("Test #" + n + ": Sikertelen teszt!");
                	++fail;
                }
                else {
                	++success;
                	System.out.println("Test #" + n + ": Sikeres teszt!");
                }
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("\n\nTesztek szama: " + n + "\nSikeres tesztek: " + success 
				+ "\nSikertelen tesztek: " + fail);
	}
	
	public static PrintStream outputFile(String name) throws FileNotFoundException {
	       return new PrintStream(new BufferedOutputStream(new FileOutputStream(name)));
	}
	
	/**
	 * Az stdin-t �tir�ny�tja, hogy a param�terk�nt kapott f�jlb�l olvasson.
	 * Az stdout-ot �tir�ny�tja a temp.output f�jlba
	 * Ezut�n lefuttatja a szoftlab4.Game.main met�dust
	 * V�g�l vissza�ll�tja az stdin-t �s az stdout-ot  
	 **/
	public static String runTest(String in){
		PrintStream ps = null;
		try {
			ps = outputFile("temp.output");
			System.setOut(ps);
			File f = new File(in);
			//System.out.print(f.);
			
			System.setIn(new FileInputStream(f));
		
			//szoftlab4.Game.main(new String[]{in});
		
			System.setIn(new FileInputStream(FileDescriptor.in));
		
			System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if (ps != null)
				ps.close();
		}
		return "temp.output";
	}

}
