package com.example.surface_texture_demo_example;

import android.os.Bundle;
import android.os.Handler;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.surface_texture_demo.SurfaceTextureDemoPlugin;

import java.lang.reflect.Field;

import io.flutter.FlutterInjector;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.renderer.SurfaceTextureWrapper;
import io.flutter.view.TextureRegistry;


public class MainActivity extends FlutterActivity {
    class MyFlutterJNI extends FlutterJNI {
        @Override
        public void onSurfaceCreated(@NonNull Surface surface) {
            super.onSurfaceCreated(surface);
            if (createCalledCount > 1) {
                try {
                    triggerCrash();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static int createCalledCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (createCalledCount == 0) {
            FlutterEngine flutterEngine = new FlutterEngine(this,null,new MyFlutterJNI(),null,true);
            flutterEngine.getDartExecutor().executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault());
            FlutterEngineCache.getInstance().put("cached_engine_identifier", flutterEngine);
        }
        createCalledCount++;
        super.onCreate(savedInstanceState);
    }

    private void triggerCrash() throws NoSuchFieldException, IllegalAccessException {
        SurfaceTextureWrapper surfaceTextureWrapper = getFirstSurfaceTextureWrapper();
        Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (surfaceTextureWrapper) {
                    // call long time function to wait raster thread call method named `SurfaceTextureWrapper.attachToGLContext` of first surfaceTextureWrapper
                    longTimeFunction();
                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 1; i < 100; i++) {
                                    // release SurfaceTextureEntry in main thread
                                    SurfaceTextureDemoPlugin.textureEntryList.get(i).release();
                                }
                                SurfaceTextureDemoPlugin.isTexturesReleased = true;
                                synchronized (MainActivity.this) {
                                    MainActivity.this.notifyAll();
                                }
                            }
                        });

                        synchronized (MainActivity.this) {
                            MainActivity.this.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private void longTimeFunction() {
        int sum = 0;
        for (int i = 0; i < 100000000; i++) {
            sum += 1;
        }
    }

    @Nullable
    private SurfaceTextureWrapper getFirstSurfaceTextureWrapper() throws NoSuchFieldException, IllegalAccessException {
        TextureRegistry.SurfaceTextureEntry entry = SurfaceTextureDemoPlugin.textureEntryList.get(0);
        Class clz = entry.getClass();
        Field fieldTextureWrapper = clz.getDeclaredField("textureWrapper");
        fieldTextureWrapper.setAccessible(true);
        SurfaceTextureWrapper surfaceTextureWrapper = (SurfaceTextureWrapper) fieldTextureWrapper.get(entry);
        return surfaceTextureWrapper;
    }

    @Nullable
    @Override
    public String getCachedEngineId() {
        return "cached_engine_identifier";
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachFromFlutterEngine();
    }
}
