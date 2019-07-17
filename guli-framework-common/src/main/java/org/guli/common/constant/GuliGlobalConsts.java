package org.guli.common.constant;

/**
 * 谷粒学院全局常量管理
 */
public interface GuliGlobalConsts {

    /**
     * 课程视频状态 Draft：未发布  Normal：已发布
     */
    interface COURSE_STATUS {
        String DRAFT = "Draft";
        String NORMAL = "Normal";
    }

    /**
     * 讲师头衔 1：高级讲师  2：首席讲师
     */
    interface TEACHER_LEVEL {
        Integer SENIOR = 1;
        Integer CHIEF = 2;
    }

    /**
     * 讲师头像名称 SENIOR：高级讲师  CHIEF：首席讲师
     */
    interface TEACHER_LEVEL_NAME {
        String SENIOR = "senior";
        String CHIEF = "chief";
    }

}
