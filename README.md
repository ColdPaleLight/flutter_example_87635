click "start activity and trigger crash"
you will see the native crash with log:
```
E/OpenGLRenderer(29724): [SurfaceTexture-0-29724-1] attachToContext: abandoned SurfaceTexture
E/flutter (29724): [ERROR:flutter/shell/platform/android/platform_view_android_jni_impl.cc(49)] java.lang.RuntimeException: Error during attachToGLContext (see logcat for details)
E/flutter (29724): 	at android.graphics.SurfaceTexture.attachToGLContext(SurfaceTexture.java:294)
E/flutter (29724): 	at io.flutter.embedding.engine.renderer.SurfaceTextureWrapper.attachToGLContext(SurfaceTextureWrapper.java:55)
E/flutter (29724): 
F/flutter (29724): [FATAL:flutter/shell/platform/android/platform_view_android_jni_impl.cc(1245)] Check failed: CheckException(env). 
F/libc    (29724): Fatal signal 6 (SIGABRT), code -1 (SI_QUEUE) in tid 30311 (1.raster), pid 29724 (re_demo_example)
*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***
Build fingerprint: 'HUAWEI/ELS-AN00/HWELS:10/HUAWEIELS-AN00/102.0.0.138C00:user/release-keys'
Revision: '0'
ABI: 'arm64'
SYSVMTYPE: Maple
APPVMTYPE: Art
Timestamp: 2021-08-04 15:58:56+0800
pid: 29724, tid: 30311, name: 1.raster  >>> com.example.surface_texture_demo_example <<<
uid: 10504
signal 6 (SIGABRT), code -1 (SI_QUEUE), fault addr --------
Abort message: '[FATAL:flutter/shell/platform/android/platform_view_android_jni_impl.cc(1245)] Check failed: CheckException(env). 
'
    x0  0000000000000000  x1  0000000000007667  x2  0000000000000006  x3  0000006fdc005f20
    x4  0000000000000000  x5  0000000000000000  x6  0000000000000000  x7  7f7f7f7f7f7f7f7f
    x8  00000000000000f0  x9  c45dfd17366734bc  x10 0000000000000001  x11 0000000000000000
    x12 fffffff0fffffbdf  x13 0000000000000003  x14 0000000000000004  x15 00000f77a7dff5dd
    x16 00000070cf3299f8  x17 00000070cf309700  x18 0000006fd3cac000  x19 000000000000741c
    x20 0000000000007667  x21 00000000ffffffff  x22 0000006f98f99088  x23 000000703f3583c0
    x24 0000000000000000  x25 0000000000000001  x26 0000000000000000  x27 0000000000000001
    x28 0000000000000060  x29 0000006fdc005fc0
    sp  0000006fdc005f00  lr  00000070cf2be580  pc  00000070cf2be5ac
backtrace:
      #00 pc 00000000000705ac  /apex/com.android.runtime/lib64/bionic/libc.so (abort+160) (BuildId: e0a2a32acc18e871df44c7f6f8c912df)
      #01 pc 0000000001451254  /data/app/com.example.surface_texture_demo_example-LvzGoKb-XNyVoIHtcQbf7w==/lib/arm64/libflutter.so (BuildId: f80e1297ae60e1a1ba4ad5e636b2e28e316a6f57)
      #02 pc 000000000147c124  /data/app/com.example.surface_texture_demo_example-LvzGoKb-XNyVoIHtcQbf7w==/lib/arm64/libflutter.so (BuildId: f80e1297ae60e1a1ba4ad5e636b2e28e316a6f57)
      #03 pc 00000000001727bc  [anon:libc_malloc]

```

