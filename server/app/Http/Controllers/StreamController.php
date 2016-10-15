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

define("_FILE_UPLOAD_ABSOLUTE_PATH_", public_path()."/upload");

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
        $tag = $request->input("tag", null);
        $result = DB::table($this->db_table)
            ->select($this->db_table.".*")
            ->orderBy("id", "desc");
        if($tag) {
            $result->where("main_character", $tag);
        }
        $result = $result->limit(100)->get();
//            ->get();
//            ->leftJoin("lec_user", "lec_user.id", "=", $this->db_table.".user_id")
//            ->where("lec_user.department_id", $departmentIdOfUser)
//            ->paginate(HelperServiceProvider::$PAGE_ITEMS);
        return HelperServiceProvider::success($result, "success");
    }

    public function postLike(Request $request){
        $id = $request->input("id", 0);
        if($id > 0){
            if($record = Stream::find($id)){
                $record->like = $record->like + 1;
                $record->save();
                return HelperServiceProvider::success(["id"=>$id, "like"=>$record->like], "success");
            }
        }
        return HelperServiceProvider::error(null, "error");
    }

    public function postUpload(Request $request){
        $file = $request->file('photo');
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

    function base64_to_file($base64_string, $output_file) {
        $ifp = fopen($output_file, "wb");
        $data = explode(',', $base64_string);
//        var_dump($base64_string, $output_file);
        fwrite($ifp, base64_decode($data[1]));
        fclose($ifp);
        return $output_file;
    }

    public function postUpload64(Request $request){
        $photo = $request->input("photo");
        $newname = time()."@".rand(1, 1000).".jpg";
        $tags = ["anger", "contempt", "disgust", "fear", "happiness", "neutral", "sadness", "surprise"];
        $tagValue = [];
        $mainTag = "";
        $currentValue = 0;

        foreach ($tags as $tag){
            $v =  $request->input($tag, 0);
            var_dump($v);
            if($v >= $currentValue){
                $mainTag = $tag;
                $currentValue = $v;
            }
        }
//
//        $anger = $request->input("anger");
//        $contempt = $request->input("contempt");
//        $disgust = $request->input("disgust");
//        $fear = $request->input("fear");
//        $happiness = $request->input("happiness");
//        $neutral = $request->input("neutral");
//        $sadness = $request->input("sadness");
//        $surprise = $request->input("surprise");

        if(file_exists(_FILE_UPLOAD_ABSOLUTE_PATH_.$newname)){
            HelperServiceProvider::error(null, "生成文件名发生碰撞");
        }else{
            $this->base64_to_file($photo, _FILE_UPLOAD_ABSOLUTE_PATH_."/".$newname);

            $ip = $request->getClientIp();
            $createData = [
                "name" => $ip,
                "ip" => $ip,
                "like" => 0,
                "filename" => $newname,
                "main_character" => $mainTag
            ];
            foreach ($tags as $tag){
                $createData[$tag] = $request->input($tag, 0);
            }
            $result = Stream::create($createData);
            return HelperServiceProvider::success($result->id, "success");
        }

        return HelperServiceProvider::error(null, "error");
    }
}
