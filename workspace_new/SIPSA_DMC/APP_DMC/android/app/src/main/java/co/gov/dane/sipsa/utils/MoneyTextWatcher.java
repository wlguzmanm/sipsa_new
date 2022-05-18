package co.gov.dane.sipsa.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;


public class MoneyTextWatcher implements TextWatcher {
    private final WeakReference<EditText> editTextWeakReference;
    private int decimales;

    public MoneyTextWatcher(EditText editText, int decimales ) {
        editTextWeakReference = new WeakReference<EditText>(editText);
        this.decimales = decimales;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {

        String temp_s =editable.toString();
        String s = editable.toString().replaceAll("\\,", "");
        s = s.replaceAll("^0[0-9]+$","0");
        s = s.replaceAll("^\\.$","");

        if((s.length()-1)/3>0 && !s.contains(".")){
            int pos = (s.length()-1)/3;
            for(int i = 0 ;i<pos;i++){
                s = new StringBuffer(s).insert(s.length()-((i+1)*3)-i, ",").toString();
            }
        }

        if(!s.contains(".")) {
            EditText editText = editTextWeakReference.get();
            editText.removeTextChangedListener(this);
            editText.setText(s);
            editText.setSelection(s.length());
            editText.addTextChangedListener(this);
        }else{
            if(Util.getString(s,".")>1 ||s.length()-s.indexOf(".")> decimales){
                temp_s = temp_s.substring(0,temp_s.length()-1);

                EditText editText = editTextWeakReference.get();
                editText.removeTextChangedListener(this);
                editText.setText(temp_s);
                editText.setSelection(temp_s.length());
                editText.addTextChangedListener(this);
            }
        }


    }
}