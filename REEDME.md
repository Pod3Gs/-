//建立consul

key：
config/consul-service/configuration

value:
spring.redis.host: localhost
spring.redis.port: 6379
spring.redis.pool.max.wait: 10000
spring.redis.pool.max.idle: 200
spring.redis.timeout: 0
spring.redis.block.when.exhausted: true



---------------分割线------------------
//建立mongodb

use mongodb1;
db.user.drop();

db.user.insert({username:"admin", 
    password: 'ISMvKXpXpadDiUoOSoAfww==',
    age: 0,
    gender: '-',
		psId:1
});


db.user.insert({username:"xiaohua123", password: 'q/FW889kSW+dosq8po2V/g==',age: 12,gender: '男',psId:2});
db.user.insert({username:"honghong123", password: 'jWdARNqAsZMqaCWyIabSQw==',age: 22,gender: '女',psId:2});
db.user.insert({username:"eddy1993", password: 'JVscV2x0DuNCK7Jf6Tdqrw==',age: 24,gender: '男',psId:2});
db.user.insert({username:"taylor13", password: 'UVBoHYpsG8KhFVLX5snBGQ==',age: 29,gender: '女',psId:2});
db.user.insert({username:"thdufr33", password: 'x7bayyACMYztncn0xGZ1lA==',age: 26,gender: '男',psId:2});


---------------分割线--------以下为明文密码----------
'admin’,’admin’
'xiaohua123','333444'
'honghong123','33344444'
'eddy1993','wde3344'
'taylor13','3hjhdede'
'thdufr33','3h3332de'
'newset23','3h232323e'
'787hdhjd','3h23233e'
'3273dedjd','989dd3e'
