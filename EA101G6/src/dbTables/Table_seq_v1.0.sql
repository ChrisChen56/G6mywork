DROP TABLE SHGMRPDT;
DROP TABLE SHGAME;
DROP SEQUENCE shgame_seq;
DROP SEQUENCE shgmrpdt_seq;
DROP DIRECTORY MEDIA_DIR;
DROP FUNCTION load_blob;
CREATE OR REPLACE  DIRECTORY MEDIA_DIR AS 'C:/Users/poi/Desktop/Shgameimgs/';
/* 擷取檔案的FUNCTION */ 
CREATE OR REPLACE FUNCTION load_blob( myFileName VARCHAR) RETURN BLOB as result BLOB;  
  myBFILE      BFILE;
  myBLOB       BLOB;
BEGIN
    myBFILE := BFILENAME('MEDIA_DIR',myFileName);
    dbms_lob.createtemporary(myBLOB, TRUE);
    dbms_lob.fileopen(myBFILE,dbms_lob.file_readonly);
    dbms_lob.loadfromfile(myBLOB,myBFILE,dbms_lob.getlength(myBFILE) );
    dbms_lob.fileclose(myBFILE);
    RETURN myBLOB;
END load_blob;

----------------------分開執行-------------------------------
CREATE TABLE SHGAME(
SHGMNO  VARCHAR2(7) NOT NULL,
SELLERNO  VARCHAR2(7) NOT NULL,
BUYERNO  VARCHAR2(7),
SHGMNAME VARCHAR2 (30),
PRICE NUMBER(6),
INTRO CLOB,
IMG BLOB,
UPCHECK NUMBER(1),
UPTIME DATE,
TAKE VARCHAR2(10),
TAKERNM VARCHAR2(20),
TAKERPH NUMBER(10),
ADDRESS VARCHAR2(30),
BOXSTATUS NUMBER(1),
PAYSTATUS NUMBER(1),
STATUS NUMBER(1),
SOLDTIME DATE,
CONSTRAINT SHGAME_SHGMNO_PK PRIMARY KEY (SHGMNO)/*,
CONSTRAINT SHGMRPDT_SELLERNO_FK FOREIGN KEY (SELLERNO) REFERENCES MBRPF (MBRNO),
CONSTRAINT SHGMRPDT_BUYERNO_FK FOREIGN KEY (BUYERNO) REFERENCES MBRPF (MBRNO)*/);

CREATE SEQUENCE shgame_seq
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO SHGAME(SHGMNO,SELLERNO,BUYERNO,SHGMNAME,PRICE,INTRO,IMG,UPCHECK,UPTIME,TAKE,TAKERNM,TAKERPH,ADDRESS,BOXSTATUS,PAYSTATUS,STATUS,SOLDTIME) VALUES(
'CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),
'BM00002',
NULL,
'矮人礦坑',
550,
'辛勤的小矮人們在礦坑中賣力地工作，期望能夠挖到金礦。然而他們當中卻混入了幾個想獨吞礦藏的壞蛋，不斷趁人不注意時暗自破壞。假如好人們成功的開闢一條通往寶藏的隧道，他們可以得到金塊，而妨礙者則落得兩手空空。然而，假如好人們失敗了，那麼妨礙者就能夠得到金塊。到底鹿死誰手，這就要各憑本事了。
九成新，沒時間玩所以賣出，請有緣人接收～',
load_blob('Saboteur.jpg'),
0,
TO_DATE('2020-02-12','YYYY-MM-DD'),
NULL,
NULL,
NULL,
NULL,
0,
0,
0,
NULL);

INSERT INTO SHGAME VALUES(
'CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),
'BM00001',
NULL,
'拾穗/194片拼圖',
200,
'米勒描繪了三個正在彎著腰、低著頭、在收割過的麥田裏撿拾剩落麥穗的婦女。她們穿著粗布衣裙和沉重的舊鞋子，此幅畫作沒有正面描繪她們的面孔，也沒有任何的美化。米勒用較明顯的輪廓使形象堅實有力，充分的表現了農民特有氣質。豐富細膩的暖色調與色彩表現沈著，使作品在純樸濃厚渾厚中具有震撼人的力量。
有點年代的拾穗拼圖，而且有幾片失蹤了，不介意的話再做下訂。',
load_blob('Des glaneuses.jpg'),
0,
TO_DATE('2020-02-24','YYYY-MM-DD'),
NULL,
NULL,
NULL,
NULL,
0,
0,
0,
NULL);

