import 'package:flutter/material.dart';
import 'package:goeasier/controller/controller.dart';
import 'package:goeasier/main.dart';
import 'package:goeasier/view/welcomePage.dart';
import 'package:goeasier/view/qrScan.dart';
import 'package:provider/src/provider.dart';

class MyHomePage extends StatefulWidget {
  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _selectedIndex = 0;
  String balance = "0";

  var _isInit = true;
  var _isLoading = false;

  @override
  void didChangeDependencies() async {
    if (_isInit) {
      await controller.getWallet(controller.login.token);
      // setState(() async {
      //   _isLoading = true;
      //   //await controller.getWallet(controller.login.token);
      //   print(controller.wallet.balance);
      // });
      _getThingsOnStartup().then((_) {
        //print('Async done');
        setState(() {
          _isLoading = false;
        });
      });
    }
    _isInit = false;
    super.didChangeDependencies();
  }

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
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        padding: EdgeInsets.only(top: 64),
        child: Column(
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              child: Column(
                children: <Widget>[
                  _buildHeader(),
                  SizedBox(height: 16),
                  _buildGradientBalanceCard(context),
                  SizedBox(height: 24.0),
                  _buildCategories(),
                ],
              ),
            ),
            SizedBox(height: 32),
            _buildTransactionList(),
          ],
        ),
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

  Container _buildTransactionList() {
    return Container(
      height: 400,
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.only(
          topLeft: Radius.circular(40),
          topRight: Radius.circular(40),
        ),
        boxShadow: [
          BoxShadow(
            blurRadius: 5,
            color: Colors.grey.withOpacity(0.1),
            offset: Offset(0, -10),
          ),
        ],
      ),
      child: ListView(
        children: <Widget>[
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0 * 3),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(
                      "Transaction",
                      style: TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    Text(
                      "See All",
                      style: TextStyle(
                        color: Colors.grey,
                        fontSize: 16,
                      ),
                    )
                  ],
                ),
                SizedBox(height: 16.0 * 2),
                _buildTransactionItem(
                  color: Colors.deepPurpleAccent,
                  iconData: Icons.photo_size_select_actual,
                  title: "Electric Bill",
                  date: "Today",
                  amount: 11.5,
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Row _buildCategories() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        _buildCategoryCard(
          bgColor: Color(0xffcfe3ff),
          iconColor: Color(0xff3f63ff),
          iconData: Icons.bus_alert_sharp,
          text: "Bus ride",
        ),
        _buildCategoryCard(
          bgColor: Color(0xfffbcfcf),
          iconColor: Color(0xfff54142),
          iconData: Icons.pin_drop,
          text: "Track Bus",
        ),
        _buildCategoryCard(
          bgColor: Color(0xffd3effe),
          iconColor: Color(0xff3fbbfe),
          iconData: Icons.trending_up,
          text: "Stats",
        ),
        _buildCategoryCard(
          bgColor: Color(0xffefcffe),
          iconColor: Color(0xffef3fff),
          iconData: Icons.payment,
          text: "Deposit",
        ),
        // currentIndex: _selectedIndex,
        // selectedItemColor: Colors.amber[800],
        // onTap: _onItemTapped,
      ],
    );
  }

  Container _buildGradientBalanceCard(BuildContext context) {
    //balance = controller.wallet.balance;
    // context.findRootAncestorStateOfType()?.didChangeDependencies();
    return Container(
      height: 140,
      width: double.infinity,
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(5),
        gradient: LinearGradient(
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
          colors: [
            Colors.purpleAccent.withOpacity(0.9),
            Color(0xff466aff),
          ],
        ),
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 32.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              "R " + context.watch<Controller>().wallet.balance,
              style: TextStyle(
                color: Colors.white,
                fontWeight: FontWeight.w500,
                fontSize: 28,
              ),
            ),
            SizedBox(height: 4),
            Text(
              "Total Balance",
              style: TextStyle(
                color: Colors.white.withOpacity(0.9),
                fontSize: 18,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Row _buildHeader() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text(
              "Hello,",
              style: TextStyle(
                fontSize: 16,
                color: Colors.grey,
                fontWeight: FontWeight.w500,
              ),
            ),
            SizedBox(height: 8),
            Text(
              controller.login.email,
              style: TextStyle(
                fontSize: 20,
                color: Colors.black,
                fontWeight: FontWeight.w500,
              ),
            ),
          ],
        ),
        Container(
          height: 56,
          width: 56,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(10.0),
          ),
        ),
      ],
    );
  }

  Row _buildTransactionItem(
      {required Color color,
      required IconData iconData,
      required String date,
      required String title,
      required double amount}) {
    return Row(
      children: <Widget>[
        Container(
          height: 52,
          width: 52,
          decoration: BoxDecoration(
            color: color,
            borderRadius: BorderRadius.circular(5),
          ),
          child: Icon(
            iconData,
            color: Colors.white,
          ),
        ),
        SizedBox(width: 16),
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text(
              date,
              style: TextStyle(
                color: Colors.grey,
                fontWeight: FontWeight.w500,
                fontSize: 16,
              ),
            ),
            Text(
              title,
              style: TextStyle(
                color: Colors.black,
                fontWeight: FontWeight.w600,
                fontSize: 20,
              ),
            )
          ],
        ),
        Spacer(),
        Text(
          "-\$ $amount",
          style: TextStyle(
            color: Colors.black,
            fontWeight: FontWeight.bold,
            fontSize: 16,
          ),
        ),
      ],
    );
  }

  Column _buildCategoryCard(
      {required Color bgColor,
      required Color iconColor,
      required IconData iconData,
      required String text}) {
    return Column(
      children: <Widget>[
        InkWell(
          onTap: () {
            if (text == "Bus ride") {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => QRViewExample()));
            }
          },
          child: Container(
            height: 75,
            width: 75,
            decoration: BoxDecoration(
              color: bgColor,
              borderRadius: BorderRadius.circular(5),
            ),
            child: Icon(
              iconData,
              color: iconColor,
              size: 36,
            ),
          ),
        ),
        SizedBox(height: 8),
        Text(text),
      ],
    );
  }
}

Future _getThingsOnStartup() async {
  await Future.delayed(const Duration(seconds: 5));
}
