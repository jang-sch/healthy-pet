package a10.mwisehart.mindmaster;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private Bitmap background;

    private Paint paint = new Paint();
    private final int boardXPos;
    private final List<Peg> pegList = new ArrayList<>();
    private final int pegRad;
    private final int rowVertSpace;
    private final int rowYOffset;


    public Board(Point screenSize, Resources resources) {
        background = BitmapFactory.decodeResource(resources, R.drawable.board);
        background = Bitmap.createScaledBitmap(background, (int)(screenSize.y * 0.66), screenSize.y, true);
        boardXPos = screenSize.x / 2 - background.getWidth() / 2;
        pegRad = background.getHeight() / 26;
        rowVertSpace = background.getHeight() / 11;
        rowYOffset = background.getHeight() / 15;

        generatePegs();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(background, boardXPos,0, paint);
        for(Peg p : pegList){
            p.draw(canvas);
        }
    }

    private void generatePegs(){
        for(int r = 0; r < 10; r++){
            for(int c = 0 ; c < 4; c++){
                int pegX = pegRad * c * 3 + boardXPos + pegRad * 2;
                int pegY = r * rowVertSpace + rowYOffset + r * 3;
                Peg tmp = new Peg(0, pegRad, new Point(pegX, pegY));
                pegList.add(tmp);
            }
        }
        for(int i = pegList.size()-1; i > pegList.size()-5; i--){
            pegList.get(i).setColor((int) (Math.random() * 5 + 1));
        }
    }

    public void onClick(int x, int y){
        Point point = new Point(x,y);
        for(Peg p : pegList){
            p.isPegClicked(point);
        }
    }

}
