## 1. 用户点数

每张照片得到的点数 ＝ Int(表情的数值就是从API上返回的表情的数值)＊10

例如:

用户A拍了一张照片，API返回数值如下：

```
"anger": 0.08891568,
"contempt": 0.00363058178,
"disgust": 0.7236219,
"fear": 0.00000603674971,
"happiness": 9.00591459e-8,
"neutral": 0.001907167,
"sadness": 0.181909069,
"surprise": 0.000009478085
```

则用户的得到的点数为：

```
"anger": 0,
"contempt": 0,
"disgust": 7,
"fear": 0,
"happiness": 0,
"neutral": 0,
"sadness": 1,
"surprise": 0
```

当用户某项数值提升超过MAX时，能量爆满，获取“今日头衔”（负能量星球／伤心1999/寂寞也不要人陪／.../开心的美少女）

> MAX值可以先设定为50

## 2. APIs

### 2.1 List

POST `http://121.201.15.116/stream/list`

返回如下

>filename的前缀地址是`http://121.201.15.116/upload/`

```
{
  "status": 0,
  "data": [
    {
      "id": 1,
      "name": "",
      "filename": "example_011@1476523179.pdf",
      "ip": "192.168.10.1",
      "like": 0,
      "created_at": "2016-10-15 17:19:39",
      "updated_at": "2016-10-15 17:19:39"
    },
    {
      "id": 2,
      "name": "",
      "filename": "example_011@1476523186.pdf",
      "ip": "192.168.10.1",
      "like": 0,
      "created_at": "2016-10-15 17:19:46",
      "updated_at": "2016-10-15 17:19:46"
    },
    {
      "id": 3,
      "name": "",
      "filename": "example_011@1476523187.pdf",
      "ip": "192.168.10.1",
      "like": 0,
      "created_at": "2016-10-15 17:19:47",
      "updated_at": "2016-10-15 17:19:47"
    }
  ],
  "info": "success"
}
```

### 2.2 Upload

POST `http://121.201.15.116/stream/upload`

| Key | Value |Notice|
|:---|:---|:---|
| photo | form-data形式的文件 | ／ |
