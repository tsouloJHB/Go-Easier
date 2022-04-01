import 'package:flutter/widgets.dart';
import 'package:goeasier/model/walletModel.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';
import 'package:goeasier/model/loginModel.dart';

class Controller with ChangeNotifier {
  Controller();
  bool loginResponse = false;
  bool logoutResponse = false;
  bool walletResponse = false;
  late loginModel login;
  late walletModel wallet;
  String ipAdress = "192.168.8.163:5050";

  Future<http.Response> apisend(String email, String password) async {
    print(email);
    print(password);
    print('http://' + ipAdress + '/login');
    final response = await http.post(
      Uri.parse('http://' + ipAdress + '/login'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'password': email.toString(),
        'email': password.toString(),
      }),
    );

    try {
      if (response.statusCode == 201 || response.statusCode == 200) {
        // If the server did return a 201 CREATED response,
        // then parse the JSON.
        loginResponse = true;
        print(response.body);
        var jsonData = jsonDecode(response.body);
        //List<Quote> qu = [];
        notifyListeners();
        print(jsonData);
        print(jsonData['token']);
        login = loginModel(
            token: jsonData['token'].toString(),
            email: jsonData['email'].toString());
      } else {
        // If the server did not return a 201 CREATED response,
        // then throw an exception.
        loginResponse = false;
        print(response.body);
        throw Exception(Error);
      }
    } on Exception catch (e) {
      // TODO
      print(e.toString());
    }
    return response;
  }

  Future<http.Response> logOut(String token) async {
    print(token);
    final response = await http.get(
      Uri.parse('http://' + ipAdress + '/logout/' + token),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );

    try {
      if (response.statusCode == 201 || response.statusCode == 200) {
        // If the server did return a 201 CREATED response,
        // then parse the JSON.
        logoutResponse = true;
        print(response.body);
        var jsonData = jsonDecode(response.body);
      } else {
        // If the server did not return a 201 CREATED response,
        // then throw an exception.
        logoutResponse = false;
        print(response.statusCode);
        throw Exception(Error);
      }
    } on Exception catch (e) {
      // TODO
      print(e.toString());
    }
    return response;
  }

  Future<http.Response> getWallet(String token) async {
    print(token);
    final response = await http.get(
      Uri.parse('http://' + ipAdress + '/wallet/' + token),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );

    try {
      if (response.statusCode == 201 || response.statusCode == 200) {
        // If the server did return a 201 CREATED response,
        // then parse the JSON.
        walletResponse = true;
        var jsonData = jsonDecode(response.body);
        wallet = walletModel(
            balance: jsonData['balance'].toString(), email: login.email);
        print(response.body);
        notifyListeners();
      } else {
        // If the server did not return a 201 CREATED response,
        // then throw an exception.
        walletResponse = false;
        print(response.statusCode);
        throw Exception(Error);
      }
    } on Exception catch (e) {
      // TODO
      print(e.toString());
    }
    return response;
  }
}
