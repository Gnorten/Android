package Tools;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


public class Game_Activity_1 extends Activity implements GLSurfaceView.Renderer, OnTouchListener, SensorEventListener
{

    /**
     * GLSurfaceView
     **/
    private GLSurfaceView glSurface;

    /**
     * GameListener
     **/
    private GameListener listener;

    /**
     * with and height of the viewport
     **/
    private int width, height;

    /**
     * start time of last frame in nano seconds
     **/
    long lastFrameStart;

    /**
     * delta time in seconds
     **/
    float deltaTime;

    /** touch coordinates and touched state **/
    int touchX, touchY;
    boolean isTouched;

    /** acceleration on the 3 axis **/
    float[] acceleration = new float[3];

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glSurface = new GLSurfaceView(this);
        glSurface.setRenderer(this);
        this.setContentView(glSurface);

        glSurface.setOnTouchListener(this);

        SensorManager manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if( manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0 )
        {
            Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME );
        }

    }

    /**
     * Called when the accuracy of the Sensor has changed.
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // we ignore this
    }

    /**
     * Called when the sensor has new input
     * @param event The SensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        System.arraycopy(event.values, 0, acceleration, 0, 3);
    }

    /**
     * Called when a touch event occurs.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if( event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE )
        {
            touchX = (int)event.getX();
            touchY = (int)event.getY();
            isTouched = true;
        }

        if( event.getAction() == MotionEvent.ACTION_UP )
            isTouched = false;

        try
        {
            Thread.sleep( 30 );
        }
        catch( Exception ex )
        {

        }

        return true;
    }

    /**
     * Called when the application is paused. We need to
     * also pause the GLSurfaceView.
     */
    @Override
    protected void onPause() {
        super.onPause();
        glSurface.onPause();
    }

    /**
     * Called when the application is resumed. We need to
     * also resume the GLSurfaceView.
     */
    @Override
    protected void onResume() {
        super.onResume();
        glSurface.onResume();

    }


    /**
     * Called each Frame
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        long currentFrameStart = System.nanoTime();
        deltaTime = (currentFrameStart - lastFrameStart) / 1000000000.0f;
        lastFrameStart = currentFrameStart;

        if( listener != null )
             listener.mainLoopIteration(this, gl);
    }

    /**
     * Called when the surface size changed, e.g. due to tilting
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        if( listener != null )
            listener.setup( this, gl );
    }

    /**
     * Called when the GLSurfaceView has finished initialization
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        lastFrameStart = System.nanoTime();
        String renderer = gl.glGetString(GL10.GL_RENDERER);
        // if( renderer.toLowerCase().contains("pixelflinger" ) )
        //   Mesh.globalVBO = false;

        if( listener != null )
            listener.setup( this, gl );
    }

    /**
     * @return the viewport width in pixels
     */
    public int getViewportWidth() {
        return width;
    }

    /**
     * @return the viewport height in pixels
     */
    public int getViewportHeight() {
        return height;
    }

    public void setGameListener(GameListener listener) {
        this.listener = listener;
    }


    /**
     * @return the delta time in seconds
     */
    public float getDeltaTime( )
    {
        return deltaTime;
    }

    /**
     * @return the last known touch coordinate on the x axis
     */
    public int getTouchX( )
    {
        return touchX;
    }

    /**
     * @return the last known touch coordinate on the x axis
     */
    public int getTouchY( )
    {
        return touchY;
    }

    /**
     * @return wheter the touch screen is touched or not
     */
    public boolean isTouched( )
    {
        return isTouched;
    }

    /**
     * @return the acceleration on the x-Axis of the device
     */
    public float getAccelerationOnXAxis( )
    {
        return acceleration[0];
    }

    /**
     * @return the acceleration on the x-Axis of the device
     */
    public float getAccelerationOnYAxis( )
    {
        return acceleration[1];
    }

    /**
     * @return the acceleration on the x-Axis of the device
     */
    public float getAccelerationOnZAxis( )
    {
        return acceleration[2];
    }
}

