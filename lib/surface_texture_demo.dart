
import 'dart:async';

import 'package:flutter/services.dart';

class SurfaceTextureDemo {
  static const MethodChannel _channel = MethodChannel('surface_texture_demo');

  static Future<List<int>> getTextureEntries() async {
    final List<Object?> idList = await _channel.invokeMethod('getTextureEntries');
    return idList.map<int>((Object? e) => e as int).toList();
  }

  static Future<void> startActivity() async {
    await _channel.invokeMethod('startActivity');
  }
}
