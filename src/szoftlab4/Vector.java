package szoftlab4;

/**
 * Egy koordináta pontot megvalósító osztály.
 * 
 * @author Nusser Ádám
 */
public class Vector {
	public double x;
	public double y;
	
	public Vector(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Vector(Vector v){
		x = v.x;
		y = v.y;
	}

	/**
	 * 
	 * @param v A kapott vektort hozzáadja.
	 */
	public void Add(Vector v){
		x += v.x;
		y += v.y;
	}
	/**
	 * 
	 * @param v
	 * @return Visszatér a két vektor közötti távolsággal.
	 */
	public double getDistance(Vector v){
		return Math.sqrt((x-v.x)*(x-v.x) + (y-v.y)*(y-v.y));
	}
	/**
	 * A kapott vektor felé elmozdul kapott távolsággal.
	 * 
	 * @param distance A távolság
	 * @param v A vektor ami felé mozdul
	 */
	public void MoveDistanceToVector(double distance, Vector v){
		double xDiff = x - v.x;
		double yDiff = y - v.y;
		double alfa;
		
		if(Math.sqrt(xDiff*xDiff + yDiff*yDiff) <= distance){
			x = v.x;
			y = v.y;
		}
		else{
			if(xDiff == 0 && yDiff > 0)
				alfa = Math.PI /2;
			else if(xDiff == 0 && yDiff < 0)
				alfa = (-1) * Math.PI /2;
			else if(xDiff < 0){
				alfa = Math.PI - Math.atan(yDiff/(-xDiff));
			}
			else
				alfa = Math.atan(yDiff/xDiff);
			y = distance * Math.sin(alfa);
			x = distance * Math.cos(alfa);
		}
	}
	
	/**
	 * @return igazzal tér vissza ha a vector epsilon sugarú körön belül van 
	 **/
	public boolean equals(Vector v, double epsilon){
		return getDistance(v) <= epsilon;
	}
}
