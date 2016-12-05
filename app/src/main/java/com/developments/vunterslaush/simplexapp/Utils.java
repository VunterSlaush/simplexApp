package com.developments.vunterslaush.simplexapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by Slaush on 24/11/2016.
 */
public class Utils
{
    private static Utils instance;


    private Utils(){}
    public static Utils getInstance()
    {
        if(instance == null)
            instance = new Utils();
        return instance;
    }

    public void addTextWatcher(final EditText editText)
    {
        TextWatcher textWatcher = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {

                try
                {
                    new Ecuacion(editable.toString());
                    editText.setTextColor(Color.GREEN);
                }
                catch (EcuacionNoValida ecuacionNoValida)
                {
                    editText.setTextColor(Color.RED);
                }
            }
        };
        editText.addTextChangedListener(textWatcher);
    }

    public void takeScreenshot(Activity context) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = context.getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            Log.e("VUNTERSLAUSH", "CAPTURE ERROR!!");
            e.printStackTrace();
        }
    }

    public TextView createTextViewStyled(Context context)
    {
        TextView tv = new TextView(context);
        tv.setEms(1);

        tv.setTextAppearance(context, android.R.style.TextAppearance_Material_Medium);
        tv.setGravity(Gravity.LEFT);

        return tv;
    }
}