INSERT INTO SHGAME VALUES(
'CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),
'BM00003',
NULL,
'抵抗組織 阿瓦隆 Avalon',
400,
'「阿瓦隆(The Resistance：Avalon)」遊戲為2012年由Don Eskridge所設計的派對遊戲，並獲得2013年金畸獎最佳派對遊戲獎項推薦。遊戲中玩家們扮演著正義與邪惡兩方陣營，準備在阿瓦隆島展開決戰。此時，代表正義方亞瑟王陣營中卻出現邪惡方莫德雷德陣營的爪牙，隱藏在正義方陣營內伺機破壞，不讓正義方成功執行任務。
經典好玩的桌遊，但沒人跟我玩，只好賣出了。',
load_blob('Avalon.jpg'),
0,
TO_DATE('2020-03-05','YYYY-MM-DD'),
NULL,
NULL,
NULL,
NULL,
0,
0,
0,
NULL);

INSERT INTO SHGAME VALUES(
'CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),
'BM00005',
NULL,
'高雄大空襲',
1000,
'高雄大空襲(Radio on Takao)是一款劇情式的合作桌遊，一共有5種劇情可以選擇，玩家們要透過解劇情任務來達成遊戲目標，若遊戲中有任何一位玩家死亡，遊戲立刻以失敗結束。高雄大空襲的故事背景是在日治時期，當時因為高雄有許多日軍的工廠設施而被美軍轟炸，桌遊的劇情任務就以此為背景展開。
還不錯的國產桌遊，玩到膩了所以想賣掉。',
load_blob('Raid on Taihoku.jpg'),
0,
TO_DATE('2020-03-18','YYYY-MM-DD'),
NULL,
NULL,
NULL,
NULL,
0,
0,
0,
NULL);

Set define off;
INSERT INTO SHGAME VALUES(
'CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),
'BM00004',
NULL,
'柏德之門的背叛者',
1299,
'柏德之門（英語：Baldur''s Gate）是一部具有高度奇幻色彩的電腦角色扮演遊戲，由BioWare和黑島工作室開發，Interplay在1998年發行。2013年1月17日推出包含本體和資料片《劍灣傳奇》的《柏德之門：增強版》。該遊戲被玩家廣泛認為是角色扮演遊戲的一個經典作品和里程碑。
經得起時間考驗的好遊戲！每次遊玩都從中體驗到D&D世界多變的魅力，適合想要嘗試的你。',
load_blob('Betrayal at Baldur''s Gate.jpg'),
0,
TO_DATE('2020-03-27','YYYY-MM-DD'),
NULL,
NULL,
NULL,
NULL,
0,
0,
0,
NULL);

INSERT INTO SHGAME VALUES(
'CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),
'BM00006',
NULL,
'詐賭巫師',
390,
'在這個遊戲裡，玩家們是懂得各種咒語的魔法師。在怪物競技賭場裡，只要自己押注的怪物鬥士獲勝，就能得到一大筆賞金。玩家們秘密地使用咒語，想盡辦法讓自己押注的怪物鬥士獲勝。
但是競技場可不會放任不管，有時是嚴格的裁判，有時是寬容的裁判。
強大的咒語在某些情況下無法使用，但也有機會背著裁判使用，做得太過火可是會取消比賽的喔。
使用咒語讓自己押注的怪物鬥士勝利（存活下來），以賺取賞金，遊戲結束時，由賺到最多錢的玩家獲勝。
每場比賽的裁判個性都不一樣，誰能口袋滿滿地回家呢？
從桌遊店全新原價入手 畢業後沒有朋友一起玩故售出8成新 配件齊全',
load_blob('Cheaty Mages!.jpg'),
0,
TO_DATE('2020-04-11','YYYY-MM-DD'),
NULL,
NULL,
NULL,
NULL,
0,
0,
0,
NULL);

INSERT INTO SHGAME VALUES(
'CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),
'BM00007',
NULL,
'趕快來逛逛！！',
10,
'慶祝盟寵家族全新開幕，即日起至08月24日止，同品項商品、不限數量，一律享八折優惠！這是我們的網址：https://ppt.cc/fW46Dx',
load_blob('MTFamily.jpg'),
0,
TO_DATE('2020-04-20','YYYY-MM-DD'),
NULL,
NULL,
NULL,
NULL,
0,
0,
0,
NULL);



