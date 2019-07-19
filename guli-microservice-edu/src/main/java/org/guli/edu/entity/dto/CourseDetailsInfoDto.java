package org.guli.edu.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("课程详细信息传输数据")
public class CourseDetailsInfoDto {

    @ApiModelProperty("课程id")
    private String id;

    @ApiModelProperty("课程标题")
    private String title;

    @ApiModelProperty("课程价格")
    private BigDecimal price;

    @ApiModelProperty("总课时")
    private Integer lessonNum;

    @ApiModelProperty("课程封面")
    private String cover;

    @ApiModelProperty("课程描述")
    private String description;

    @ApiModelProperty("课程类别父标题")
    private String subjectParentTitle;

    @ApiModelProperty("课程类别标题")
    private String subjectTitle;

    @ApiModelProperty("讲师名")
    private String teacherName;

    @ApiModelProperty("讲师头像")
    private String teacherAvatar;

    @ApiModelProperty("讲师简介")
    private String teacherIntro;

    @ApiModelProperty("讲师资历")
    private String teacherCareer;

    @ApiModelProperty("讲师级别")
    private Integer teacherLevel;

    @ApiModelProperty("章节id")
    private String chapterId;

    @ApiModelProperty("章节标题")
    private String chapterTitle;

    @ApiModelProperty("视频id")
    private String videoId;

    @ApiModelProperty("视频标题")
    private String videoTitle;

    @ApiModelProperty("视频是否免费")
    private String videoIsFree;

}
