<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Stream extends Model
{
	protected $table = "stream";
    /**
    * 可以被批量赋值的属性. *
    * @var array
    */
    protected $fillable = ['name', "ip", "like", "filename", "main_character", "anger", "anger", "contempt", "disgust",
"fear", "happiness", "neutral", "sadness", "surprise", "created_at", "updated_at"];
    
}
