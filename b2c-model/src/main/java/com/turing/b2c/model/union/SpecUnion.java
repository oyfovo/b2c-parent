package com.turing.b2c.model.union;

import com.turing.b2c.model.pojo.Specification;
import com.turing.b2c.model.pojo.SpecificationOption;
import lombok.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author H ovo
 * @date 2021年12月02日 17:09
 * 规格联合类
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecUnion implements Serializable {
    //生成随机序列化数
    private static final long serialVersionUID = -6298650669510120417L;
    //规格
    private Specification spec;
    //规格联系
    private List<SpecificationOption> specOptionList;
}
