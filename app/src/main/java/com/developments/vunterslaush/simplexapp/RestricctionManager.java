package com.developments.vunterslaush.simplexapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by VunterSlaush !
 */
public class RestricctionManager
{
    private static int layout_width = LinearLayout.LayoutParams.MATCH_PARENT;
    private static int layout_height = LinearLayout.LayoutParams.WRAP_CONTENT;

    int viewCount = 0;
    private ArrayList <EditText> editables;
    private Context context;
    private LinearLayout layout;


    private static RestricctionManager instance;

    private RestricctionManager(){}

    public static RestricctionManager getInstance()
    {
        if(instance == null)
            instance = new RestricctionManager();
        return instance;
    }


    public void initManager(Context context, LinearLayout layout)
    {
        this.layout = layout;
        editables = new ArrayList<>();
        this.context = context;
    }

    /**
     * TODO Erase All Views!
     */
    public void eraseAll()
    {
        editables.clear();
        viewCount = 0;
        limpiarViews();
        layout.removeAllViews();
    }

    private void limpiarViews()
    {
        for (int i = 0; i<layout.getChildCount(); i++)
        {
            View v = layout.getChildAt(i);
            v.setTag(null);
            v.destroyDrawingCache();
        }
    }

    public void setErrorToConstraint(PlanteamientoNoValido error)
    {
        editables.get(error.getCampo() - 1).requestFocus();
        editables.get(error.getCampo() - 1).setError(error.toString());
    }

    public void removeErrors()
    {
        for (EditText e : editables)
            e.setError(null);
    }

    public ArrayList<String> getStringsOfTextFields()
    {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i<viewCount; i++)
        {
            strings.add(editables.get(i).getText().toString());
        }
        return  strings;
    }


    private void initView(View view)
    {
        ViewHolder holder;

        holder = new ViewHolder(view);
        view.setTag(holder);
        editables.add(viewCount, holder.getEditText());
        Log.d("VUNTERSLAUSH","/--/> Nuevo HOLDER:"+viewCount);

        holder.setPos(viewCount);
        holder.setText("R"+(viewCount+1));

        holder.setOnClick(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ViewHolder holder = (ViewHolder) view.getTag();
                editables.remove(holder.getPos());
                viewCount--;
                notifyItemRemoveAt(holder.getPos());
                holder = null;
            }
        });
        viewCount++;

    }

    private void notifyItemRemoveAt(int pos)
    {
        View vi = layout.getChildAt(pos);
        vi.setTag(null);
        layout.removeViewAt(pos);
        for (int i = pos; i<layout.getChildCount(); i++)
        {
            View v = layout.getChildAt(i);
            ViewHolder holder = (ViewHolder) v.getTag();
            holder.setPos(i);
            holder.setText("R"+(i+1));
        }
    }

    public void inflateNewViewIntoLayout()
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.restriccion_item,null,false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        initView(view);
        layout.addView(view);
    }
}

class ViewHolder
{
    private Button quitButton;
    private EditText restrictionEditTex;
    private TextView textView;
    private int pos;

    public ViewHolder(View v)
    {
        quitButton = (Button) v.findViewById(R.id.quitRestrictionButton);
        quitButton.setTag(this);
        restrictionEditTex = (EditText)v.findViewById(R.id.restriccionEditText);
        restrictionEditTex.setError(null);
        textView = (TextView)v.findViewById(R.id.restriccionTextView);
        Utils.getInstance().addTextWatcher(restrictionEditTex);
        restrictionEditTex.requestFocus();
    }

    public EditText getEditText()
    {
        return restrictionEditTex;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }


    public void setText(String s)
    {
        textView.setText(s);
    }

    public void setOnClick(View.OnClickListener click)
    {
        quitButton.setOnClickListener(click);
    }

}
