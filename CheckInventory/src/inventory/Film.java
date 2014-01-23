package inventory;

public class Film {
	
	private int filmId;
	private String filmName;
	
	public int getFilmId() {
		return filmId;
	}
	public void setFilmId(int fId) {
		filmId = fId;
	}
	
	public String getFilmName() {
		return filmName;
	}
	public void setFilmName(String fName) {
		filmName = fName;
	}
	
	@Override
	public String toString(){
		return filmName;
	}
}