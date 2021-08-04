import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:surface_texture_demo/surface_texture_demo.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  List<int> textureIds = <int>[];

  @override
  void initState() {
    super.initState();
    createTextureEntries();
  }

  void createTextureEntries() async {
    List<int> textureIds = await SurfaceTextureDemo.getTextureEntries();
    setState(() {
      this.textureIds.addAll(textureIds);
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('SurfaceTexture demo'),
          ),
          body: Stack(
            children: [
              ListView.builder(
                itemCount: textureIds.length,
                itemBuilder: (BuildContext context, int index) {
                  return Container(
                    height: 2,
                    color: Colors.yellow,
                    child: Texture(
                      textureId: textureIds[index],
                    ),
                  );
                },
              ),
              Container(
                width: 500,
                height: 100,
                child: Column(
                  children: [
                    GestureDetector(
                      behavior: HitTestBehavior.translucent,
                      child: Container(
                        color: Colors.green,
                        width: 500,
                        height: 100,
                        child: Text('start activity and trigger crash'),
                      ),
                      onTap: () {
                        SurfaceTextureDemo.startActivity();
                      },
                    ),
                  ],
                ),
              )
            ],
          )),
    );
  }
}
