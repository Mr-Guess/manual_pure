CREATE TABLE tb_job_remind (
MODEL NVARCHAR(200) NULL ,--model
NAME NVARCHAR(200) NULL ,--name
TIME NVARCHAR(200) NULL ,--time
OWNER NVARCHAR(200) NULL ,--owner
CREATOR NVARCHAR(200) NULL ,--creator

ID VARCHAR(50) NOT NULL PRIMARY KEY ,
STATUS CHAR(1) NULL DEFAULT ((1)) ,
CREATED VARCHAR(32) NULL ,
CREATE_TIME DATETIME NULL ,
UPDATED VARCHAR(32) NULL ,
UPDATE_TIME DATETIME NULL, 
ORG NVARCHAR(4000) NULL, 
 ORG_TREE NVARCHAR(4000) NULL 
)