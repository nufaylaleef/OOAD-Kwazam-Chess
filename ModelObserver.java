//Method to be called when the model is uodated
public interface ModelObserver {
    public void onModelUpdate(boolean redMoved,boolean blueMoved);
}

//reMoved indicates if the red player has moved
//blueMoved indicates if the blue player has moved