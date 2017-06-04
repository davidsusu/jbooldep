package hu.webarticum.jsatbuilder.satbuilder;


public interface Definition extends Brick {

    public void addRemovalListener(RemovalListener removalListener);

    public void removeRemovalListener(RemovalListener removalListener);
    
}
