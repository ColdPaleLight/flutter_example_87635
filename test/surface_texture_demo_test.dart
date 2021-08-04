import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:surface_texture_demo/surface_texture_demo.dart';

void main() {
  const MethodChannel channel = MethodChannel('surface_texture_demo');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await SurfaceTextureDemo.platformVersion, '42');
  });
}
