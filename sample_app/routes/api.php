<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('user-details', ['as' => 'user-details', 'uses' => 'UserDetailsController@index']);
Route::get('user-details/{id}', 'UserDetailsController@show');
Route::post('user-details','UserDetailsController@store');
Route::put('user-details/{id}','UserDetailsController@update');
Route::delete('user-details/{id}','UserDetailsController@destroy');