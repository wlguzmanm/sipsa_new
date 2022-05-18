package co.gov.dane.sipsa.Helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import co.gov.dane.sipsa.Util;

public class ValidadorTextWatcher implements TextWatcher {

    EditText editText;


    public ValidadorTextWatcher(EditText editText) {
        this.editText = editText;


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try
        {
            editText.removeTextChangedListener(this);
            String value = editText.getText().toString();

            if(!Util.stringNullEmptys(value)){
                editText.setText("0");
                editText.setSelection(editText.getText().toString().length());
            }else{
                if(value.startsWith(".")){
                    editText.setText("0.");
                }
                if(value.startsWith("0") && !value.startsWith("0.")){
                    if(value.length() ==0){
                       editText.setText(value.substring(0));
                       editText.setSelection(0);
                    }else{
                       editText.setText(value.substring(1));
                       editText.setSelection(editText.getText().toString().length());
                    }
                }
            }
            editText.addTextChangedListener(this);
            return;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            editText.addTextChangedListener(this);
        }
    }

}