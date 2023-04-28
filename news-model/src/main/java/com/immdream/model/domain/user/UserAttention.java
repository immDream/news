package com.immdream.model.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Primary;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author immDream
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_attention")
public class UserAttention extends Model<UserAttention> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 当前用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 被关注用户id
     */
    @TableField("attention_user_id")
    private Integer attentionUserId;

    /**
     * 0，没有特别关心
     */
    @TableField("is_particular")
    private Boolean isParticular;

    /**
     * 关注时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新操作时间
     */
    @TableField("update_time")
    private Date updateTime;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
