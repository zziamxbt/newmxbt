package com.example.acer.zzia_mxbt.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.acer.zzia_mxbt.R;

/**
 * Created by 刘俊杰 on 2016/4/23.
 */
public class RoundImageview extends ImageView {
    //private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.hangyuan.myapp";
    private Paint paint;
    private int roundwidth=8;
    private int roundheight=8;
    private Paint paint2;
    public RoundImageview(Context context) {
        super(context);
        init(context,null);
    }



    public RoundImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }
    private void init(Context context, AttributeSet attrs) {
        if(attrs!=null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageview);
            roundwidth= a.getDimensionPixelSize(R.styleable.RoundImageview_roundwidth, roundwidth);
            roundheight= a.getDimensionPixelSize(R.styleable.RoundImageview_roundheight, roundheight);
        }else{
            float density = context.getResources().getDisplayMetrics().density;
            roundwidth = (int) (roundwidth*density);
            roundheight = (int) (roundheight*density);
        }
        paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        paint2 = new Paint();
        paint2.setXfermode(null);
    }
        public void draw(Canvas canvas){
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas2=new Canvas(bitmap);
            super.draw(canvas2);
            drawleftup(canvas2);
            drawleftdown(canvas2);
            drawrightup(canvas2);
            drawrightdown(canvas2);
            canvas.drawBitmap(bitmap,0,0,paint2);
            bitmap.recycle();

        }

    private void drawrightdown(Canvas canvas) {
        Path path = new Path();
              path.moveTo(getWidth()-roundwidth, getHeight());
                path.lineTo(getWidth(), getHeight());
                path.lineTo(getWidth(), getHeight()-roundheight);
                path.arcTo(new RectF(
                              getWidth()-roundwidth*2,
                              getHeight()-roundheight*2,
                              getWidth(),
                            getHeight()), 0, 90);
               path.close();
             canvas.drawPath(path, paint);
    }

    private void drawrightup(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), roundheight);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - roundwidth, 0);
        path.arcTo(new RectF(
                        getWidth() - roundwidth * 2,
                        0,
                        getWidth(),
                        0 + roundheight * 2),
                -90,
                90);
        path.close();
        canvas.drawPath(path, paint);

    }

    private void drawleftdown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() - roundheight);
        path.lineTo(0, getHeight());
        path.lineTo(roundwidth, getHeight());
        path.arcTo(new RectF(
                        0,
                        getHeight() - roundheight * 2,
                        0 + roundwidth * 2,
                        getHeight()),
                90,
                90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawleftup(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, roundheight);
        path.lineTo(0, 0);
        path.lineTo(roundwidth, 0);
        path.arcTo(new RectF(
                        0,
                        0,
                        roundwidth * 2,
                        roundheight * 2),
                -90,
                -90);
        path.close();
        canvas.drawPath(path, paint);
    }


}
