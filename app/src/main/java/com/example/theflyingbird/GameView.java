package com.example.theflyingbird;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

public class GameView extends View {

    private int cwidth;
    private int cheight;
    //private Bitmap bird;
    private Bitmap bird[]=new Bitmap[2];
    private int birdX=10;
    private int birdY;
    private int birdSpeed;

    private int blueX;
    private int blueY;
    private int blueSpeed=15;
    private Paint bluePaint=new Paint();

    //black ball
    private int blackX;
    private int blackY;
    private int blackSpeed=20;
    private Paint blackpaint=new Paint();

    private Bitmap bgimg;
    private Paint score=new Paint();
    private  int score1;
    private Paint level=new Paint();
    private  Bitmap life[]=new Bitmap[2];
    private int life_count;
    private boolean touch_flg=false;

    public GameView(Context context) {
        super(context);
        bird[0]= BitmapFactory.decodeResource(getResources(),R.drawable.bird1);
        bird[1]= BitmapFactory.decodeResource(getResources(),R.drawable.bird2);
        bgimg= BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        bluePaint.setColor(Color.RED);
        bluePaint.setAntiAlias(false);

       blackpaint.setColor(Color.BLACK);
       blackpaint.setAntiAlias(false);


        score.setColor(Color.BLACK);
        score.setTextSize(32);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        score.setAntiAlias(true);

        level.setColor(Color.DKGRAY);
        level.setTextSize(32);
        level.setTypeface(Typeface.DEFAULT_BOLD);
        level.setTextAlign(Paint.Align.CENTER);
        level.setAntiAlias(true);

        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.heart);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_g);


        //first position
        birdY=500;
        score1=0;
        life_count=3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        cwidth=canvas.getWidth();
        cheight=canvas.getHeight();
        canvas.drawBitmap(bgimg,0,0,null);
        //bird
      //  canvas.drawBitmap(bird,0,0,null);
        int minBirdY=bird[0].getHeight();
        int maxBirdY=cheight-bird[0].getHeight()*3;
        birdY+=birdSpeed;
        if(birdY<minBirdY)
            birdY=minBirdY;
        if(birdY>maxBirdY)
            birdY=maxBirdY;
        birdSpeed+=2;

        if(touch_flg)
        {
            //flap Wings
            canvas.drawBitmap(bird[1],birdX,birdY,null);
            touch_flg=false;
        }
        else
        {
            canvas.drawBitmap(bird[0],birdX,birdY,null);
        }
       //blue
        blueX-=blueSpeed;
        if(hitCheck(blueX,blueY))
        {
          score1+=10;
          blueX=-100;
        }
        if(blueX<0)
        {
            blueX=cwidth+20;
            blueY=(int) Math.floor(Math.random()*(maxBirdY-minBirdY))+minBirdY;
        }
        canvas.drawCircle(blueX,blueY,10,bluePaint);
        //black ball
        blackX-=blackSpeed;
        if(hitCheck(blackX,blackY))
        {
            blackX=-100;
            life_count--;
            if(life_count==0)
            {
                //game over
//                Intent intent=new Intent(GameView.this,GameOver.class);
//                ContextCompat.startActivity(intent);
                Log.v("MESSAGE","GAME OVER");



            }
        }
        if(blackX<0)
        {
            blackX=cwidth+200;
            blackY=(int) Math.floor(Math.random()*(maxBirdY-minBirdY))+minBirdY;

        }
        canvas.drawCircle(blackX,blackY,25,blackpaint);


     //score
     canvas.drawText("Score: 0"+ score1,20,60,score);
     //level
     canvas.drawText("Level-1",cwidth/2,60,level);
     //life
        for(int i=0;i<3;i++)
        {
            int x=(int)(560+life[0].getWidth()*1.5*i);
            int y=30;
            if(i<life_count)
            {
                canvas.drawBitmap(life[0],x,y,null);
            }
            else
            {
                canvas.drawBitmap(life[1],x,y,null);
            }
        }
     canvas.drawBitmap(life[0],560,30,null);
     canvas.drawBitmap(life[0],620,30,null);
     canvas.drawBitmap(life[1],680,30,null);
    }

    public boolean hitCheck(int x,int y)
    {
        if(birdX<x && x<(bird[0].getWidth()+birdX) && birdY<y && y<(birdY+bird[0].getHeight())){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            touch_flg=true;
            birdSpeed=-20;
        }
        return  true;
    }
}
