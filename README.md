# ネットワーク同時多人数対局可能チェスプログラム
## 概要
本プログラムは、対局をルームで仕切り、ルームごとにネットワークを介して同時に多数の対戦を行うことができるプログラムであり、サーバープログラムとクライアントプログラムからなる。また、クライアントは自分がプレイヤーになるか、観戦者となるかを選択することができる。そして、同じルームにいるクライアント同士はゲーム画面上でチャットを行うことができる。  
  
## 実行環境
openjdk version "1.8.0_131"  
OpenJDK Runtime Environment (build 1.8.0_131-8u131-b11-0ubuntu1.16.04.2-b11)  
OpenJDK 64-Bit Server VM (build 25.131-b11, mixed mode)  

## 実行方法
### test
$ java -jar server.jar  
$ java -jar client.jar  
$ java -jar client.jar  
client.jarを起動するとルーム入場画面が出るので、必要事項記入の上submitを押してください。  
### 標準実行
$ java -jar server.jar [port番号]  
$ java -jar client.jar [ipアドレス|host名] [port番号|nothing]  
$ java -jar client.jar [ipアドレス|host名] [port番号|nothing]  

### 作成
Frame.java : 桑原　豊明  
Board.java : 大石　雄大  
その他ネットワーク部等: 片山　源太郎(gntrua)  


##注意事項
1. 入場画面の名前入力欄は、名前入力完了後enterを押してください。
2. Serverから先に起動してください。
3. Server起動時の引数がない場合は10101~のportを使うのでその辺りが開いていないとうまく通信できません。
