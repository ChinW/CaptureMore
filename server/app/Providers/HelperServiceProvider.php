<?php

namespace App\Providers;

use Illuminate\Support\ServiceProvider;
use Response;
use DB;

class HelperServiceProvider extends ServiceProvider
{
    static protected $TOKEN_LIMIT = 86400;
    static public $PAGE_ITEMS = 60;
    static public $HISTORY_PAGE_ITEMS = 15;
    /**
     * Bootstrap the application services.
     *
     * @return void
     */
    public function boot()
    {
        //
    }

    /**
     * Register the application services.
     *
     * @return void
     */
    public function register()
    {
        
    }
    static function isValidUser($token){
        $r = DB::table("lec_user")->where("token",$token)->get();
        $timestamp = intval(strstr($token,"@",true));
        if(time() - $timestamp > HelperServiceProvider::$TOKEN_LIMIT ){
            // return [false,HelperServiceProvider::error(["type"=>"TOKEN_INVALID"],"error")];
            return false;
        }else{
            if(count($r)<1){
                return false;
                // return [false,HelperServiceProvider::error(["type"=>"TOKEN_INVALID"],"error")];
            }else{
                return [true,$r];
            }
        }
    }
    static function success($data,$info){
        $r = ["status"=>0,"data"=>$data,"info"=>$info];
        return Response::json($r);
    }
    static function error($data,$info){
        $r = ["status"=>1,"data"=>$data,"info"=>$info];
         return Response::json($r);
    }
    static function array_splice_assoc(&$input, $offset, $length, $replacement) {
        $replacement = (array) $replacement;
        $key_indices = array_flip(array_keys($input));
        if (isset($input[$offset]) && is_string($offset)) {
                $offset = $key_indices[$offset];
        }
        if (isset($input[$length]) && is_string($length)) {
                $length = $key_indices[$length] - $offset;
        }

        $input = array_slice($input, 0, $offset, TRUE)
                + $replacement
                + array_slice($input, $offset + $length, NULL, TRUE);
    }
    static function object_to_array($data)
    {
        if (is_array($data) || is_object($data))
        {
            $result = array();
            foreach ($data as $key => $value)
            {
                $result[$key] = HelperServiceProvider::object_to_array($value);
            }
            return $result;
        }
        return $data;
    }
}
