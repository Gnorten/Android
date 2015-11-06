package Game1_SpaceInvaders;

import android.os.Bundle;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

import Tools.GameListener;
import Tools.Game_Activity_1;

/**
 * Created by thvo8508 on 28.10.2015.
 */
public class Game1 extends Game_Activity_1 implements GameListener {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setGameListener( this );
    }

    @Override
    public void mainLoopIteration(Game_Activity_1 activity, GL10 gl) {
        Log.d("InputSample", "touch: " + activity.getTouchX() + ", " + activity.getTouchY() + ", " + activity.isTouched());
        Log.d("InputSample", "accelerometer: " + activity.getAccelerationOnXAxis() + ", " + activity.getAccelerationOnYAxis() + ", " + activity.getAccelerationOnZAxis());
    }

    @Override
    public void setup(Game_Activity_1 activity, GL10 gl) {
        // TODO Auto-generated method stub

    }
}
