import 'package:flutter/material.dart';
import 'dart:developer';
import 'dart:io';
import 'package:goapp/main.dart';
import 'package:goapp/view/homeview.dart';
import 'package:goapp/view/welcomePage.dart';

import 'package:flutter/foundation.dart';

import 'package:qr_code_scanner/qr_code_scanner.dart';

class QRViewExample extends StatefulWidget {
  const QRViewExample({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _QRViewExampleState();
}

class _QRViewExampleState extends State<QRViewExample> {
  int _selectedIndex = 0;
  Barcode? result;
  QRViewController? controllerscan;
  final GlobalKey qrKey = GlobalKey(debugLabel: 'QR');

  void _onItemTapped(int index) async {
    if (index == 1) {
      await controller.logOut(controller.login.token.toString());
    }
    setState(() {
      _selectedIndex = index;
      if (index == 1) {
        //controller.logOut(controller.login.token.toString());
        if (controller.logoutResponse) {
          Navigator.push(
              context, MaterialPageRoute(builder: (context) => WelcomePage()));
        }
      } else if (index == 2) {
        print("profile");
      } else if (index == 0) {
        Navigator.push(
            context, MaterialPageRoute(builder: (context) => MyHomePage()));
      }
    });
  }

  // In order to get hot reload to work we need to pause the camera if the platform
  // is android, or resume the camera if the platform is iOS.
  @override
  void reassemble() {
    super.reassemble();
    if (Platform.isAndroid) {
      controllerscan!.pauseCamera();
    }
    controllerscan!.resumeCamera();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: <Widget>[
          Expanded(flex: 4, child: _buildQrView(context)),
          Expanded(
            flex: 1,
            child: FittedBox(
              fit: BoxFit.contain,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: <Widget>[
                  if (result != null)
                    Text(
                        'Barcode Type: ${describeEnum(result!.format)}   Data: ${result!.code}')
                  else
                    const Text('Scan a scode'),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
                      Container(
                        margin: const EdgeInsets.all(8),
                        child: ElevatedButton(
                            onPressed: () async {
                              await controllerscan?.toggleFlash();
                              setState(() {});
                            },
                            child: FutureBuilder(
                              future: controllerscan?.getFlashStatus(),
                              builder: (context, snapshot) {
                                return Text('Flash: ${snapshot.data}');
                              },
                            )),
                      ),
                      Container(
                        margin: const EdgeInsets.all(8),
                        child: ElevatedButton(
                            onPressed: () async {
                              await controllerscan?.flipCamera();
                              setState(() {});
                            },
                            child: FutureBuilder(
                              future: controllerscan?.getCameraInfo(),
                              builder: (context, snapshot) {
                                if (snapshot.data != null) {
                                  return Text(
                                      'Camera facing ${describeEnum(snapshot.data!)}');
                                } else {
                                  return const Text('loading');
                                }
                              },
                            )),
                      )
                    ],
                  ),
                  //   Row(
                  //     mainAxisAlignment: MainAxisAlignment.center,
                  //     crossAxisAlignment: CrossAxisAlignment.center,
                  //     children: <Widget>[
                  //       Container(
                  //         margin: const EdgeInsets.all(8),
                  //         child: ElevatedButton(
                  //           onPressed: () async {
                  //             await controllerscan?.pauseCamera();
                  //           },
                  //           child: const Text('pause',
                  //               style: TextStyle(fontSize: 20)),
                  //         ),
                  //       ),
                  //       Container(
                  //         margin: const EdgeInsets.all(8),
                  //         child: ElevatedButton(
                  //           onPressed: () async {
                  //             await controllerscan?.resumeCamera();
                  //           },
                  //           child: const Text('resume',
                  //               style: TextStyle(fontSize: 20)),
                  //         ),
                  //       )
                  //     ],
                  //   ),
                ],
              ),
            ),
          )
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Home',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.logout),
            label: 'logout',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.person),
            label: 'Profile',
          ),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.amber[800],
        onTap: _onItemTapped,
      ),
    );
  }

  Widget _buildQrView(BuildContext context) {
    // For this example we check how width or tall the device is and change the scanArea and overlay accordingly.
    var scanArea = (MediaQuery.of(context).size.width < 600 ||
            MediaQuery.of(context).size.height < 600)
        ? 300.0
        : 500.0;
    // To ensure the Scanner view is properly sizes after rotation
    // we need to listen for Flutter SizeChanged notification and update controller
    return QRView(
      key: qrKey,
      onQRViewCreated: _onQRViewCreated,
      overlay: QrScannerOverlayShape(
          borderColor: Colors.red,
          borderRadius: 5,
          borderLength: 50,
          borderWidth: 10,
          cutOutSize: scanArea),
      onPermissionSet: (ctrl, p) => _onPermissionSet(context, ctrl, p),
    );
  }

  void _onQRViewCreated(QRViewController controller1) {
    setState(() {
      controllerscan = controller1;
    });
    controller1.scannedDataStream.listen((scanData) {
      setState(() {
        result = scanData;
        if (result != null) {
          controller.startTrip(controller.login.token, "hdf4545bet45bgeg4",
              "4f4443434", "1/02/2022");
          Navigator.push(
              context, MaterialPageRoute(builder: (context) => MyHomePage()));
        }
      });
    });
  }

  void _onPermissionSet(BuildContext context, QRViewController ctrl, bool p) {
    log('${DateTime.now().toIso8601String()}_onPermissionSet $p');
    if (!p) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('no Permission')),
      );
    }
  }

  @override
  void dispose() {
    controllerscan?.dispose();
    super.dispose();
  }
}
