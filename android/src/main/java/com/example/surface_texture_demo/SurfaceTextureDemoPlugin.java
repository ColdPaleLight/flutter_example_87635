package com.example.surface_texture_demo;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.Choreographer;
import android.view.Surface;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.view.TextureRegistry;

/** SurfaceTextureDemoPlugin */
public class SurfaceTextureDemoPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private TextureRegistry textureRegistry;
  private Context context;
  public static List<TextureRegistry.SurfaceTextureEntry> textureEntryList = new ArrayList<>();
  static private List<Surface> surfaces = new ArrayList<>();
  static private List<Long> textureIdList = new ArrayList<>();
  public static boolean isTexturesReleased = false;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "surface_texture_demo");
    channel.setMethodCallHandler(this);
    textureRegistry = flutterPluginBinding.getTextureRegistry();
    context = flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getTextureEntries")) {
      for (int i = 0; i < 100; i++) {
        TextureRegistry.SurfaceTextureEntry entry = textureRegistry.createSurfaceTexture();
        textureEntryList.add(entry);
        textureIdList.add(entry.id());
        Surface surface = new Surface(entry.surfaceTexture());
        surfaces.add(surface);
        entry.surfaceTexture().setDefaultBufferSize(1, 1);
      }
      result.success(textureIdList);
      doDraw();

    }else if (call.method.equals("startActivity")) {
      try {
        Class clz = Class.forName("com.example.surface_texture_demo_example.MainActivity");
        Intent intent = new FlutterActivity.CachedEngineIntentBuilder(clz,"cached_engine_identifier").build(context);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    } else {
      result.notImplemented();
    }
  }

  void doDraw() {
    Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
      @Override
      public void doFrame(long frameTimeNanos) {
        for (int i = 0; i < 100; i++) {

          Surface surface = surfaces.get(i);
          if (surface != null && surface.isValid() && !isTexturesReleased) {
            try {
              Canvas canvas = surface.lockCanvas(null);
              canvas.drawARGB(255,100,100,100);
              surface.unlockCanvasAndPost(canvas);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
        doDraw();
      }
    });
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
    textureRegistry = null;
  }
}
