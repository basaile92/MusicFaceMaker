package com.ircica.music;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.puredata.android.io.AudioParameters;
import org.puredata.android.service.PdService;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.utils.IoUtils;

import java.io.File;
import java.io.IOException;

import com.tzutalin.dlibtest.R;

public class PlayNotesActivity extends AppCompatActivity{

    private static final String TAG = "PDtest";

    Button btnPlaySound;


    /**
     * The PdService is provided by the pd-for-android library.
     */
    private PdService pdService = null;

    private Notes notes = new Notes();

    /**
     * Initialises the pure data service for playing audio and receiving control commands.
     */
    private final ServiceConnection pdConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            pdService = ((PdService.PdBinder)service).getService();
            initPd();

            try {
                int sampleRate = AudioParameters.suggestSampleRate();
                pdService.initAudio( sampleRate, 0, 2, 8 );
                pdService.startAudio();
            } catch (IOException e) {
                toast(e.toString());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            pdService.stopAudio();
        }
    };

    /**
     * Initialises the pure data audio interface and loads the patch file packaged within the app.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initPd() {
        File patchFile = null;
        try {
            PdBase.setReceiver(new PdUiDispatcher());
            PdBase.subscribe("android");
            File dir = getFilesDir();
            IoUtils.extractZipResource( getResources().openRawResource( R.raw.pdpatch ), dir, true );
            patchFile = new File( dir, "pdpatch.pd" );
            PdBase.openPatch( patchFile.getAbsolutePath() );
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            finish();
        } finally {
            if (patchFile != null) {
                patchFile.delete();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_notes);

        AudioParameters.init(this);
        bindService(new Intent(this, PdService.class), pdConnection, BIND_AUTO_CREATE);

        initGui();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unbindService(pdConnection);
        } catch (IllegalArgumentException e) {
            pdService = null;
        }
    }



    /**
     * Initialises the user interface elements and necessary handlers responsibly for the interaction with the
     * pre-loaded pure data patch. The code is really pure data patch specific.
     */
    private void initGui() {
        this.btnPlaySound = (Button) findViewById( R.id.buttonPlaySound );
        this.btnPlaySound.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch( View v, MotionEvent event ) {
                if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
                    PdBase.sendFloat( "osc_pitch", notes.getFrequency(3,NomNotes.DOd) );
                    PdBase.sendFloat( "osc_volume", 1 ); // send volume (0 to 1)

                } else if ( event.getAction() == MotionEvent.ACTION_UP ) {
                    PdBase.sendFloat( "osc_volume", 0 ); // quiet down
                }
                return false;
            }
        } );


    }

    /**
     * Trigger a native Android toast message.
     * @param text
     */
    private void toast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
                toast.setText(TAG + ": " + text);
                toast.show();
            }
        });
    }

}