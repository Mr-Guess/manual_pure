CREATE TABLE tb_frame_list (
FRAME_NAME VARCHAR(200) NULL ,-- 流程名称
FRAME_CODE VARCHAR(200) NULL ,-- 流程代码

ID VARCHAR(50) NOT NULL PRIMARY KEY ,
STATUS CHAR(1) NULL DEFAULT '1' ,
CREATED VARCHAR(32) NULL ,
CREATE_TIME DATETIME NULL ,
UPDATED VARCHAR(32) NULL ,
UPDATE_TIME DATETIME NULL, 
ORG text NULL, 
ORG_TREE text NULL, 
) ENGINE=MyISAM;
ALTER TABLE `tb_frame_list` ADD FULLTEXT `fidx_tb_frame_list_orgTree` (`ORG_TREE`);