ALTER TABLE TransPackageContent DROP FOREIGN KEY FKTransPacka6349;
ALTER TABLE TransPackageContent DROP FOREIGN KEY packagecontent;
ALTER TABLE TransHistory DROP FOREIGN KEY packagehistory;
ALTER TABLE TransHistory DROP FOREIGN KEY packagehistoryowner;
ALTER TABLE ExpressHistory DROP FOREIGN KEY expresshistory;
ALTER TABLE ExpressHistory DROP FOREIGN KEY expresshistoryowner;

DROP TABLE IF EXISTS TransPackage;
DROP TABLE IF EXISTS UserInfo;
DROP TABLE IF EXISTS TransPackageContent;
DROP TABLE IF EXISTS ExpressSheet;
DROP TABLE IF EXISTS CustomerInfo;
DROP TABLE IF EXISTS TransHistory;
DROP TABLE IF EXISTS ExpressHistory;
DROP TABLE IF EXISTS TransNode;

DROP TABLE IF EXISTS Region;

CREATE TABLE TemporaryExpress(SN int(10) NOT NULL
AUTO_INCREMENT, Type int(4),
SenderName varchar(32),SenderTel varchar(24),
SenderRegCode char(6),SenderAddr varchar(64),
ReceiverName varchar(32),ReceiverTel varchar(24),
ReceiverRegCode char(6),ReceiverAddr varchar(64),
Note varchar(64),PRIMARY KEY (SN) )

CREATE TABLE TransPackageContent (SN int(10) NOT NULL 
AUTO_INCREMENT, ExpressID char(32) NOT NULL, 
PackageID char(32) NOT NULL,Status int(4),
 PRIMARY KEY (SN)) 
CHARACTER SET UTF8;

CREATE TABLE TransHistory (SN int(10) NOT NULL 
AUTO_INCREMENT, PackageID char(32) NOT NULL,
ActID int(4) NOT NULL,UserID int(10),
ActTime datetime NOT NULL, SourceNode char(9),
TargetNode char(9), x float, y float, 
PRIMARY KEY (SN)) CHARACTER SET UTF8;

CREATE TABLE TransPackage (ID char(32) NOT NULL, 
SourceNode char(9), TargetNode char(9), 
CreateTime datetime NULL, Status int(4),
FinishTime datetime NULL, PRIMARY KEY (ID)) 
CHARACTER SET UTF8;

CREATE TABLE ExpressSheet (ID char(32) NOT NULL, Type int(4),
 SenderName varchar(32),SenderTel varchar(24),
 SenderRegCode char(6),SenderAddr varchar(64),
 ReceiverName varchar(32),ReceiverTel varchar(24),
 ReceiverRegCode char(6),ReceiverAddr varchar(64), 
 Weight float(16), TransWay int(4),TransFee float(16),
 NextNodeId char(9),CreateTime datetime, FinishTime datetime,
 Status int(4),Note varchar(64), PRIMARY KEY (ID)) 
CHARACTER SET UTF8;

CREATE TABLE ExpressHistory (SN int(10) NOT NULL AUTO_INCREMENT, 
ExpressID char(32) NOT NULL,ActID int(4) NOT NULL,
UserID int(10),ActTime datetime NOT NULL,
SourceNode char(9), TargetNode char(9),
  x float, y float, PRIMARY KEY (SN)) 
CHARACTER SET UTF8;

CREATE TABLE TransNode (ID char(9) NOT NULL ,
 NodeName varchar(32), NodeType int(4),
  RegionCode varchar(6),DetailedAddr varchar(64),
   TelCode varchar(24),Status int(4),
   x float, y float, PRIMARY KEY (ID)) CHARACTER SET UTF8;

CREATE TABLE UserInfo (UID int(10) NOT NULL AUTO_INCREMENT,
UName varchar(24), PWD varchar(8), 
Name varchar(16), URole int(4), 
TelCode varchar(24), Status int(4), 
x float, y float, 
PRIMARY KEY (UID)) CHARACTER SET UTF8;

CREATE TABLE Region (RegionCode char(6) NOT NULL, 
Prv varchar(32), Cty varchar(32), Twn varchar(32), 
Stage int(4) NOT NULL, PRIMARY KEY (RegionCode)) CHARACTER SET UTF8;

CREATE TABLE Posion (PosCode int(10) NOT NULL AUTO_INCREMENT, X float NOT NULL, Y float NOT NULL, PRIMARY KEY (PosCode)) CHARACTER SET UTF8;

CREATE TABLE CustomerInfo (CID int(10) NOT NULL AUTO_INCREMENT,
NickName varchar(32),
PWD varchar(8),  TelCode varchar(24), 
PRIMARY KEY (CID)) CHARACTER SET UTF8;


ALTER TABLE TransPackageContent ADD INDEX FKTransPacka6349 (ExpressID), ADD CONSTRAINT FKTransPacka6349 FOREIGN KEY (ExpressID) REFERENCES ExpressSheet (ID);
ALTER TABLE TransPackageContent ADD INDEX packagecontent (PackageID), ADD CONSTRAINT packagecontent FOREIGN KEY (PackageID) REFERENCES TransPackage (ID);
ALTER TABLE TransHistory ADD INDEX packagehistory (PackageID), ADD CONSTRAINT packagehistory FOREIGN KEY (PackageID) REFERENCES TransPackage (ID);
ALTER TABLE TransHistory ADD INDEX packagehistoryowner (UserID), ADD CONSTRAINT packagehistoryowner FOREIGN KEY (UserID) REFERENCES UserInfo (UID);
ALTER TABLE ExpressHistory ADD INDEX expresshistory (ExpressID), ADD CONSTRAINT expresshistory FOREIGN KEY (ExpressID) REFERENCES ExpressSheet (ID);
ALTER TABLE ExpressHistory ADD INDEX expresshistoryowner (UserID), ADD CONSTRAINT expresshistoryowner FOREIGN KEY (UserID) REFERENCES UserInfo (UID);

ALTER TABLE ExpressSheet ADD INDEX express_node (NextNodeId), ADD CONSTRAINT express_node FOREIGN KEY (NextNodeId) REFERENCES TransNode(ID);
ALTER TABLE ExpressHistory ADD INDEX expresshistory_snode (SourceNode), ADD CONSTRAINT expresshistory_snode FOREIGN KEY (SourceNode) REFERENCES TransNode(ID);
ALTER TABLE ExpressHistory ADD INDEX expresshistory_tnode (TargetNode), ADD CONSTRAINT expresshistory_tnode FOREIGN KEY (TargetNode) REFERENCES TransNode(ID);
ALTER TABLE TransHistory ADD INDEX packagehistory_snode (SourceNode), ADD CONSTRAINT packagehistory_snode FOREIGN KEY (SourceNode) REFERENCES TransNode(ID);
ALTER TABLE TransHistory ADD INDEX packagehistory_tnode (TargetNode), ADD CONSTRAINT packagehistory_tnode FOREIGN KEY (TargetNode) REFERENCES TransNode(ID);
