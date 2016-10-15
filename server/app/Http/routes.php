<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', function () {
    return view('welcome');
});

Route::controller('stream', 'StreamController');

// Route::post("/init","PanelController@init");
// Route::post("/initWithoutToken","PanelController@initWithoutToken");
// Route::controller("lecture","LectureController");

// Route::group(["namespace"=>"Auth"], function(){
// 	Route::controller("auth","AuthController");
// });

// Route::controller('practice', 'PracticeController');
// Route::controller('major', 'MajorController');
// Route::controller('history', 'HistoryController');

// Route::group(["prefix" => "schoollist"], function(){
// 	Route::get("list","SchoolListController@schoolList");
// 	Route::post("majorInfo", "SchoolListController@majorInfo");
// });

// Route::group(["prefix" => "security"], function(){
// 	Route::post("browse", "SecurityController@browse");
// 	Route::post("edit", "SecurityController@edit");
// });