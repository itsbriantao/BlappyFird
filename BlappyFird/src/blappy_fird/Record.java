package blappy_fird;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {
	private int score;
	private String name;
	private Date date;
	
	public Record(String name, int score, Date date) {
	    this.score = score;
	    this.name = name;
	    this.date = date;
	}
	
	public int getScore() {
	    return score;
	}
	
	public String getName() {
	    return name;
	}
	
	public Date getDate(){
        return date;
    }
	
	@Override
	public String toString() {
		return getName() + " " + getScore();
	}
}
