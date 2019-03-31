<?php

namespace App\Http\Controllers;

use http\Exception;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Input;
use App\UserDetails;

class UserDetailsController extends Controller
{

    /**
	 * Index function for user listing.
	 *
	 * @return \Illuminate\Http\JsonResponse
	 */
	public function index()
	{
        $users = [];
        $getParams = Input::get();
        if(!empty($getParams)) {
            foreach ($getParams as $key => $param) {
                $users = UserDetails::all()->where($key, 'like', $param)->first();
            }
        } else {
            $users = UserDetails::all();
        }
        $response =  response()->json($users);
        return $response;
	}

    /**
     * Show user
     *
     * @param int $id
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function show($id)
    {
        try {
            $user = UserDetails::find($id);
            $response =  response()->json($user);
        } catch (Exception $ex) {
            $response =  response()->json($ex->getMessage());
        }

        return $response;
    }

    /**
	 * Store user
	 *
	 * @param Request $request
	 *
	 * @return \Illuminate\Http\JsonResponse
	 */
	public function store(Request $request)
	{
        try {
            $user = UserDetails::create($request->all());
            $response =  response()->json($user);
        } catch (Exception $ex) {
            $response =  response()->json($ex->getMessage());
        }

        return $response;
	}

	/**
	 * Update user
	 *
	 * @param Request $request
	 * @param int $id
	 *
	 * @return \Illuminate\Http\JsonResponse
	 */
	public function update(Request $request, $id)
	{
        try {
            $user = UserDetails::findOrFail($id);
            $user->update($request->all());
            $response =  response()->json($user);
        } catch (Exception $ex) {
            $response =  response()->json($ex->getMessage());
        }

		return $response;
	}

	/**
	 * Destroy user
	 *
	 * @param int $id
	 *
	 * @return \Illuminate\Http\JsonResponse
	 */
	public function destroy($id)
	{
        try {
            UserDetails::find($id)->delete();
            $response =  response()->json([], 204);
        } catch (Exception $ex) {
            $response =  response()->json($ex->getMessage());
        }

		return $response;
	}
}
