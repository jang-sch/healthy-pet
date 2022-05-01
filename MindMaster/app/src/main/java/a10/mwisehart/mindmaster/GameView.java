package a10.mwisehart.mindmaster;
//30:42
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private final int fps = 1000/30;
    private Thread thread;
    private boolean isPlaying;
    private Board board;


    public GameView(Context context, Point screenSize) {
        super(context);
        board = new Board(screenSize, getResources(), (GameActivity)context);
    }

    @Override
    public void run() {
        while(isPlaying){
            update();
            draw();
            sleep();
        }

    }

    private void update(){


    }

    private void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.BLACK);
            board.draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }


    }

    public void pause(){
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void sleep(){
        try {
            Thread.sleep(fps);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_UP){
            int x = Math.round(event.getX());
            int y = Math.round(event.getY());
            board.onClick(x,y);
        }
        return true;
    }
}
