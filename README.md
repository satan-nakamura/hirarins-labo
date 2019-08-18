# hirarins-laboプロジェクト

## フォルダ構成
下記のとおり。
```
hirarins-labo
    docker
        db                  ・・・　dbコンテナ
        tom                 ・・・　tomcatコンテナ
    legacy-app
        legacy-app          ・・・ legacyアプリプロジェクト
```

## docker network作成 / コンテナビルド / アプリビルド / コンテナ起動方法 / アクセスURL
### docker network作成
以下のコマンドで作成する。
```
docker network create hirarins-labo
```

### コンテナビルド
以下のコマンドでコンテナをビルド
```
cd hirarins-labo/docker
docker-compose build
```

### アプリビルド
以下のコマンドでコンテナをビルド
```
cd hirarins-labo/legacy-app/legacy-app
mvn clean package -Dmaven.test.skip=true
```

### コンテナ起動方法
以下のコマンドでコンテナを起動。（DBを先に上げる必要がある。TOMCATがDB接続を保持するため。）
```
cd hirarins-labo/docker
docker-compose up -d db && docker-compose up tom
```

### アクセスURL
http://localhost:8080/legacy-app/sample

利用可能アカウント  
user1 / user1  
user2 / user2  
user3 / user3  

## 注意事項
visual studio codeで開発したため、eclipseなどでちゃんとビルドできるかは未確認
