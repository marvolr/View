package com.example.marc9.sound;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    private TextView[] mDots;
    Button button;
    public SlideAdapter(Context context){
        this.context=context;
    }

    // list of images
    public int[] lst_images = {
            R.drawable.cercleazulclaro,
            R.drawable.cercleazuloscuro,
            R.drawable.cerclemarron,
            R.drawable.cerclemorado
    };
    // list of titles
    public String[] lst_title = {
            "METRO",
            "NOTICIA",
            "TURISMO",
            "PRODUCTO"
    }   ;
    // list of descriptions
    public String[] lst_description = {
            "Aqui podras Gestionar todo el aspecto de la movilidad dentro de la cuidad, recargar tu tarjeta, acceder a promociones unicas, accionar un boton de comunicacion directa para emergencias, MUCHO MAS",
            "Mantente informado!! busca tus noticias favoritas o sucesos importantes de la cuidad!",
            "Quieres saber un poco mas de tu cuidad? o eres turista? Te tenemos toda la informacion acerca de los puntos de interes de la cuidad, y actividades turistica, artistica a realizar",
            "Estas cansado de lo mismo? Aqui encontraras productos innovadores, servicios o actividades de interes. Apoyar a emprededores y adquirir sus productos"
    };
    // list of background colors
    public int[]  lst_backgroundcolor = {
            Color.rgb(240,81,81),
            Color.rgb(242,28,71),
            Color.rgb(246,18,94),
            Color.rgb(255,0,137)
    };


    @Override
    public int getCount() {
        return lst_title.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);
        LinearLayout layoutslide = (LinearLayout) view.findViewById(R.id.slidelinear);
        ImageView imgslide = (ImageView)  view.findViewById(R.id.slidepic);
        TextView txttitle= (TextView) view.findViewById(R.id.slidetitle);
        button = (Button) view.findViewById(R.id.button_slide);
        TextView description = (TextView) view.findViewById(R.id.slidedescription);
        layoutslide.setBackgroundColor(lst_backgroundcolor[position]);
        imgslide.setImageResource(lst_images[position]);
        txttitle.setText(lst_title[position]);
        description.setText(lst_description[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    ViewPager.OnPageChangeListener viewListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            if(i==3){
                button.setVisibility(View.VISIBLE);

            }else{button.setVisibility(View.GONE);}

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
