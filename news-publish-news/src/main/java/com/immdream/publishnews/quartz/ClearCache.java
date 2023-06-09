package com.immdream.publishnews.quartz;

import com.immdream.publishnews.controller.NewsManagerController;
import com.immdream.publishnews.controller.NewsRecommendController;
import com.immdream.publishnews.controller.NewsViewsController;
import com.immdream.publishnews.service.NewsRecommendService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.publishnews.quartz
 *
 * @author immDream
 * @date 2023/06/08/22:50
 * @since 1.8
 */
@Slf4j
public class ClearCache extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("===============定时任务清除缓存=================");
        NewsViewsController.clearHotNews();
        NewsRecommendService.clearCache();
    }
}
