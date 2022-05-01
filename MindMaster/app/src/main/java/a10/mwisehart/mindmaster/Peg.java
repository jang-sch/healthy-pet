package a10.mwisehart.mindmaster;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Peg {
    private Point pos = new Point();
    int radius;
    List<Paint> paints = new ArrayList<>();
    int selectedPaint = 0;

    public Peg(int selectedPaint, int radius, Point pos) {
        this.selectedPaint = selectedPaint;
        for(int i = 0; i < 6; i++){
            paints.add(new Paint());
        }
        paints.get(0).setARGB(255,50,50,50); //Dark grey rgbcode
        paints.get(1).setARGB(255,255,0,0); //red rgbcode
        paints.get(2).setARGB(255,0,0,255); //blue rgbcode
        paints.get(3).setARGB(255,0,255,0); //green rgbcode
        paints.get(4).setARGB(255,255,255,0); //yellow rgbcode
        paints.get(5).setARGB(255,255,165,0); //orange rgbcode
        this.radius = radius;
        this.pos = pos;
    }
    public void setColor(int c){
        selectedPaint = c;
    }
    public void draw(Canvas canvas){
        canvas.drawCircle(pos.x, pos.y, radius, paints.get(selectedPaint));
    }

    public void isPegClicked(Point point){
        double distance = Math.sqrt(Math.pow(pos.x - point.x, 2) + Math.pow(pos.y - point.y, 2));
        if(distance <= radius){
            pegClicked();
        }
    }

    public Point getPos() {
        return pos;
    }

    private void pegClicked() {
        selectedPaint = (selectedPaint + 1) % paints.size();
    }
}
