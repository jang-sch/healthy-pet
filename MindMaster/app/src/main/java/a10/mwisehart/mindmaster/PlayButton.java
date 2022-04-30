package a10.mwisehart.mindmaster;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class PlayButton {
    private Point pos = new Point();
    private int height;
    private Paint paint = new Paint();
    private Path path;

    public PlayButton(Point pos, int height) {
        this.pos = pos;
        this.height = height;
        this.paint.setARGB(255,0,255,0);
    }

    public void draw(Canvas canvas){
        path = new Path();
        path.moveTo(pos.x, pos.y - height / 2f);
        path.lineTo(pos.x + height, pos.y);
        path.lineTo(pos.x, pos.y + height / 2f);
        path.close();
        canvas.drawPath(path, paint);
    }

    public boolean isPlayButtonClicked(Point point){
        return (point.x >= pos.x && point.x <= pos.x + height &&
                point.y >= pos.y - height / 2 && point.y <= pos.y + height / 2);

    }


    public void moveDown(int rowVertSpace) {
        pos.y += rowVertSpace;
    }
}
