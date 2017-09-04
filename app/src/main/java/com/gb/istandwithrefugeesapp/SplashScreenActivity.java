package com.gb.istandwithrefugeesapp;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SplashScreenActivity extends Activity implements View.OnClickListener
{

        private Button splashButton;
        private ImageView imageView;
        private Bitmap bitmap;
        private TextView tx;
        private Bitmap bitmap2;
        private RelativeLayout layout;

        @Override
        protected  void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splashscreen);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 64;

            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
            bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.background, options);
            imageView = (ImageView)this.findViewById(R.id.imageView1);
            layout = (RelativeLayout)this.findViewById(R.id.contents_frame);
           layout.setBackgroundDrawable(new BitmapDrawable(bitmap));
            //layout.setBackgroundResource(R.drawable.background);
            tx = (TextView) findViewById(R.id.textviewsplash);
            Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
            tx.setTypeface(custom_font);
            Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/globe.png")
                .into(imageView);
            splashButton = (Button)findViewById(R.id.button1);
            splashButton.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            layout.setBackgroundDrawable(new BitmapDrawable(bitmap2));
            bitmap.recycle();
            //bitmap.dispose();
            bitmap = null;}


        
    }

