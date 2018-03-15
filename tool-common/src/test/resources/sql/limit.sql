DELIMITER $$

USE `baixin`$$

DROP PROCEDURE IF EXISTS `limit_procedure_0`$$

CREATE PROCEDURE `limit_procedure_0`(IN `product_id` BIGINT(20))

BEGIN  					

DECLARE done INT DEFAULT 0;
DECLARE _id BIGINT DEFAULT 0;
DECLARE _customer_id BIGINT DEFAULT 0;
DECLARE _product_code VARCHAR(32);
DECLARE _credit_limit BIGINT DEFAULT 0;
DECLARE _occupy_limit BIGINT DEFAULT 0;	
DECLARE _err INT DEFAULT 0;
DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _err=1;

SET autocommit=0; 

SELECT CONCAT('CREATE_TEM_TABLE_START_TIME: ',NOW()) AS start_time;

DROP TABLE IF EXISTS `produce_limit_temp_1`;
CREATE TABLE `produce_limit_temp_1` (
  `id` BIGINT(20) NOT NULL ,
  `customer_id` BIGINT(20),
  `product_code` VARCHAR(32),
  `credit_limit` BIGINT(20),
  `occupy_limit` BIGINT(20), 
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO produce_limit_temp_1 SELECT MAX(id) id,customer_id,product_code,MAX(credit_limit) credit_limit,SUM(occupy_limit) occupy_limit FROM (SELECT id,customer_id,product_code,credit_limit,available_limit, credit_limit-available_limit AS occupy_limit FROM t_product_limit_0_1 WHERE id > product_id) AS tem GROUP BY customer_id;
COMMIT;

IF _err=0 THEN
	COMMIT;
ELSE
	ROLLBACK;
	SELECT CONCAT('INSERT INTO produce_limit_temp is false') AS error_message;
END IF;


DECLARE product_cursor_data CURSOR FOR SELECT id,customer_id,product_code,credit_limit,occupy_limit FROM produce_limit_temp_1 WHERE id > product_id GROUP BY customer_id;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

OPEN  product_cursor_data;     

    read_loop: LOOP

            FETCH  NEXT FROM product_cursor_data INTO _id,_customer_id,_product_code,_credit_limit,_occupy_limit;
            IF done=1 THEN
                LEAVE read_loop;
            END IF;
	
        UPDATE t_cycling_account_limit_0_1 SET credit_limit=_credit_limit,available_limit=(_credit_limit-_occupy_limit) WHERE customer_id=_customer_id;
		
	INSERT INTO t_product_type_limit_0_1 SELECT  `id`,`customer_id`,`cycling_account_credit_id`,`credit_limit`,`available_limit`,`cycling_flag`,`account_credit_id`,`account_type`,"2" AS `product_type` ,`status`,`modified`,`created`,`version` FROM t_cycling_account_limit_0_1
		
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