<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;
use DB;
use Response;
use App\Stream;
use App\Providers\HelperServiceProvider;
use Illuminate\Http\File;
use Symfony\Component\HttpFoundation;

class StreamController extends Controller
{
    public $db_table = "stream";
    public $photoPath = "";

    public function __construct(){
        $this->photoPath = public_path()."/upload";
    }

    /**
     * [setDBTable description]
     * @param [type] $tableName [description]
     */
    public function setDBTable($tableName){
        $db_table = $tableName;
    }

    /**
     * 用key值来分类成数组
     * @param  [type] &$arr [description]
     * @param  [type] $key  [description]
     * @return [type]       [description]
     */
    private function getArrayInKey(&$arr,$key){
        $n=[];
        foreach($arr as $k => $v){
            $n[$v->$key][$v->id]=$v->name;
        }
        return $n;
    }
    
    /**
     * [getArrayByKey description]
     * @param  [type] &$arr [description]
     * @return [type]       [description]
     */
    private function getArrayByKey(&$arr){
        $n=[];
        foreach($arr as $k => $v){
            $n[$v->type][$v->id]=$v->name;
        }
        return $n;
    }

    public function postList(Request $request){
        $result = DB::table($this->db_table)
            ->select($this->db_table.".*")
            ->get();
//            ->leftJoin("lec_user", "lec_user.id", "=", $this->db_table.".user_id")
//            ->where("lec_user.department_id", $departmentIdOfUser)
//            ->paginate(HelperServiceProvider::$PAGE_ITEMS);
        return HelperServiceProvider::success($result, "success");
    }

    public function postUpload(Request $request){
        $file = $request->file('photo');
//        var_dump($file);
        if($file->isValid()){
            $tmp = explode(".", $file->getClientOriginalName());
            $filename = $tmp[0]."@".time();
            $extend = $tmp[count($tmp) - 1];
            $newname = $filename.".".$extend;
            $result = $file->move($this->photoPath, $newname);
            if($result){
                $ip = $request->getClientIp();
                $createData = [
                    "name" => $ip,
                    "ip" => $ip,
                    "like" => 0,
                    "filename" => $result->getFilename()
                ];
                $result = Stream::create($createData);
                return HelperServiceProvider::success($result->id, "success");
            }
        }
        return HelperServiceProvider::error(null, "error");
    }
}
