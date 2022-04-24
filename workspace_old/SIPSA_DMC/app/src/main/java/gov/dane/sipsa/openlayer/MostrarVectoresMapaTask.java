package gov.dane.sipsa.openlayer;

import android.os.AsyncTask;

import org.osmdroid.bonuspack.overlays.BasicInfoWindow;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.model.PolygonUnidadCobertura;


public class MostrarVectoresMapaTask extends AsyncTask<String, Integer, String> {
	private List<PolygonUnidadCobertura> polygonUnidadCoberturaList = new ArrayList<PolygonUnidadCobertura>();
	private MapView map;
	private responseMostrarMapa callback;

	public MostrarVectoresMapaTask(List<PolygonUnidadCobertura> polygonUnidadCoberturaList, MapView map, responseMostrarMapa callback){
		this.polygonUnidadCoberturaList = polygonUnidadCoberturaList;
		this.map = map;
		this.callback = callback;
	}

	protected String doInBackground(String... dato) {
		for (int i = 0; i < polygonUnidadCoberturaList.size(); i++) {
			polygonUnidadCoberturaList.get(i).getPoligono().setInfoWindow(new BasicInfoWindow(R.layout.bonuspack_bubble, map));
			//polygonUnidadCoberturaList.get(i).getPoligono().setTitle("tap en polygon " + i);
			publishProgress(i);
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
        callback.OnProgress(progress[0], polygonUnidadCoberturaList.size());
	}


	@Override
	protected void onPostExecute(String result) {
		callback.OnPostExecute();
	}
}
