package Standard;

import java.util.ArrayList;

/**
 * Created by rober on 1-11-2015.
 */
public abstract class AbstractController {

    private ArrayList<AbstractModel> models = new ArrayList<>();
    private ArrayList<AbstractView> views = new ArrayList<>();

    public void addView(AbstractView view) {
        views.add(view);
    }

    public void addModel(AbstractModel model){
        models.add(model);
    }

    public void removeView(int index) {
        views.remove(index);
    }

    public void removeModel(int index) {
        models.remove(index);
    }

}