INSERT INTO SHGAME VALUES(
'CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),
'BM00008',
NULL,
'骰越世紀',
800,
'在60分鐘內建立一個繁盛的文明！ 
在歷史上永遠只有強大的文明才會被歌頌！但是文明發展不但需要一位決策英明的統治者，很多時候?國家的運氣?似乎也是決定性的關鍵…… 
取得物資、指派工人建造城市及巨型建築，透過文化與科學的發展來推進您的文明，但別忘了要生產足夠的糧食來餵養您迅速增加的人民。 
，靠著您無比的機智與手氣，抓起這些骰子，並在這個步調快速、令人上癮且策略豐富的遊戲中「骰越世紀」，創造一座流傳千古的傳奇之都吧！！',
load_blob('Roll Through the Ages.jpg'),
0,
TO_DATE('2020-05-11','YYYY-MM-DD'),
NULL,
NULL,
NULL,
NULL,
0,
0,
0,
NULL);

INSERT INTO SHGAME VALUES(
'CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),
'BM00008',
NULL,
'正版桌遊-妙探尋兇',
600,
'在一個暴風雨的夜晚，玩家們受邀參加一場豪宅宴會，
但這場宴會的背後，其實是個計劃已久的連續殺人事件，
因為當大家抵達到會場時，發現豪宅的主人已經陳屍在泳池邊了...
於是，找出這場兇案的真相，就是這場遊戲的目的，
但是要記住，時間拖得越久，就有可能會有第二個受害者出現 !!
那麼...兇手倒底是來賓中的那一位呢？',
load_blob('Cluedo.jpg'),
0,
TO_DATE('2020-05-16','YYYY-MM-DD'),
NULL,
NULL,
NULL,
NULL,
0,
0,
0,
NULL);

INSERT INTO SHGAME VALUES(
'CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),
'BM00003',
NULL,
'UNO',
150,
'你需要謹慎管理自己的手牌，判斷檯面上出現的顏色、數字，利用卡牌間的特性，
想辦法讓自己的牌全部脫手，成為這場遊戲的優勝者！知名卡牌遊戲，讓您一玩就上癮。看誰先把手牌全數打出去！剩最後一張時別忘了大聲喊一聲UNO哦！ 全新未拆封',
load_blob('uno.jpg'),
0,
TO_DATE('2020-05-28','YYYY-MM-DD'),
NULL,
NULL,
NULL,
NULL,
0,
0,
0,
NULL);

CREATE TABLE SHGMRPDT(
SHGMRPNO VARCHAR2(7) NOT NULL,
SHGMNO VARCHAR2(7) NOT NULL,
SUITERNO VARCHAR2(7) NOT NULL,
DETAIL CLOB,
STATUS NUMBER(1),
CONSTRAINT SHGMRPDT_SHGMRPNO_PK PRIMARY KEY (SHGMRPNO)/*,
CONSTRAINT SHGMRPDT_SHGMNO_FK FOREIGN KEY (SHGMNO) REFERENCES SHGAME (SHGMNO),
CONSTRAINT SHGMRPDT_SUITERNO_FK FOREIGN KEY (SUITERNO) REFERENCES MBRPF (MBRNO)*/);

CREATE  SEQUENCE shgmrpdt_seq
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO SHGMRPDT VALUES(
'CB'||LPAD(shgmrpdt_seq.NEXTVAL,5,'0'),
'CA00004',
'BM00001',
'圖片與產品名稱、簡介不一樣。',
0);

INSERT INTO SHGMRPDT VALUES(
'CB'||LPAD(shgmrpdt_seq.NEXTVAL,5,'0'),
'CA00005',
'BM00002',
'簡介好像是電玩版的，跟桌遊沒有關係。',
0);

INSERT INTO SHGMRPDT VALUES(
'CB'||LPAD(shgmrpdt_seq.NEXTVAL,5,'0'),
'CA00007',
'BM00004',
'簡介是廣告訊息。',
0);

INSERT INTO SHGMRPDT VALUES(
'CB'||LPAD(shgmrpdt_seq.NEXTVAL,5,'0'),
'CA00008',
'BM00007',
'圖片太模糊了，看不清楚。',
0);
