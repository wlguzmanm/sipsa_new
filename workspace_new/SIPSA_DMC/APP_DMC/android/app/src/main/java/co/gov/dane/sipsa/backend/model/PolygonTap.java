package co.gov.dane.sipsa.backend.model;

import android.content.Context;
import android.view.MotionEvent;

import org.osmdroid.bonuspack.overlays.Polygon;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;


public class PolygonTap extends Polygon {

    private boolean selected = false;

    private boolean isInside = false;

    public PolygonTap(final Context ctx){
        super(ctx);

    }


    @Override
    public boolean onSingleTapConfirmed(final MotionEvent event, final MapView mapView){

        //System.out.println("me hicieron tap");

        boolean tapped2 = contains(event);

        if (tapped2) {
            setIsInside(true);
            if (!selected) {
                selected = true;
                System.out.println("me hicieron tap seleccionado*" + selected+"*");
                this.setFillColor(0x33aadd7d);

            } else {
                selected = false;
                System.out.println("me hicieron tap seleccionado*" + selected+"*");
                //this.setFillColor(0x12121212);
            }
        }
        else{
            setIsInside(false);
        }
        System.out.println("Esta dentro del poligono " + isInside);


        if (mInfoWindow == null)
            //no support for tap:
            return false;
        boolean tapped = contains(event);
        if (tapped){
            Projection pj = mapView.getProjection();
            GeoPoint position = (GeoPoint)pj.fromPixels((int)event.getX(), (int)event.getY());
            mInfoWindow.open(this, position, 0, 0);
        }


        mapView.refreshDrawableState();
        mapView.invalidate();



        return tapped;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isInside() {
        return isInside;
    }

    public void setIsInside(boolean isInside) {
        this.isInside = isInside;
    }
}