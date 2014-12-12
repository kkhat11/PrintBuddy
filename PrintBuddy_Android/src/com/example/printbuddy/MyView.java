package com.example.printbuddy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class MyView extends View {
  
	private boolean[][] printMatrix;
    private float cellWidth;
    private float cellHeight;
    private float selfWidth;
    private float selfHeight;
    private Paint linePaint;
    private Paint squarePaint;
    private Paint erasePaint;

    public MyView(Context context) {
        super(context);
        printMatrix = new boolean[24][24];
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        squarePaint = new Paint();
        squarePaint.setColor(Color.parseColor("#00BFFF"));
        erasePaint = new Paint();
        erasePaint.setColor(Color.BLACK);
        this.clearFrame();
    }

    public MyView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        printMatrix = new boolean[24][24];
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        squarePaint = new Paint();
        squarePaint.setColor(Color.parseColor("#00BFFF"));
        erasePaint = new Paint();
        erasePaint.setColor(Color.BLACK);
        this.clearFrame();
    }

    public MyView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        printMatrix = new boolean[24][24];
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        squarePaint = new Paint();
        squarePaint.setColor(Color.parseColor("#00BFFF"));
        erasePaint = new Paint();
        erasePaint.setColor(Color.BLACK);
        this.clearFrame();
    }
    
    public String getStringInformation(){
    	String returnString = "";
    	for(int i = 0; i < 24; i++){
    		for(int j = 0; j < 24; j++){
    			if(printMatrix[i][j] == true) {
    				returnString += "1";
    			}
    			else
    			{
    				returnString += "0";
    			}
    		}
    	}
    	returnString += "\n";
    	return returnString;
    }
    public void testCheck(){
    	printMatrix[0][0] = true;
    	invalidate();
    }
    public void eraseAll(){
    	for(int i = 0; i < 24; i++){
    		for(int j = 0; j < 24; j++){
    			printMatrix[i][j] = false;
    		}
    	}
    	invalidate();
    }

    /**
     * calculate the size of the cells whenever we're redrawn
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldh, int oldw){
        cellWidth = ((w - (getPaddingLeft()+getPaddingRight())) / 24);
        cellHeight = ((h - (getPaddingBottom()+getPaddingTop())) / 24);
        selfWidth = w;
        selfHeight = h;
    }

    /**
     * This function handles drawing the canvas & what LEDs are lit up.
     */
    @Override
    protected void onDraw(Canvas canvas){
        float startX = 0;
        float startY = 0;
        //canvas.drawColor(Color.BLACK);  //clear the screen

        for(int i = 0; i < 24; i++){
            canvas.drawLine(0, startY+cellHeight, cellWidth*24, startY+cellHeight, linePaint);
            canvas.drawLine(startX+cellWidth, 0, startX+cellWidth, cellHeight*24, linePaint);
            startX += cellWidth;
            startY += cellHeight;
        }

        startY = 0;

        for(int i = 0; i < 24; i++){
            startX = 0;
            for(int j = 0; j < 24; j++){
                //if the LED is set, we draw a green square to show that it is lit.
                if(printMatrix[i][j]){
                    canvas.drawRect(startX,startY,
                            (startX+cellWidth),(startY+cellHeight),squarePaint);
                }
                startX += cellWidth;
            }
            startY += cellHeight;
        }
        //draw the the edge of the grid
        canvas.drawLine(0,0,selfWidth,0,linePaint);
        canvas.drawLine(0,0,0,selfHeight,linePaint);
        canvas.drawLine(0,selfHeight,selfWidth,selfHeight,linePaint);
        canvas.drawLine(selfWidth,0,selfWidth,selfHeight,linePaint);
    }


    //toggle the LED on or off, depending on if we're erasing or not
    public void togglePixel(int x, int y){
        if(x < 0) x = 0;
        if(y < 0) y = 0;
        if(x > 23) x = 23;
        if(y > 23) y = 23;
        printMatrix[y][x] = true;
        invalidate();
    }

    
    //go through and set every LED to false; we also send a clearLED command to the arduino
    public void clearFrame(){
        for(int i = 0; i < 24; i++){
            for(int j = 0; j < 24; j++){
              printMatrix[i][j] = false;
            }
        }
        invalidate();
    }

    //Rows are (where you touched) / width of the cell
    //Columns are the same, but just with Y and height
    private int calcRow(float touchX) {
        return (int)(touchX / cellWidth);
    }

    private int calcColumn(float touchY){
        return (int)(touchY / cellHeight);
    }

    /**The touch handler. Here we get the X and Y where you touch,
     * and update our internal representation of the matrix and
     * send the LED that got touched.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        int x = calcRow(touchX);
        int y = calcColumn(touchY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            	togglePixel(x,y);
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }


}