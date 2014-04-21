package szoftlab4;

/**
 * Egy koordináta pontot megvalósító osztály.
 * @author Nusser Áddm
 *
 */
public class Vector {
	
	private double x;
	private double y;
	
	public Vector(double x, double y){
		this.x = x;
		this.y = y;
	}
	public Vector(Vector v){
		x = v.getX();
		y = v.getY();
	}
	
	double getX(){
		return x;
	}
	double getY(){
		return y;
	}
	void setX(double newX){
		x = newX;
	}
	void setY(double newY){
		y = newY;
	}
	/**
	 * 
	 * @param v A kapott vektort hozzáadja.
	 */
	void Add(Vector v){
		x += v.getX();
		y += v.getY();
	}
	/**
	 * 
	 * @param v
	 * @return Visszatér a két vektor közötti távolsággal.
	 */
	double getDistance(Vector v){
		return Math.sqrt((x-v.getX())*(x-v.getX()) + (y-v.getY())*(y-v.getY()));
	}
	/**
	 * A kapott vektor felé elmozdul kapott távolsággal.
	 * 
	 * @param distance A távolság
	 * @param v A vektor ami felé mozdul
	 */
	void MoveDistanceToVector(double distance, Vector v){
		double xDiff = x - v.getX();
		double yDiff = y - v.getY();
		double alfa;
		
		if(Math.sqrt(xDiff*xDiff + yDiff*yDiff) <= distance){
			x = v.getX();
			y = v.getY();
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
}
