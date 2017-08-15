CREATE TABLE sys_sheet (
MOBULE_ID NVARCHAR(255) NULL ,--所属模块
NAME_EN NVARCHAR(255) NULL ,--英文表名
NAME_CH NVARCHAR(255) NULL ,--中文名称
ENABLE NVARCHAR(255) NULL ,--是否启用

ID VARCHAR(50) NOT NULL PRIMARY KEY ,
STATUS CHAR(1) NULL DEFAULT ((1)) ,
CREATED VARCHAR(32) NULL ,
CREATE_TIME DATETIME NULL ,
UPDATED VARCHAR(32) NULL ,
UPDATE_TIME DATETIME NULL
)