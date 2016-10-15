<?php

namespace App\Http\Controllers\Auth;

use App\User;
use Validator;
use App\Http\Controllers\Controller;
use Illuminate\Foundation\Auth\ThrottlesLogins;
use Illuminate\Foundation\Auth\AuthenticatesAndRegistersUsers;
use DB;
use Hash;
use Response;
use Auth;
use App\Providers\HelperServiceProvider;
use Illuminate\Http\Request;

class AuthController extends Controller
{

    /*
    |--------------------------------------------------------------------------
    | Registration & Login Controller
    |--------------------------------------------------------------------------
    |
    | This controller handles the registration of new users, as well as the
    | authentication of existing users. By default, this controller uses
    | a simple trait to add these behaviors. Why don't you explore it?
    |
    */

    use AuthenticatesAndRegistersUsers, ThrottlesLogins;

    public function postAuth(Request $request){
        $user = $request->input("name");
        $password =$request->input("pass");
        $r = DB::table("lec_user")->where("loginname",$user)->get();
        if(count($r)>0 && Hash::check($password,$r[0]->password)){
            $info="Success";
            $token = time()."@".md5(time());
            DB::table("lec_user")->where("id",$r[0]->id)->update(["token"=>$token]);
            $reponse = HelperServiceProvider::success($token,$info);
            // $reponse->withCookie(cookie("token",md5(time()),3600,null,"localhost"));
            return $reponse;
        }else{
           $info="账号或密码错误,点击重试";
            return HelperServiceProvider::error([], $info);
        }
        // return Response::json($r);
    }
    
    
    public function generatePassword($pass){
        return (Hash::make($pass));
    }

    public function isPasswordSame($origin, $input){
        $inputHash = Hash::make($input);
        if(Hash::check($input,$origin)){
            return true;
        }
        return false;
    }
    /**
     * Create a new authentication controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('guest', ['except' => 'getLogout']);
    }

    /**
     * Get a validator for an incoming registration request.
     *
     * @param  array  $data
     * @return \Illuminate\Contracts\Validation\Validator
     */
    protected function validator(array $data)
    {
        return Validator::make($data, [
            'name' => 'required|max:255',
            'email' => 'required|email|max:255|unique:users',
            'password' => 'required|confirmed|min:6',
        ]);
    }

    /**
     * Create a new user instance after a valid registration.
     *
     * @param  array  $data
     * @return User
     */
    protected function create(array $data)
    {
        return User::create([
            'name' => $data['name'],
            'email' => $data['email'],
            'password' => bcrypt($data['password']),
        ]);
    }
}
