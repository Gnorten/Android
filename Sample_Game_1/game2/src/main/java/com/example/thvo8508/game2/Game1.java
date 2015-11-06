package com.example.thvo8508.game2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;
import android.util.Log;

import Tools.GameListener;
import Tools.Game_Activity_1;

/**
 * Example showing how to use vertex arrays to draw a
 * simple triangle with OpenGL ES.
 *
 * @author mzechner
 *
 */
public class Game1 extends Game_Activity_1 implements GameListener
{
    private FloatBuffer vertices, vertices1, vertices2,  colors, accelbuffer;
    private int i,j=0;
    private float fj,fy=0;
    private boolean jswitch, iswitch;

    public void onCreate( Bundle savedInstance )
    {
        super.onCreate( savedInstance );
        setGameListener( this );
    }


    @Override
    public void setup(Game_Activity_1 activity, GL10 gl)
    {
        final int SMOOTH_BUFFER_SIZE = 40;
        accelbuffer = FloatBuffer.allocate(SMOOTH_BUFFER_SIZE);

        ByteBuffer buffer1 = ByteBuffer.allocateDirect( 3 * 4 * 3 );
        buffer1.order(ByteOrder.nativeOrder());
        vertices = buffer1.asFloatBuffer();
        ByteBuffer buffer1 = ByteBuffer.allocateDirect( 3 * 4 * 3 );
        buffer1.order(ByteOrder.nativeOrder());
        vertices1 = buffer1.asFloatBuffer();
        ByteBuffer buffer1 = ByteBuffer.allocateDirect( 3 * 4 * 3 );
        buffer1.order(ByteOrder.nativeOrder());
        vertices2 = buffer1.asFloatBuffer();

        vertices.put( -0.5f );
        vertices.put( -0.5f );
        vertices.put( 0 );

        vertices.put( 0.5f );
        vertices.put( -0.5f );
        vertices.put( 0 );

        vertices.put( 0 );
        vertices.put( 0.5f );
        vertices.put( 0 );

        vertices.rewind();

        buffer1 = ByteBuffer.allocateDirect( 3 * 4 * 4 );
        buffer1.order(ByteOrder.nativeOrder());
        colors = buffer1.asFloatBuffer();
//        colors.put( 1 );
//        colors.put( 0 );
//        colors.put( 0 );
//        colors.put( 1 );
//        colors.put( 0 );
//        colors.put( 1 );
//        colors.put( 0 );
//        colors.put( 1 );
//        colors.put( 0 );
//        colors.put( 0 );
//        colors.put( 1 );
//        colors.put( 1 );
//        colors.rewind();
    }

    public void changevertices()
    {
        if(i == 100) iswitch = false;
        if(i==0 ) iswitch= true;
        i=(iswitch)? i+1 : i-1;

        vertices.put( -0.5f );
        vertices.put( -0.5f );
        vertices.put( 0 );

        vertices.put( 0.5f );
        vertices.put( -0.5f );
        vertices.put( 0 );

        vertices.put( -0.5f + (i/100f) );
        vertices.put( 0.5f );
        vertices.put(0);

        vertices.rewind();
       // Log.d("InputSample", "i" + i);
    }

    public void accchangevertices()
    {
        fj = (smoothvalue(acceleration[0])/(9.81f));
        fy = (smoothvalue(acceleration[1])/(9.81f));

        vertices.put( -0.5f );
        vertices.put( fy*1.5f );
        vertices.put( 0 );

        vertices.put( 0.5f );
        vertices.put( 0 );
        vertices.put( 0 );

        vertices.put((1.5f*fj));
        vertices.put( 0.5f );
        vertices.put(0);

        vertices.rewind();
       // Log.d("InputSample", "fj" + fj);
    }

    public void changecolor(GL10 gl)
    {

        if(j == 100) jswitch = false;
        if(j==0 ) jswitch= true;
        j=(jswitch)? j+1 : j-1;

        colors.put( 0 + (j/100f) );
        colors.put( 0 );
        colors.put( 0 );
        colors.put( 1 );

        colors.put( 1 );
        colors.put( 0 );
        colors.put( 1 - (j/100f) );
        colors.put( 1 );

        colors.put( 0);
        colors.put( 1 - (j/100f) );
        colors.put(0 + (j / 100f));
        colors.put( 1 );

        colors.rewind();
       // Log.d("InputSample", "j" + j);


    }

    public void accchangecolor(GL10 gl) {
        if (j == 100) jswitch = false;
        if (j == 0) jswitch = true;
        j = (jswitch) ? j + 1 : j - 1;

        fy = 2f * Math.abs(acceleration[1] / (9.81f));
        fj = 2f * Math.abs(acceleration[0] / (9.81f));

        colors.put(fy);
        colors.put((1 - fy));
        colors.put(0);
        colors.put(1);

        colors.put(0);
        colors.put(1 - (j / 100f));
        colors.put(0 + (j / 100f));
        colors.put(1);

        colors.put((fj));
        colors.put((1 - fj));
        colors.put(0);
        colors.put(1);

        colors.rewind();
        Log.d("InputSample", "fj= " + fj);
        Log.d("InputSample", "fy= " + fy);
    }

    public float smoothvalue(float value) {

        accelbuffer.put(value);
        if (!accelbuffer.hasRemaining()) accelbuffer.rewind();
        return calcaverage(accelbuffer);
    }


    /**
     * calcaverage returns the average of a Float Buffer
     * @param Buffer
     * @return
     */
    public float calcaverage(FloatBuffer Buffer)
    {
        float average = 0;
        for (int i = 0; i <Buffer.limit(); i++)
        {
            average += Buffer.get(i);
        }
        return (average/Buffer.limit());
    }

    @Override
    public void mainLoopIteration(Game_Activity_1 activity, GL10 gl)
    {

        //Log.d("InputSample", "Run");
        // Log.d("InputSample", "accelerometer: " + activity.getAccelerationOnXAxis() + ", " + activity.getAccelerationOnYAxis() + ", " + activity.getAccelerationOnZAxis());
        this.accchangevertices();
        this.accchangecolor(gl);
        gl.glViewport(0, 0, getViewportWidth(), getViewportHeight());
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colors);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
       // Log.d("InputSample", "acc=" + acceleration[0]);
    }

}

