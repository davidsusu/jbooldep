package hu.webarticum.jsatbuilder.satbuilder.core;


public interface Definition extends Brick {

    public void addRemovalListener(RemovalListener removalListener);

    public void removeRemovalListener(RemovalListener removalListener);
    
}
