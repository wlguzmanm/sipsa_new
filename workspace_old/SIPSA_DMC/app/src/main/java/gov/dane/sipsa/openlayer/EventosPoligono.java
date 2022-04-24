package gov.dane.sipsa.openlayer;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jperezp on 02/03/2016.
 */
class EventosPoligono extends View {


    public EventosPoligono(Context context) {
        super(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("AAAA");
        invalidate();
        return true;
    }

}
