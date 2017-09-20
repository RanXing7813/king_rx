package cn.com.king.repository.db2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import cn.com.king.domain.db2.LogZJTaskInfo;


@Transactional
public interface LogZJTaskInfoRepository  extends JpaRepository<LogZJTaskInfo, String>, JpaSpecificationExecutor<LogZJTaskInfo>{


}

