DELIMITER $$

USE `baixin`$$

DROP PROCEDURE IF EXISTS `limit_procedure_0`$$

CREATE PROCEDURE `limit_procedure`(IN `product_id` BIGINT(20))

BEGIN  					

#默认值为0，发生异常修改为1	
DECLARE _err INT DEFAULT 0;
DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _err=1;
#关闭手动提交事务
SET autocommit=0; 

#创建临时表

SELECT CONCAT('CREATE_TEM_TABLE_START_TIME: ',NOW()) AS start_time;

DROP TABLE IF EXISTS `produce_limit_temp_1`;
CREATE TABLE `produce_limit_temp_1` (
  `id` BIGINT(20) NOT NULL ,
  `customer_id` BIGINT(20),
  `product_code` VARCHAR(32),
  `credit_limit` BIGINT(20),
  `available_limit` BIGINT(20),
  `occupy_limit` BIGINT(20), 
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

#临时表中插入数据
INSERT INTO produce_limit_temp SELECT id,customer_id,product_code,credit_limit,available_limit, credit_limit-available_limit AS occupy_limit FROM t_product_limit_0_1
COMMIT;

IF _err=0 THEN
	COMMIT;
ELSE
	ROLLBACK;
	SELECT CONCAT('INSERT INTO produce_limit_temp is false') AS error_message;
END IF;

#将临时表中数据，存入游标中

#id作为条件，查询出要处理的数据数量

DECLARE done INT DEFAULT FALSE;
-- 游标
DECLARE product_cursor_data CURSOR FOR SELECT MAX(id) id,customer_id,product_code,MAX(credit_limit) credit_limit,SUM(occupy_limit) AS occupy_limit FROM t_product_limit_0_0 WHERE id > product_id GROUP BY customer_id
-- 将结束标志绑定到游标
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

OPEN  product_cursor_data;     
    -- 遍历
    read_loop: LOOP
            -- 取值 取多个字段
            FETCH  NEXT from product_cursor_data INTO customer_id,credit_limit,occupy_limit;
            IF done THEN
                LEAVE read_loop;
             END IF;
 
        --修改账户额度表、插入类别额度表		
        UPDATE t_cycling_account_limit_0_1 SET credit_limit=credit_limit,available_limit=(credit_limit-occupy_limit) WHERE customer_id=customer_id;
		
		INSERT INTO t_product_type_limit_0_1 (`id`,`customer_id`,`cycling_account_credit_id`,`credit_limit`,`available_limit`,`cycling_flag`,`account_credit_id`,`account_type`,`product_type`,`status`,`modified`,`created`,`version`) 
		SELECT  `id`,`customer_id`,`cycling_account_credit_id`,`credit_limit`,`available_limit`,`cycling_flag`,`account_credit_id`,`account_type`,"2" AS `product_type` ,`status`,`modified`,`created`,`version` FROM t_cycling_account_limit_0_1
		
		IF _err=0 THEN
			COMMIT;
		ELSE
			ROLLBACK;
			SELECT CONCAT('INSERT INTO produce_limit_temp is false') AS error_message;
		END IF;
		
    END LOOP;
 
 
CLOSE product_cursor_data;




SELECT CONCAT('TRANSFER_DATA_END_TIME: ',NOW()) AS end_time;


END$$
DELIMITER ;