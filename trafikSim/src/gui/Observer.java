package gui;

public interface Observer<T> {
	
	public void addObserver(T o);
	
	public void removeObserver(T o);
	
}
