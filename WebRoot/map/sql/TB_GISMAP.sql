CREATE TABLE TB_GISMAP (
[dwdm] nvarchar(255) NULL ,
[wxyid] nvarchar(255) NULL ,
[wxytype] nvarchar(255) NULL ,
[idkey] nvarchar(255) NULL ,
[remark] nvarchar(255) NULL ,
[x] float(53) NULL ,
[y] float(53) NULL 
);

ALTER TABLE TB_GISMAP ADD CONSTRAINT UNI_WXYID UNIQUE(WXYID);