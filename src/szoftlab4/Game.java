package szoftlab4;

public class Game {
	
	/** Ez számolja milyen mélyen vagyunk a metódushívásokban
	 *  A printEnter és a printExit metódusok ez alapján indentálnak
	 **/
	private static int tabs = 0;
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}
	
	/**
	 * Minden metódus hívás legelején ezt a metódust hívjuk meg.
	 * A következőt írja ki a kimenetre: ->[:Osztály].metódus(param):
	 * @param sender az objektum, amiből meg lett hívva a metódus
	 * @param params ha hívó metódusnak voltak paraméterei, akkor string formátumban meg kell adni a neveiket 
	 **/
	public static void printEnter(Object sender, String... params){
		++tabs;
		String methodEnter = "";
		
		for(int i = 0; i < tabs; ++i)
			methodEnter += "\t";
		
		Exception e = new Exception();
		e.fillInStackTrace();
		methodEnter += "->[:" + sender.getClass().getSimpleName() + "]";//->[:Game].buildTower(pos):
		String methodName = e.getStackTrace()[1].getMethodName();
		methodEnter += "." + methodName + "(";
		if(params.length != 0){
			int i;
			for(i = 0; i < params.length - 1; ++i)
				methodEnter += params[i] + ", ";
			methodEnter += params[i];
		}
		methodEnter += "):";
		System.out.println(methodEnter);
	}
	
	/**
	 * Minden metódus hívás legvégén ezt a metódust hívjuk meg.
	 * A következőt írja ki a kimenetre: <-[:Osztály].metódus()
	 * @param sender az objektum, amiből meg lett hívva a metódus 
	 **/
	public static void printExit(Object sender){
		String methodExit = "";
		
		for(int i = 0; i < tabs; ++i)
			methodExit += "\t";
		Exception e = new Exception();
		e.fillInStackTrace();
		methodExit += "<-[:" + sender.getClass().getSimpleName() + "]";//->[:Game].buildTower(pos):
		String methodName = e.getStackTrace()[1].getMethodName();
		methodExit += "." + methodName + "(" + ")";
		
		System.out.println(methodExit);
		--tabs;
	}
	
	public void run(){
		printEnter(this);
		
		run2(5);
		
		printExit(this);
	}
	public void run2(int some_param){
		printEnter(this, "some_param");
		
		printExit(this);
	}

}
