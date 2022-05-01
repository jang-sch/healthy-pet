package a10.mwisehart.mindmaster;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class SolutionCover {
    private Point pos;
    private final Paint paint = new Paint();
    private int width, height;
    public boolean show = false;
    public boolean offScreen = false;
    public int alpha = 255;

    public SolutionCover(Point pos, int width, int height) {
        paint.setARGB(alpha, 50,50,50);
        this.pos = pos;
        this.width = width;
        this.height = height;
    }

    public void draw(Canvas canvas){
        canvas.drawRect(new Rect(pos.x, pos.y, pos.x + width, pos.y + height), paint);
        if(show && !offScreen){
            alpha -= 5;
            paint.setARGB(alpha, 50,50,50);
            if(alpha <= 0){
                offScreen = true;
            }
        }

    }
}
