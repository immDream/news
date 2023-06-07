package com.immdream.model.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("message")
public class Message extends Model<Message> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("userid")
    private Integer userId;

    @TableField("newsid")
    private Integer newsId;

    private String message;

    private String title;

    @TableField("hadread")
    private boolean hadRead;

    /**
     * 新闻发布时间
     */
    @TableField("time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date time;



    @Override
    public Serializable pkVal() {
        return null;
    }

}
