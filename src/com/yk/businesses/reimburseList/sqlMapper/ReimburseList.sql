CREATE TABLE tb_reimburse_list (
PROJECT_NAME NVARCHAR(200) NULL ,--付款事由
BZ NVARCHAR(200) NULL ,--备注摘要
AMOUNT NVARCHAR(200) NULL ,--金额
RELATE_DATA NVARCHAR(200) NULL ,--relateData

ID VARCHAR(50) NOT NULL PRIMARY KEY ,
STATUS CHAR(1) NULL DEFAULT ((1)) ,
CREATED VARCHAR(32) NULL ,
CREATE_TIME DATETIME NULL ,
UPDATED VARCHAR(32) NULL ,
UPDATE_TIME DATETIME NULL, 
ORG NVARCHAR(4000) NULL, 
 ORG_TREE NVARCHAR(4000) NULL 
)