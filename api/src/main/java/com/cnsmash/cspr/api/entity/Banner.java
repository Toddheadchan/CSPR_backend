package com.cnsmash.cspr.api.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Amaki
 * @since 2021-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;

    private String deadline;

    private String link;


}
