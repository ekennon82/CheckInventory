package inventory;

public class Store {
	
	private int storeId;
	
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int sId) {
		storeId = sId;
	}
	
	@Override
	public String toString(){
		return Integer.toString(storeId);
	}
}