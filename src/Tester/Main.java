package Tester;

import szoftlab4.*;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;


public class Main {
	
	public static ArrayList<String> getTests(File folder) {
		ArrayList<String> tests = new ArrayList<String>();
		
	    for (File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	            //System.out.println(fileEntry.getName());
	            String ext = getFileExtension(fileEntry);
	            
	            if (ext != null && "in".compareToIgnoreCase(ext) == 0)
	            	tests.add(removeExtension(fileEntry));
	        }
	    }
	    
	    return tests;
	}
	
	public static String removeExtension(File file){
		String fileName = file.getName();
		
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    return fileName.substring(0, i);
		}
		
		return null;
	}
	
	public static String getFileExtension(File file){
		String fileName = file.getName();
		
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    return fileName.substring(i+1);
		}
		
		return null;
	}

	public static void main(String[] args) {
		System.out.println("Tester");
		File folder = new File(System.getProperty("user.dir"));
		ArrayList<String> tests = getTests(folder);
		
		int n = 0;
		for (String f : tests){		
			try {
				System.out.println("Test #" + (++n) + ": " + f);
				
				String o = runTest(f + ".in");
				
				Runtime rt = Runtime.getRuntime();
				Process pr = rt.exec("FC " + f + ".in" + " " + o);
				
				BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				 
                String line = null;
 
                while((line=input.readLine()) != null) {
                    System.out.println("\t" + line);
                }
 
                int exitVal = pr.waitFor();
                if (exitVal != 0){
                	System.out.println("Test #" + n + ": Sikertelen teszt!");
                }
                else
                	System.out.println("Test #" + n + ": Sikeres teszt!");
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static PrintStream outputFile(String name) throws FileNotFoundException {
	       return new PrintStream(new BufferedOutputStream(new FileOutputStream(name)));
	}
	
	public static String runTest(String in){
		PrintStream ps = null;
		try {
			ps = outputFile("temp.output");
			System.setOut(ps);
		
		
		szoftlab4.Game.main(new String[]{in});
		
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				ps.close();
		}
		return "temp.output";
	}

}
