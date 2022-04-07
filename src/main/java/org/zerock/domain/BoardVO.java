package org.zerock.domain;
import java.sql.Date;

import org.apache.ibatis.logging.Log;

import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

 
@Data
public class BoardVO {

	private Long bno;
	private String title, content, writer;
	private Date regdate, updateDate;
	
	
	
}
