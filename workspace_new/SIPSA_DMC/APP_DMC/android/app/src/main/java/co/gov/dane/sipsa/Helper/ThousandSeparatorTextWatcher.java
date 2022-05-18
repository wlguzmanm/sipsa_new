package co.gov.dane.sipsa.Helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.StringTokenizer;

import co.gov.dane.sipsa.Util;

public class ThousandSeparatorTextWatcher implements TextWatcher {

    EditText editText;


    public ThousandSeparatorTextWatcher(EditText editText) {
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
            //Log.d("THOUSSSSSSS","value----"+value);
            if (value != null && !value.equals(""))
            {
                //Log.d("THOUSSSSSSS","1----");
                if(value.startsWith(".")){
                    //Log.d("THOUSSSSSSS","2----");
                    editText.setText("0.");
                }
                if(value.startsWith("0") && !value.startsWith("0.")){
                    //Log.d("THOUSSSSSSS","3----");
                    if(value.length() ==0){
                        //Log.d("THOUSSSSSSS","4----");
                        editText.setText(value.substring(0));
                        editText.setSelection(0);
                    }else{
                        //Log.d("THOUSSSSSSS","5----");
                        editText.setText(value.substring(1));
                        editText.setSelection(editText.getText().toString().length());
                    }
                }

                String str = editText.getText().toString().replaceAll(",", "");
                //Log.d("THOUSSSSSSS","6----");
                if (!value.equals("")){
                    //Log.d("THOUSSSSSSS","7----");
                    if(value.equals("0")){
                        //Log.d("THOUSSSSSSS","8----");
                        editText.setText("0");
                        editText.setSelection(1);
                    }else{
                        //Log.d("THOUSSSSSSS","9----");
                        editText.setText(getDecimalFormattedString(str));
                        editText.setSelection(editText.getText().toString().length());
                    }
                }else{
                    //Log.d("THOUSSSSSSS","10----");
                    editText.setSelection(0);
                }
            }else{
                //Log.d("THOUSSSSSSS","11----");
                if(value != null && value.equals("")){
                    //Log.d("THOUSSSSSSS","12----");
                    editText.setText("0");
                    editText.setSelection(1);
                }
            }
            //Log.d("THOUSSSSSSS","13----");
            editText.addTextChangedListener(this);
            return;
        }
        catch (Exception ex)
        {
            //Log.d("THOUSSSSSSS","14----"+ ex.getMessage());
            ex.printStackTrace();
            editText.addTextChangedListener(this);
        }

    }

    public static String getDecimalFormattedString(String value)
    {
        if(Util.stringNullEmptys(value) &&  value.matches("[+-]?\\d*(\\.\\d+)?")){
            //Log.d("TAG", "getDecimalFormattedString -----"+ value);
            StringTokenizer lst = new StringTokenizer(value, ".");
            String str1 = value;
            String str2 = "";
            if (lst.countTokens() > 1)
            {
                str1 = lst.nextToken();
                str2 = lst.nextToken();
            }
            String str3 = "";
            int i = 0;
            int j = -1 + str1.length();
            if (str1.charAt( -1 + str1.length()) == '.')
            {
                j--;
                str3 = ".";
            }
            for (int k = j;; k--)
            {
                if (k < 0)
                {
                    if (str2.length() > 0)
                        str3 = str3 + "." + str2;
                    return str3;
                }
                if (i == 3)
                {
                    str3 = "," + str3;
                    i = 0;
                }
                str3 = str1.charAt(k) + str3;
                i++;
            }
        }else{
            return "0";
        }
    }

    public static String trimCommaOfString(String string) {
        if(Util.stringNullEmptys(string)){
            if(string.contains(",")){
                return string.replace(",","");}
            else {
                return string;
            }
        }else{
            return null;
        }
    }
}