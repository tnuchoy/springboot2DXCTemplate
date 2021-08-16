DELETE FROM `TB_M_GIM_D`;
DELETE FROM `TB_M_GIM_H`;

INSERT INTO `TB_M_GIM_H` (`GIM_TYPE`, `GIM_DESC`, `CD_LENGTH`, `FIELD1_LABEL`, `FIELD2_LABEL`, `FIELD3_LABEL`, `ACTIVE_FLAG`, `CREATED_BY`, `CREATED_DT`, `MODIFIED_BY`, `MODIFIED_DT`)
VALUES ('ACTIVE_FLAG', 'Active Flag Please don\'t delete', 1, 'Not used', 'Not used', 'Not used', 'Y', 'SYSTEM', '2021-08-04 10:37:42', 'SYSTEM', '2021-12-01 10:37:42');

INSERT INTO `TB_M_GIM_D` (`GIM_TYPE`, `GIM_CD`, `GIM_VALUE`, `FIELD1`, `FIELD2`, `FIELD3`, `ACTIVE_FLAG`, `CREATED_BY`, `CREATED_DT`, `MODIFIED_BY`, `MODIFIED_DT`)
VALUES ('ACTIVE_FLAG', 'N', 'In-Active', NULL, NULL, NULL, 'Y', 'SYSTEM', '2021-08-04 10:43:35', 'SYSTEM', '2021-08-04 10:43:35');
INSERT INTO `TB_M_GIM_D` (`GIM_TYPE`, `GIM_CD`, `GIM_VALUE`, `FIELD1`, `FIELD2`, `FIELD3`, `ACTIVE_FLAG`, `CREATED_BY`, `CREATED_DT`, `MODIFIED_BY`, `MODIFIED_DT`)
VALUES ('ACTIVE_FLAG', 'Y', 'Active', NULL, NULL, NULL, 'Y', 'SYSTEM', '2021-08-04 10:43:35', 'SYSTEM', '2021-08-04 10:43:35');
INSERT INTO `TB_M_GIM_D` (`GIM_TYPE`, `GIM_CD`, `GIM_VALUE`, `FIELD1`, `FIELD2`, `FIELD3`, `ACTIVE_FLAG`, `CREATED_BY`, `CREATED_DT`, `MODIFIED_BY`, `MODIFIED_DT`)
VALUES ('ACTIVE_FLAG', 'Z', 'Active-Z', NULL, NULL, NULL, 'N', 'SYSTEM', '2021-08-04 10:43:35', 'SYSTEM', '2021-08-04 10:43:35');