create table hirarin.users(
    user_id char(10)                        --ユーザーID
    ,user_name varchar(50) not null         --名前
    ,mail_address varchar(30) not null      --メールアドレス
    ,passcode varchar(50) not null          --ログインパスワード（hash値）
    ,last_login_date timestamp              --最終ログイン日時
    ,last_change_passcode_date timestamp    --最終ログイン日時
    ,login_faild_count integer default 0    --ログイン失敗回数
    ,locked_flag char(1) default '0'        --ロックフラグ(1:ロック / 0:平気)
    ,unlock_target_flag char(1) default '0' --ロック解除対象フラグ(1:解除対象 / 0:解除対象外)
    ,constraint pk_users primary key (user_id)
);
